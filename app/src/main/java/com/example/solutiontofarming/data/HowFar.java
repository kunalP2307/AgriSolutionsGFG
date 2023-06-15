package com.example.solutiontofarming.data;

import java.io.Serializable;

public class HowFar implements Serializable {
    double farFromSource, farFromDestination;

    public HowFar(){}

    public HowFar(double farFromSource, double farFromDestination) {
        this.farFromSource = farFromSource;
        this.farFromDestination = farFromDestination;
    }

    public double getFarFromSource() {
        return farFromSource;
    }

    public void setFarFromSource(double farFromSource) {
        this.farFromSource = farFromSource;
    }

    public double getFarFromDestination() {
        return farFromDestination;
    }

    public void setFarFromDestination(double farFromDestination) {
        this.farFromDestination = farFromDestination;
    }
}
