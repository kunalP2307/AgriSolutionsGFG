package com.example.solutiontofarming.data;

import java.io.Serializable;

public class RideTime implements Serializable {
    String date,time,flexWithDate,noOfDays,flexWithTime;

    public RideTime(){}

    public RideTime(String date, String time, String flexWithDate, String noOfDays, String flexWithTime) {
        this.date = date;
        this.time = time;
        this.flexWithDate = flexWithDate;
        this.noOfDays = noOfDays;
        this.flexWithTime = flexWithTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFlexWithDate() {
        return flexWithDate;
    }

    public void setFlexWithDate(String flexWithDate) {
        this.flexWithDate = flexWithDate;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getFlexWithTime() {
        return flexWithTime;
    }

    public void setFlexWithTime(String flexWithTime) {
        this.flexWithTime = flexWithTime;
    }

    @Override
    public String toString() {
        return "RideTime{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", flexWithDate='" + flexWithDate + '\'' +
                ", noOfDays='" + noOfDays + '\'' +
                ", flexWithTime='" + flexWithTime + '\'' +
                '}';
    }
}
