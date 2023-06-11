package com.example.solutiontofarming.data;

import java.io.Serializable;

public class Goods implements Serializable {
    String category,name;

    public Goods(){}

    public Goods(String category, String name) {
        this.category = category;
        this.name = name;
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

    @Override
    public String toString() {
        return "Goods{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
