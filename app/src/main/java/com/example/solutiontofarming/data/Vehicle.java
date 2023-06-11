package com.example.solutiontofarming.data;

import java.io.Serializable;

public class Vehicle implements Serializable {
    String type, containerType,registrationNo, availableLimit,weightUnit;

    public Vehicle(){}

    public Vehicle(String type, String containerType, String registrationNo, String availableLimit, String weightUnit) {
        this.type = type;
        this.containerType = containerType;
        this.registrationNo = registrationNo;
        this.availableLimit = availableLimit;
        this.weightUnit = weightUnit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(String availableLimit) {
        this.availableLimit = availableLimit;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "type='" + type + '\'' +
                ", containerType='" + containerType + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
                ", availableLimit='" + availableLimit + '\'' +
                ", weightUnit='" + weightUnit + '\'' +
                '}';
    }
}
