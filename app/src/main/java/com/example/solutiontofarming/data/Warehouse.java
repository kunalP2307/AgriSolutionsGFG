package com.example.solutiontofarming.data;

import java.io.Serializable;

public class Warehouse implements Serializable {
    Address address;
    Owner owner;
    String freeSpace,ceilingHeight;
    InfraFacilities infraFacilities;
    Lease lease;
    String rent;

    public Warehouse() {
    }

    public Warehouse(Address address, Owner owner, String freeSpace, String ceilingHeight, InfraFacilities infraFacilities, Lease lease, String rent) {
        this.address = address;
        this.owner = owner;
        this.freeSpace = freeSpace;
        this.ceilingHeight = ceilingHeight;
        this.infraFacilities = infraFacilities;
        this.lease = lease;
        this.rent = rent;
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
        return ceilingHeight;
    }

    public void setCeilingHeight(String ceilingHeight) {
        this.ceilingHeight = ceilingHeight;
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
                ", ceilingHeight='" + ceilingHeight + '\'' +
                ", infraFacilities=" + infraFacilities +
                ", lease=" + lease +
                ", rent='" + rent + '\'' +
                '}';
    }
}
