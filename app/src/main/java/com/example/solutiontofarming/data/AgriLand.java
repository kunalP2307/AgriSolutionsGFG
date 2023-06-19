package com.example.solutiontofarming.data;


import java.io.Serializable;

public class AgriLand implements Serializable {

    private Field field;
    private String description;
    private Address address;
    private Lease lease;
    private Rent rent;
    private Owner owner;
    private LandUser user;

    public AgriLand(Field field, String description, Address address, Lease lease, Rent rent, Owner owner, LandUser user) {
        this.field = field;
        this.description = description;
        this.address = address;
        this.lease = lease;
        this.rent = rent;
        this.owner = owner;
        this.user = user;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
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

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LandUser getUser() {
        return user;
    }

    public void setUser(LandUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AgriLand{" +
                "field=" + field +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", lease=" + lease +
                ", rent=" + rent +
                ", owner=" + owner +
                ", user=" + user +
                '}';
    }
}
