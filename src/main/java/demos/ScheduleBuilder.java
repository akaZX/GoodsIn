package demos;

public class ScheduleBuilder {

//
//    private static LocalDate date = LocalDate.of(2020,1,27);
//    private static List<ScheduleEntry> list = ScheduleEntryService.getDeliveriesFromDb(date);
//
//
//    private static List<ScheduleEntry> comparableList;
//
//
//    public ScheduleBuilder(LocalDate date) {
//        this.date = date;
//    }
//
//
//    public static void main(String[] args) {
//
//
//        comparableList = ScheduleEntryService.getDeliveriesFromDb(date.minusDays(45), date);
//
//
//
//
//
//
//        list.forEach(scheduleEntry -> {
//
//            List<ScheduleEntry> allSupplierEntries = new ArrayList<>();
//
//            comparableList.forEach(item->{
//                if(item.getSupplier().getSupplierCode().equalsIgnoreCase(scheduleEntry.getSupplier().getSupplierCode())){
//                    allSupplierEntries.add(item);
//                }
//            });
//
//            if (allSupplierEntries.size() > 0) {
//
//                String haulier = getHaulier(scheduleEntry, allSupplierEntries);
//                String bay = getBay(allSupplierEntries, haulier);
//                int pallets = getPalletsCount(scheduleEntry);
//                int duration = getUnloadingTime(pallets);
//                LocalTime eta = getEta(haulier, allSupplierEntries);
//
//                ScheduleDetails details = scheduleEntry.getDetails();
//                details.setHaulier(haulier);
//                details.setEta(LocalDateTime.of(scheduleEntry.getOrder().getOrderDate(), eta));
//                details.setBay(bay);
//                details.setPallets(pallets);
//                details.setDuration(duration);
//
//                new ScheduleDetailsDao().update(details);
//            }
//
//
//        });
//
//
//    }
//
//
//    private static LocalTime getEta(String haulier, List<ScheduleEntry> allSupplierEntries){
//
//        List<LocalTime> times = new ArrayList<>();
//
//        allSupplierEntries.forEach(scheduleEntry -> {
//            if (scheduleEntry.getDetails().getHaulier().equalsIgnoreCase(haulier)) {
//                times.add(scheduleEntry.getDetails().getEta().toLocalTime());
//            }
//        });
//
//       return times.stream().reduce(BinaryOperator.maxBy(Comparator.comparingInt(o -> Collections.frequency(times, o)))).get();
//    }
//
//
//
//    private static String getHaulier(ScheduleEntry scheduleEntry, List<ScheduleEntry> allSupplierEntries) {
//
//
//        Classifier<String, String> hauliersClassification = new BayesClassifier<>();
//
//
//
//            //maps hauliers to delivered materials for supplier
//            for (ScheduleEntry entry : allSupplierEntries) {
//
//                List<PoMaterials> poMaterials = new PoMaterialsDao().getAll(entry.getOrder().getPoNumber());
//                Set<String> set = new HashSet<>();
//
//                for (PoMaterials material : poMaterials) {
//                    set.add(material.getMCode());
//                }
//
//                hauliersClassification.learn(entry.getDetails().getHaulier(), set);
//
//            }
//
//            Set<String> poMaterials = new HashSet<>();
//            for (PoMaterials materials : new PoMaterialsDao().getAll(scheduleEntry.getOrder().getPoNumber())) {
//                poMaterials.add(materials.getMCode());
//            }
////            System.out.println(scheduleEntry.getSupplier().getSupplierName() + "   " + scheduleEntry.getOrder().getPoNumber() +"  " + hauliersClassification.classify(poMaterials).getCategory());
//
//
//            return hauliersClassification.classify(poMaterials).getCategory();
//    }
//
//
//    private static String getBay(List<ScheduleEntry> allSupplierEntries, String haulier){
//
//        List<String> bays = new ArrayList<>();
//
//        for (ScheduleEntry entry : allSupplierEntries) {
//            if (entry.getDetails().getHaulier().equalsIgnoreCase(haulier)) {
//                bays.add(entry.getDetails().getBay());
//            }
//        }
//
//        Map<String, Long> bay = bays.stream().collect(Collectors.groupingBy(b -> b, Collectors.counting()));
//        return getMostCommonString(bay);
//
//    }
//
//
//    private static int getPalletsCount(ScheduleEntry entry){
//
//        final int DEFAULT_PALLET_WEIGHT = 1000;
//        int pallets = 0;
//
//        System.out.println(entry.getSupplier().getSupplierName());
//        List<PoMaterials> list = new PoMaterialsDao().getAll(entry.getOrder().getPoNumber());
//        List<SupplierMaterials> supplierMaterials = new SupplierMaterialsDao().getAll(entry.getSupplier().getSupplierCode());
//
//        for (PoMaterials poMaterials : list) {
//            for (SupplierMaterials supplierMaterial : supplierMaterials) {
//                if (supplierMaterial.getmCode().equalsIgnoreCase(poMaterials.getMCode())) {
//                    if (supplierMaterial.getPalletWeight() > 0) {
//                        pallets += BigDecimal.valueOf(
//                                poMaterials.getExpectedQuantity() / supplierMaterial.getPalletWeight()).setScale(0, RoundingMode.CEILING).intValue();
//
//                    }else{
//                        pallets += BigDecimal.valueOf(
//                                poMaterials.getExpectedQuantity() / DEFAULT_PALLET_WEIGHT).setScale(0, RoundingMode.CEILING).intValue();
//                    }
//                }
//            }
//        }
//
//        if(pallets > 30){
//            int counter = pallets / 30;
//            for (int i = 0; i < counter; i++) {
//                new ScheduleDetailsDao().save(entry.getDetails());
//            }
//            return 26;
//        }
//
//        return pallets;
//
//    }
//
//
//    private static int getUnloadingTime(int pallets) {
//
//        int duration = 20;
//        duration += pallets *2 ;
//        return duration;
//
//    }
//
//
//    private static  String getMostCommonString(Map<String, Long> map){
//
//        String string = "";
//        long count = 0;
//
//        for (Map.Entry<String, Long> e : map.entrySet()) {
//            String k = e.getKey();
//            Long   v = e.getValue();
//            if (count > 0) {
//                if (v > count) {
//                    string = k;
//                    count = v;
//                }
//            }
//            else {
//                string = k;
//                count = v;
//            }
//        }
//        return string;
//
//    }

}








