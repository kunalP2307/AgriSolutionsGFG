package com.example.solutiontofarming.data;

import java.io.Serializable;
import java.util.List;

public class AgriEquipment implements Serializable {
    String userId;
    String category,name,yearsUsed,rentPerDay;
    String imageUrl;
    List<Availability> availabilities;
    Address address;
    Owner owner;

    public AgriEquipment() {
    }

    public AgriEquipment(String userId, String category, String name, String yearsUsed, String rentPerDay, String imageUrl, List<Availability> availabilities, Address address, Owner owner) {
        this.userId = userId;
        this.category = category;
        this.name = name;
        this.yearsUsed = yearsUsed;
        this.rentPerDay = rentPerDay;
        this.imageUrl = imageUrl;
        this.availabilities = availabilities;
        this.address = address;
        this.owner = owner;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearsUsed() {
        return yearsUsed;
    }

    public void setYearsUsed(String yearsUsed) {
        this.yearsUsed = yearsUsed;
    }

    public String getRentPerDay() {
        return rentPerDay;
    }

    public void setRentPerDay(String rentPerDay) {
        this.rentPerDay = rentPerDay;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "AgriEquipment{" +
                "userId='" + userId + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", yearsUsed='" + yearsUsed + '\'' +
                ", rentPerDay='" + rentPerDay + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", availabilities=" + availabilities +
                ", address=" + address +
                ", owner=" + owner +
                '}';
    }
}
