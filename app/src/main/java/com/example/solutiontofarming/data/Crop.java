package com.example.solutiontofarming.data;

public class Crop {
    private String cropName;
    private String plantingSeason;
    private String marketValue;

    public Crop(String cropName, String plantingSeason, String marketValue) {
        this.cropName = cropName;
        this.plantingSeason = plantingSeason;
        this.marketValue = marketValue;
    }

    public String getCropName() {
        return cropName;
    }

    public String getPlantingSeason() {
        return plantingSeason;
    }

    public String getMarketValue() {
        return marketValue;
    }
}
