package com.example.solutiontofarming.data;

import java.io.Serializable;

public class Lease implements Serializable {
    String duration, startDate, endDate;

    public Lease() {
    }

    public Lease(String duration, String startDate, String endDate) {
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Lease{" +
                "duration='" + duration + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
