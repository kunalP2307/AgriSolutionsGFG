package com.example.solutiontofarming.data;

import java.io.Serializable;

public class Field implements Serializable {
    private String type;
    private double area;
    private String unit;

    public Field(String type, double area, String unit) {
        this.type = type;
        this.area = area;
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Field{" +
                "type='" + type + '\'' +
                ", area='" + area + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
