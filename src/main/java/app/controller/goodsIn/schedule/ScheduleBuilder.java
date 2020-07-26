package app.controller.goodsIn.schedule;

import app.controller.sql.dao.PoMaterialsDao;
import app.controller.sql.dao.ScheduleDetailsDao;
import app.controller.sql.dao.SupplierMaterialsDao;
import app.controller.sql.serviceClasses.ScheduleEntryService;
import app.controller.utils.LabelWithIcons;
import app.model.ScheduleEntry;
import app.pojos.PoMaterials;
import app.pojos.ScheduleDetails;
import app.pojos.SupplierMaterials;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class ScheduleBuilder {


    private final List<ScheduleEntry> list;


    private final List<ScheduleEntry> comparableList;

    private final LocalDate date;


    public ScheduleBuilder(LocalDate date) {

        this.date = date;
        this.list = ScheduleEntryService.getDeliveriesFromDb(date);
        comparableList = ScheduleEntryService.getDeliveriesFromDb(date.minusDays(45), date);
    }


    public void buildSchedule(Node node, POTableTab poTableTab) {

        JFXAlert<String> alert = new JFXAlert<>((Stage) node.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(LabelWithIcons.largeWarningIconLabel("Generate schedule for: " + date));
        layout.setBody(new Label("This operation will override existing entries data"));
        JFXButton build  = new JFXButton("CONTINUE");
        JFXButton cancel = new JFXButton("CANCEL");

        build.setOnAction(e -> {
            build();
            poTableTab.listAllRecords();
            alert.hideWithAnimation();
        });

        cancel.setOnAction(b -> {
            alert.hideWithAnimation();
        });
        layout.setActions(build, cancel);
        alert.setContent(layout);
        alert.show();


    }


    private void build() {

        list.forEach(scheduleEntry -> {

            List<ScheduleEntry> allSupplierEntries = new ArrayList<>();

            comparableList.forEach(item -> {
                if (item.getSupplier().getSupplierCode().equalsIgnoreCase(scheduleEntry.getSupplier().getSupplierCode())) {
                    allSupplierEntries.add(item);
                }
            });

            if (allSupplierEntries.size() > 0) {

                String    haulier  = getHaulier(scheduleEntry, allSupplierEntries);
                String    bay      = getBay(allSupplierEntries, haulier);
                int       pallets  = getPalletsCount(scheduleEntry);
                int       duration = getUnloadingTime(pallets);
                LocalTime eta      = getEta(haulier, allSupplierEntries);

                ScheduleDetails details = scheduleEntry.getDetails();
                details.setHaulier(haulier);
                details.setEta(LocalDateTime.of(scheduleEntry.getOrder().getOrderDate(), eta));
                details.setBay(bay);
                details.setPallets(pallets);
                details.setDuration(duration);

                new ScheduleDetailsDao().update(details);
            }
        });
    }


    private LocalTime getEta(String haulier, List<ScheduleEntry> allSupplierEntries) {

        List<LocalTime> times = new ArrayList<>();

        allSupplierEntries.forEach(scheduleEntry -> {
            if (scheduleEntry.getDetails().getHaulier().equalsIgnoreCase(haulier)) {
                times.add(scheduleEntry.getDetails().getEta().toLocalTime());
            }
        });

        return times.stream().reduce(BinaryOperator.maxBy(Comparator.comparingInt(o -> Collections.frequency(times, o)))).get();
    }


    private String getHaulier(ScheduleEntry scheduleEntry, List<ScheduleEntry> allSupplierEntries) {

        Classifier<String, String> hauliersClassification = new BayesClassifier<>();


        //maps hauliers to delivered materials for supplier
        for (ScheduleEntry entry : allSupplierEntries) {

            List<PoMaterials> poMaterials = new PoMaterialsDao().getAll(entry.getOrder().getPoNumber());
            Set<String>       set         = new HashSet<>();

            for (PoMaterials material : poMaterials) {
                set.add(material.getMCode());
            }

            hauliersClassification.learn(entry.getDetails().getHaulier(), set);

        }

        Set<String> poMaterials = new HashSet<>();
        for (PoMaterials materials : new PoMaterialsDao().getAll(scheduleEntry.getOrder().getPoNumber())) {
            poMaterials.add(materials.getMCode());
        }

        return hauliersClassification.classify(poMaterials).getCategory();
    }


    private String getBay(List<ScheduleEntry> allSupplierEntries, String haulier) {

        List<String> bays = new ArrayList<>();

        for (ScheduleEntry entry : allSupplierEntries) {
            if (entry.getDetails().getHaulier().equalsIgnoreCase(haulier)) {
                bays.add(entry.getDetails().getBay());
            }
        }

        Map<String, Long> bay = bays.stream().collect(Collectors.groupingBy(b -> b, Collectors.counting()));
        return getMostCommonString(bay);

    }

    private String getMostCommonString(Map<String, Long> map) {

        String string = "";
        long   count  = 0;

        for (Map.Entry<String, Long> e : map.entrySet()) {
            String k = e.getKey();
            Long   v = e.getValue();
            if (count > 0) {
                if (v > count) {
                    string = k;
                    count = v;
                }
            }
            else {
                string = k;
                count = v;
            }
        }
        return string;

    }


    private int getPalletsCount(ScheduleEntry entry) {

        final int DEFAULT_PALLET_WEIGHT = 1000;
        int       pallets               = 0;

        System.out.println(entry.getSupplier().getSupplierName());
        List<PoMaterials>       list              = new PoMaterialsDao().getAll(entry.getOrder().getPoNumber());
        List<SupplierMaterials> supplierMaterials = new SupplierMaterialsDao().getAll(entry.getSupplier().getSupplierCode());

        for (PoMaterials poMaterials : list) {
            for (SupplierMaterials supplierMaterial : supplierMaterials) {
                if (supplierMaterial.getmCode().equalsIgnoreCase(poMaterials.getMCode())) {
                    if (supplierMaterial.getPalletWeight() > 0) {
                        pallets += BigDecimal.valueOf(
                                poMaterials.getExpectedQuantity() /
                                supplierMaterial.getPalletWeight()).setScale(0, RoundingMode.CEILING).intValue();

                    }
                    else {
                        pallets += BigDecimal.valueOf(
                                poMaterials.getExpectedQuantity() /
                                DEFAULT_PALLET_WEIGHT).setScale(0, RoundingMode.CEILING).intValue();
                    }
                }
            }
        }

        if (pallets > 28) {
            int counter = pallets / 28;
            int numberOfEntries = 0;
            List<ScheduleDetails> entries = new ScheduleDetailsDao().getAll(entry.getOrder().getPoNumber());


            for (ScheduleDetails details : entries) {
                if(details.getOrderDate().equals(entry.getDetails().getOrderDate())){
                    numberOfEntries++;
                }
            }



            if(numberOfEntries < counter){
                for (int i = 0; i < (counter - numberOfEntries); i++) {
                    new ScheduleDetailsDao().save(entry.getDetails());
                }
            }

            return 26;
        }

        return pallets;

    }


    private int getUnloadingTime(int pallets) {

        int duration = 20;
        duration += pallets;
        return duration;

    }


}
