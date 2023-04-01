package com.example.solutiontofarming.data;

import java.io.Serializable;

public class AgriculturalLand implements Serializable {

    private String providerId;
    private String providerName;
    private String providerPhone;
    private String landArea;
    private String landAddress;
    private String landLatitude;
    private String landLongitude;
    private String landType;
    private String rentPerYear;
    private String landStatus;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }

    public String getLandArea() {
        return landArea;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    public String getLandAddress() {
        return landAddress;
    }

    public void setLandAddress(String landAddress) {
        this.landAddress = landAddress;
    }

    public String getLandLatitude() {
        return landLatitude;
    }

    public void setLandLatitude(String landLatitude) {
        this.landLatitude = landLatitude;
    }

    public String getLandLongitude() {
        return landLongitude;
    }

    public void setLandLongitude(String landLongitude) {
        this.landLongitude = landLongitude;
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getRentPerYear() {
        return rentPerYear;
    }

    public void setRentPerYear(String rentPerYear) {
        this.rentPerYear = rentPerYear;
    }

    public String getLandStatus() {
        return landStatus;
    }

    public void setLandStatus(String landStatus) {
        this.landStatus = landStatus;
    }

    public AgriculturalLand(){

    }

    public AgriculturalLand(String providerId, String providerName, String providerPhone, String landArea, String landAddress, String landLatitude, String landLongitude, String landType, String rentPerYear) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.providerPhone = providerPhone;
        this.landArea = landArea;
        this.landAddress = landAddress;
        this.landLatitude = landLatitude;
        this.landLongitude = landLongitude;
        this.landType = landType;
        this.rentPerYear = rentPerYear;
        this.landStatus = "Visible To All";

    }
}
