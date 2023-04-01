package com.example.solutiontofarming.data;

import java.io.Serializable;

public class TransportDetails implements Serializable {
    private String driverName;
    private String driverPhoneNo;
    private String vehicleNo;
    private String goodsLimit;
    private String transportDate;
    private String source;
    private String destination;

    public TransportDetails(){

    }
    public TransportDetails(String driverName, String driverPhoneNo, String vehicleNo, String goodsLimit, String transportDate, String source, String destination) {
        this.driverName = driverName;
        this.driverPhoneNo = driverPhoneNo;
        this.vehicleNo = vehicleNo;
        this.goodsLimit = goodsLimit;
        this.transportDate = transportDate;
        this.source = source;
        this.destination = destination;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhoneNo() {
        return driverPhoneNo;
    }

    public void setDriverPhoneNo(String driverPhoneNo) {
        this.driverPhoneNo = driverPhoneNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getGoodsLimit() {
        return goodsLimit;
    }

    public void setGoodsLimit(String goodsLimit) {
        this.goodsLimit = goodsLimit;
    }

    public String getTransportDate() {
        return transportDate;
    }

    public void setTransportDate(String transportDate) {
        this.transportDate = transportDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
