package com.example.solutiontofarming.data;

public class Land {
    String landId ;
    String landArea;
    String cropRecommend;

    public Land(String landId, String landArea, String cropName) {
        this.landArea = landArea;
        this.landId = landId;
        this.cropRecommend = cropName;
    }

    public String getLandId() {
        return landId;
    }

    public String getLandArea() {
        return landArea;
    }

    public String getCropRecommend() {
        return cropRecommend;
    }
}
