package com.example.solutiontofarming.data;

import java.io.Serializable;

public class AgriculturalEquipment implements Serializable {

    private String equipmentProviderId;
    private String providerName;
    private String providerContact;
    private String equipmentCategory;
    private String equipmentName;
    private String rentPerHour;
    private String additionalDetails;
    private String equipmentLocation;
    private String status;

    public AgriculturalEquipment(){

    }

    public AgriculturalEquipment(String equipmentProviderId, String providerName, String providerContact, String equipmentCategory, String equipmentName, String rentPerHour, String additionalDetails, String equipmentLocation) {
        this.equipmentProviderId = equipmentProviderId;
        this.providerName = providerName;
        this.providerContact = providerContact;
        this.equipmentCategory = equipmentCategory;
        this.equipmentName = equipmentName;
        this.rentPerHour = rentPerHour;
        this.additionalDetails = additionalDetails;
        this.equipmentLocation = equipmentLocation;
        this.status = "Visible to All";
    }

    public String getEquipmentProviderId() {
        return equipmentProviderId;
    }

    public void setEquipmentProviderId(String equipmentProviderId) {
        this.equipmentProviderId = equipmentProviderId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderContact() {
        return providerContact;
    }

    public void setProviderContact(String providerContact) {
        this.providerContact = providerContact;
    }

    public String getEquipmentCategory() {
        return equipmentCategory;
    }

    public void setEquipmentCategory(String equipmentCategory) {
        this.equipmentCategory = equipmentCategory;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getRentPerHour() {
        return rentPerHour;
    }

    public void setRentPerHour(String rentPerHour) {
        this.rentPerHour = rentPerHour;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public String getEquipmentLocation() {
        return equipmentLocation;
    }

    public void setEquipmentLocation(String equipmentLocation) {
        this.equipmentLocation = equipmentLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
