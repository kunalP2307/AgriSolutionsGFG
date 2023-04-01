package com.example.solutiontofarming.data;

import java.io.Serializable;

public class Transport implements Serializable {

    private String rideProviderId;
    private String driverName;
    private String drivePhone;
    private String vehicleType;
    private String vehicleNo;
    private String availableLoad;
    private String rideDate;
    private String rideTime;
    private String sourceAddress;
    private String destinationAddress;
    private String geoPoints;
    private String rideDescription;
    private String rideStatus;
    private String pricePerKm;



    public Transport(){

    }

    public Transport(String rideProviderId, String driverName, String drivePhone, String vehicleType, String vehicleNo, String availableLoad, String rideDate, String rideTime, String sourceAddress, String destinationAddress, String geoPoints, String rideDescription,String pricePerKm) {
        this.rideProviderId = rideProviderId;
        this.driverName = driverName;
        this.drivePhone = drivePhone;
        this.vehicleType = vehicleType;
        this.vehicleNo = vehicleNo;
        this.availableLoad = availableLoad;
        this.rideDate = rideDate;
        this.rideTime = rideTime;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.geoPoints = geoPoints;
        this.rideDescription = rideDescription;
        this.pricePerKm = pricePerKm;
        this.rideStatus = "init";

    }

    public String getRideProviderId() {
        return rideProviderId;
    }

    public void setRideProviderId(String rideProviderId) {
        this.rideProviderId = rideProviderId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDrivePhone() {
        return drivePhone;
    }

    public void setDrivePhone(String drivePhone) {
        this.drivePhone = drivePhone;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getAvailableLoad() {
        return availableLoad;
    }

    public void setAvailableLoad(String availableLoad) {
        this.availableLoad = availableLoad;
    }

    public String getRideDate() {
        return rideDate;
    }

    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
    }

    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getGeoPoints() {
        return geoPoints;
    }

    public void setGeoPoints(String geoPoints) {
        this.geoPoints = geoPoints;
    }

    public String getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(String rideStatus) {
        this.rideStatus = rideStatus;
    }

    public void setRideDescription(String rideDescription){
        this.rideDescription = rideDescription;
    }
    public String getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(String pricePerKm) {
        this.pricePerKm = pricePerKm;
    }
    public String getRideDescription(){
        return rideDescription;
    }
}
