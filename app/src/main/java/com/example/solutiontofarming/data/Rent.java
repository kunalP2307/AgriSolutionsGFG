package com.example.solutiontofarming.data;

import java.io.Serializable;

public class Rent implements Serializable {

    private String type;
    private double amount;
    private double share_percent;

    public Rent(String type, double amount, double share_percent) {
        this.type = type;
        this.amount = amount;
        this.share_percent = share_percent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getShare_percent() {
        return share_percent;
    }

    public void setShare_percent(double share_percent) {
        this.share_percent = share_percent;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "type='" + type + '\'' +
                ", amount='" + amount + '\'' +
                ", share_price='" + share_percent + '\'' +
                '}';
    }
}
