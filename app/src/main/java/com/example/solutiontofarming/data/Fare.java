package com.example.solutiontofarming.data;

import java.io.Serializable;

public class Fare implements Serializable {
    String pricePerKm,onDemandAvailable;

    public Fare() {
    }

    public Fare(String pricePerKm, String onDemandAvailable) {
        this.pricePerKm = pricePerKm;
        this.onDemandAvailable = onDemandAvailable;
    }

    public String getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(String pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public String getOnDemandAvailable() {
        return onDemandAvailable;
    }

    public void setOnDemandAvailable(String onDemandAvailable) {
        this.onDemandAvailable = onDemandAvailable;
    }

    @Override
    public String toString() {
        return "Fare{" +
                "pricePerKm='" + pricePerKm + '\'' +
                ", onDemandAvailable='" + onDemandAvailable + '\'' +
                '}';
    }
}
