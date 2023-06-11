package com.example.solutiontofarming.data;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class TransportRide implements Serializable {
    String userId;
    Address source,destination;
    String route;
    RideTime when;
    Vehicle vehicle;
    Goods goods;
    Fare fare;
    Driver driver;

    public TransportRide() {
    }

    public TransportRide(String userId, Address source, Address destination, String route, RideTime when, Vehicle vehicle, Goods goods, Fare fare, Driver driver) {
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.route = route;
        this.when = when;
        this.vehicle = vehicle;
        this.goods = goods;
        this.fare = fare;
        this.driver = driver;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Address getSource() {
        return source;
    }

    public void setSource(Address source) {
        this.source = source;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public RideTime getWhen() {
        return when;
    }

    public void setWhen(RideTime when) {
        this.when = when;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "TransportRide{" +
                "userId='" + userId + '\'' +
                ", source=" + source +
                ", destination=" + destination +
                ", route='" + route + '\'' +
                ", when=" + when +
                ", vehicle=" + vehicle +
                ", goods=" + goods +
                ", fare=" + fare +
                ", driver=" + driver +
                '}';
    }
}
