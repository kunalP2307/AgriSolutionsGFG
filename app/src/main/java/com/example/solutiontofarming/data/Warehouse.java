package com.example.solutiontofarming.data;

import java.io.Serializable;

public class Warehouse implements Serializable {

    String userId;
    Address address;
    Owner owner;
    String freeSpace,area,description;
    InfraFacilities infraFacilities;
    Lease lease;
    String rent;

    public Warehouse() {
    }

    public Warehouse(String userId,Address address, Owner owner, String freeSpace, String area, String description, InfraFacilities infraFacilities, Lease lease, String rent) {
        this.userId = userId;
        this.address = address;
        this.owner = owner;
        this.freeSpace = freeSpace;
        this.area = area;
        this.description = description;
        this.infraFacilities = infraFacilities;
        this.lease = lease;
        this.rent = rent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(String freeSpace) {
        this.freeSpace = freeSpace;
    }

    public String getCeilingHeight() {
        return area;
    }

    public void setCeilingHeight(String ceilingHeight) {
        this.area = ceilingHeight;
    }

    public InfraFacilities getInfraFacilities() {
        return infraFacilities;
    }

    public void setInfraFacilities(InfraFacilities infraFacilities) {
        this.infraFacilities = infraFacilities;
    }

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "address=" + address +
                ", owner=" + owner +
                ", freeSpace='" + freeSpace + '\'' +
                ", ceilingHeight='" + area + '\'' +
                ", infraFacilities=" + infraFacilities +
                ", lease=" + lease +
                ", rent='" + rent + '\'' +
                '}';
    }
}
