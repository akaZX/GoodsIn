package app.pojos;

import org.intellij.lang.annotations.Language;

import java.math.BigDecimal;
import java.util.List;


//THIS CLASS WILL BE STORED AS JSON STRING IN DATABASE
public class RmtQaIntakeDetails {

    private int yield;

    private int yieldGross;

    private int yieldNet;

    private double lorryTemp;

    private double materialTemp;

    private double density;

    private int densityGrossWeight;

    private List<Integer> densityDiameters;

    private List<Double> brix;

    private List<Double> pressure;

    private List<Integer> length;

    private List<Integer> width;

    private List<Integer> colorStage;

    private List<Integer> headWeight;

    private String comments = "";

    private String variety = "";

    private String country = "";

    private String grower = "";

    private String harvestDate = "";

    private String lotNumber = "";

    private String day = "";

    private String room = "";

    private String rtaNumber = "";

    private String ggn = "";

    private String twa = "";

    private String healthMark = "";

    private String expiryDate = "";

    private String count = "";

    private String containerNo = "";

    private int samples;

    private boolean major;

    private boolean major1;

    private boolean major2;

    private boolean major3;

    private boolean major4;

    private boolean major5;

    private boolean major6;

    private boolean critical;

    private boolean critical1;

    private boolean critical2;

    private boolean critical3;

    private boolean critical4;

    private boolean critical5;

    private boolean critical6;

    private boolean minor;

    private boolean minor1;

    private boolean minor2;

    private boolean minor3;

    private boolean minor4;

    private boolean minor5;

    private boolean minor6;

    private String minorText = "";

    private String majorText = "";

    private String criticalText = "";


    public double getLorryTemp() {

        return lorryTemp;
    }


    public void setLorryTemp(double lorryTemp) {

        this.lorryTemp = lorryTemp;
    }


    public double getMaterialTemp() {

        return materialTemp;
    }


    public void setMaterialTemp(double materialTemp) {

        this.materialTemp = materialTemp;
    }


    public int getYield() {

        return yield;
    }


    public void setYield(int yield) {

        this.yield = yield;
    }


    public int getYieldGross() {

        return yieldGross;
    }


    public void setYieldGross(int yieldGross) {

        this.yieldGross = yieldGross;
    }


    public int getYieldNet() {

        return yieldNet;
    }


    public void setYieldNet(int yieldNet) {

        this.yieldNet = yieldNet;
    }


    public String getComments() {

        return comments;
    }


    public void setComments(String comments) {

        this.comments = comments;
    }


    public double getDensity() {

        return density;
    }


    public void setDensity(double density) {

        this.density = density;
    }


    public int getDensityGrossWeight() {

        return densityGrossWeight;
    }


    public void setDensityGrossWeight(int densityGrossWeight) {

        this.densityGrossWeight = densityGrossWeight;
    }


    public List<Integer> getDensityDiameters() {

        return densityDiameters;
    }


    public void setDensityDiameters(List<Integer> densityDiameters) {

        this.densityDiameters = densityDiameters;
    }


    public List<Double> getBrix() {

        return brix;
    }


    public void setBrix(List<Double> brix) {

        this.brix = brix;
    }


    public List<Double> getPressure() {

        return pressure;
    }


    public void setPressure(List<Double> pressure) {

        this.pressure = pressure;
    }


    public List<Integer> getLength() {

        return length;
    }


    public void setLength(List<Integer> length) {

        this.length = length;
    }


    public List<Integer> getWidth() {

        return width;
    }


    public void setWidth(List<Integer> width) {

        this.width = width;
    }


    public List<Integer> getColorStage() {

        return colorStage;
    }


    public void setColorStage(List<Integer> colorStage) {

        this.colorStage = colorStage;
    }


    public List<Integer> getHeadWeight() {

        return headWeight;
    }


    public void setHeadWeight(List<Integer> headWeight) {

        this.headWeight = headWeight;
    }


    public String getVariety() {

        return variety;
    }


    public void setVariety(String variety) {

        this.variety = variety;
    }


    public String getCountry() {

        return country;
    }


    public void setCountry(String country) {

        this.country = country;
    }


    public String getGrower() {

        return grower;
    }


    public void setGrower(String grower) {

        this.grower = grower;
    }


    public String getHarvestDate() {

        return harvestDate;
    }


    public void setHarvestDate(String harvestDate) {

        this.harvestDate = harvestDate;
    }


    public String getLotNumber() {

        return lotNumber;
    }


    public void setLotNumber(String lotNumber) {

        this.lotNumber = lotNumber;
    }


    public String getDay() {

        return day;
    }


    public void setDay(String day) {

        this.day = day;
    }


    public String getRoom() {

        return room;
    }


    public void setRoom(String room) {

        this.room = room;
    }


    public String getRtaNumber() {

        return rtaNumber;
    }


    public void setRtaNumber(String rtaNumber) {

        this.rtaNumber = rtaNumber;
    }


    public String getGgn() {

        return ggn;
    }


    public void setGgn(String ggn) {

        this.ggn = ggn;
    }


    public String getTwa() {

        return twa;
    }


    public void setTwa(String twa) {

        this.twa = twa;
    }


    public String getHealthMark() {

        return healthMark;
    }


    public void setHealthMark(String healthMark) {

        this.healthMark = healthMark;
    }


    public String getExpiryDate() {

        return expiryDate;
    }


    public void setExpiryDate(String expiryDate) {

        this.expiryDate = expiryDate;
    }


    public String getCount() {

        return count;
    }


    public void setCount(String count) {

        this.count = count;
    }


    public String getContainerNo() {

        return containerNo;
    }


    public void setContainerNo(String containerNo) {

        this.containerNo = containerNo;
    }


    public int getSamples() {

        return samples;
    }


    public void setSamples(int samples) {

        this.samples = samples;
    }


    public boolean isMajor() {

        return major;
    }


    public void setMajor(boolean major) {

        this.major = major;
    }


    public boolean isMajor1() {

        return major1;
    }


    public void setMajor1(boolean major1) {

        this.major1 = major1;
    }


    public boolean isMajor2() {

        return major2;
    }


    public void setMajor2(boolean major2) {

        this.major2 = major2;
    }


    public boolean isMajor3() {

        return major3;
    }


    public void setMajor3(boolean major3) {

        this.major3 = major3;
    }


    public boolean isMajor4() {

        return major4;
    }


    public void setMajor4(boolean major4) {

        this.major4 = major4;
    }


    public boolean isMajor5() {

        return major5;
    }


    public void setMajor5(boolean major5) {

        this.major5 = major5;
    }


    public boolean isMajor6() {

        return major6;
    }


    public void setMajor6(boolean major6) {

        this.major6 = major6;
    }


    public boolean isCritical() {

        return critical;
    }


    public void setCritical(boolean critical) {

        this.critical = critical;
    }


    public boolean isCritical1() {

        return critical1;
    }


    public void setCritical1(boolean critical1) {

        this.critical1 = critical1;
    }


    public boolean isCritical2() {

        return critical2;
    }


    public void setCritical2(boolean critical2) {

        this.critical2 = critical2;
    }


    public boolean isCritical3() {

        return critical3;
    }


    public void setCritical3(boolean critical3) {

        this.critical3 = critical3;
    }


    public boolean isCritical4() {

        return critical4;
    }


    public void setCritical4(boolean critical4) {

        this.critical4 = critical4;
    }


    public boolean isCritical5() {

        return critical5;
    }


    public void setCritical5(boolean critical5) {

        this.critical5 = critical5;
    }


    public boolean isCritical6() {

        return critical6;
    }


    public void setCritical6(boolean critical6) {

        this.critical6 = critical6;
    }


    public boolean isMinor() {

        return minor;
    }


    public void setMinor(boolean minor) {

        this.minor = minor;
    }


    public boolean isMinor1() {

        return minor1;
    }


    public void setMinor1(boolean minor1) {

        this.minor1 = minor1;
    }


    public boolean isMinor2() {

        return minor2;
    }


    public void setMinor2(boolean minor2) {

        this.minor2 = minor2;
    }


    public boolean isMinor3() {

        return minor3;
    }


    public void setMinor3(boolean minor3) {

        this.minor3 = minor3;
    }


    public boolean isMinor4() {

        return minor4;
    }


    public void setMinor4(boolean minor4) {

        this.minor4 = minor4;
    }


    public boolean isMinor5() {

        return minor5;
    }


    public void setMinor5(boolean minor5) {

        this.minor5 = minor5;
    }


    public boolean isMinor6() {

        return minor6;
    }


    public void setMinor6(boolean minor6) {

        this.minor6 = minor6;
    }


    public String getMinorText() {

        return minorText;
    }


    public void setMinorText(String minorText) {

        this.minorText = minorText;
    }


    public String getMajorText() {

        return majorText;
    }


    public void setMajorText(String majorText) {

        this.majorText = majorText;
    }


    public String getCriticalText() {

        return criticalText;
    }


    public void setCriticalText(String criticalText) {

        this.criticalText = criticalText;
    }


    @Override
    public String toString() {

        return
                "Yield: " + yield +
                "\nVariety: " + variety +
                "\nCountry: " + country +
                "\nComments: " + comments +
                "\nGrower: " + grower +

                "\nExpiry date: " + expiryDate +
                "\nLorry temp: " + lorryTemp +
                "\nMaterial temp: " + materialTemp +

                "\nHarvest date: " + harvestDate +
                "\nLot number: " + lotNumber +
                "\nGGN: " + ggn +
                "\nTWA: " + twa +
                "\nHealth mark: " + healthMark +
                "\nCount: " + count +
                "\nContainer No: " + containerNo +
                "\nRTA: " + rtaNumber +
                "\nRoom: " + room +
                "\nDay: " + day
                ;

    }


    public String getGeneralDetails() {

        @Language("HTML")
        String params = (variety != null && variety.length() > 0 ? "Variety: " + variety + "<br/>" : "") +
                        (country != null && country.length() > 0 ? "Country: " + country + "<br/>" : "") +
                        (grower != null && grower.length() > 0 ? "Grower: " + grower + "<br/>" + variety +
                                                                 "<br/>" : "") +
                        (expiryDate != null && expiryDate.length() > 0 ? "Expiry date: " + expiryDate + "<br/>" : "") +
                        (harvestDate != null && harvestDate.length() > 0 ? "Harvest date: " + harvestDate +
                                                                           "<br/>" : "") +
                        (lotNumber != null && lotNumber.length() > 0 ? "Lot number: " + lotNumber + "<br/>" : "") +
                        (ggn != null && ggn.length() > 0 ? "GGN: " + ggn + "<br/>" : "") +
                        (twa != null && twa.length() > 0 ? "TWA: " + twa + "<br/>" : "") +
                        (healthMark != null && healthMark.length() > 0 ? "Health mark: " + healthMark + "<br/>" : "") +
                        (count != null && count.length() > 0 ? "Count: " + count + "<br/>" : "") +
                        (containerNo != null && containerNo.length() > 0 ? "Container No: " + containerNo +
                                                                           "<br/>" : "") +
                        (rtaNumber != null && rtaNumber.length() > 0 ? "RTA: " + rtaNumber + "<br/>" : "") +
                        (room != null && room.length() > 0 ? "Room: " + room + "<br/>" : "") +
                        (day != null && day.length() > 0 ? "Day: " + day : "");


        return params;
    }


    public String getNumericalParams() {


        return (yield == 0 ? "" : "Yield: " + yield + "% <br/>") +
               (lorryTemp == 0 ? "" : "Lorry temperature: " + lorryTemp + "C <br/>") +
               (materialTemp == 0 ? "" : "Material temperature: " + materialTemp + "C <br/>") +
               (density == 0.0 ? "" : "Density: " + density + "<br/>") +
               ((brix != null && brix.size() > 0) ? "Brix:" + getDoubleListAverage(brix) + "<br/>" : "") +
               ((pressure != null && pressure.size() > 0) ? "Pressure: " + getDoubleListAverage(pressure) +
                                                            " <br/>" : "") +
               ((length != null && length.size() > 0) ? "Length: " + getIntListAverage(length) + "mm" + " <br/>" : "") +
               ((width != null && width.size() > 0) ? "Width: " + getIntListAverage(width) + "mm" + "<br/>" : "") +
               ((headWeight != null && headWeight.size() > 0) ? "Head weight: " + getIntListAverage(headWeight) +
                                                                "g" : "");

    }


    private double getIntListAverage(List<Integer> list) {

        int total = list.stream()
                .reduce(0, Integer::sum);

        return new BigDecimal(total / list.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    private double getDoubleListAverage(List<Double> list) {

        double total = list.stream()
                .reduce(0d, Double::sum);

        return new BigDecimal(total / list.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public String getDefectsText() {

        final String NONE = "NONE";
        return "Critical defects: " + (criticalText.length() > 2 ? criticalText : NONE) +
               "<br/>Major foreign bodies: " + (majorText.length() > 2 ? majorText : NONE) +
               "<br/>Non-critical defects: " + (criticalText.length() > 2 ? minorText : NONE);
    }


}
