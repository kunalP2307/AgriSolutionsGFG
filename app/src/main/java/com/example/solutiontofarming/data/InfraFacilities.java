package com.example.solutiontofarming.data;

import java.io.Serializable;

public class InfraFacilities implements Serializable {
    String ventilation,temperatureControl;
    public InfraFacilities(){

    }

    public InfraFacilities(String ventilation, String temperatureControl) {
        this.ventilation = ventilation;
        this.temperatureControl = temperatureControl;
    }

    public String getVentilation() {
        return ventilation;
    }

    public void setVentilation(String ventilation) {
        this.ventilation = ventilation;
    }

    public String getTemperatureControl() {
        return temperatureControl;
    }

    public void setTemperatureControl(String temperatureControl) {
        this.temperatureControl = temperatureControl;
    }

    @Override
    public String toString() {
        return "InfraFacilities{" +
                "ventilation='" + ventilation + '\'' +
                ", temperatureControl='" + temperatureControl + '\'' +
                '}';
    }
}
