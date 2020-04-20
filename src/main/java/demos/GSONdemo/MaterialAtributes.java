package demos.GSONdemo;

import java.time.LocalDate;
import java.util.Arrays;

public class MaterialAtributes {

    private String comments;
    private double density;
    private double lorryTemp;
    private double materialTemp;
    private double brix;
    private double pressure;
    private int length;
    private int width;
    private LocalDate date;

    private int[] a = {1, 2, 3, 4, 5, 6};


    public MaterialAtributes() {

    }


    public LocalDate getDate() {

        return date;
    }


    public int[] getA() {

        return a;
    }


    public void setA(int[] a) {

        this.a = a;
    }


    public void setDate(LocalDate date) {

        this.date = date;
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


    public double getBrix() {

        return brix;
    }


    public void setBrix(double brix) {

        this.brix = brix;
    }


    public double getPressure() {

        return pressure;
    }


    public void setPressure(double pressure) {

        this.pressure = pressure;
    }


    public int getLength() {

        return length;
    }


    public void setLength(int length) {

        this.length = length;
    }


    public int getWidth() {

        return width;
    }


    public void setWidth(int width) {

        this.width = width;
    }


    @Override
    public String toString() {

        return "MaterialAtributes{" +
               "comments='" + comments + '\'' +
               ", density=" + density +
               ", lorryTemp=" + lorryTemp +
               ", materialTemp=" + materialTemp +
               ", brix=" + brix +
               ", pressure=" + pressure +
               ", length=" + length +
               ", width=" + width +
               ", date=" + date +
               ", a=" + Arrays.toString(a) +
               '}';
    }
}
