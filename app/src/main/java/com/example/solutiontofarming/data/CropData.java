package com.example.solutiontofarming.data;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CropData implements Serializable {

    String cropName;
    String cropGrowingDuration;
    String marketValue;
    String harvestingStorage;
    String yieldCrop;
    String pesticideUsed;
    String irrigationPractice;
    String fertilizerUsed;


    public CropData(String cropName,
                             String cropGrowingDuration,
                             String marketValue,
                             String harvestingStorage,
                             String yieldCrop,
                             String pesticideUsed,
                             String irrigationPractice,
                             String fertilizerUsed,
                             String landId,
                             String landArea,
                             String soilType,
                             String location,
                             String avgRainFall,
                             String avgTemperature,
                             String growingDurationSeason,
                             String waterSupply,
    String spinWaterSource) {
        this.cropName = cropName;
        this.cropGrowingDuration = cropGrowingDuration;
        this.marketValue = marketValue;
        this.harvestingStorage = harvestingStorage;
        this.yieldCrop = yieldCrop;
        this.pesticideUsed = pesticideUsed;
        this.irrigationPractice = irrigationPractice;
        this.fertilizerUsed = fertilizerUsed;

    }

    public CropData(String cropName, String cropGrowingDuration, String marketValue, String harvestingStorage, String yieldCrop, String pesticideUsed, String irrigationPractice, String fertilizerUsed) {
        this.cropName = cropName;
        this.cropGrowingDuration = cropGrowingDuration;
        this.marketValue = marketValue;
        this.harvestingStorage = harvestingStorage;
        this.yieldCrop = yieldCrop;
        this.pesticideUsed = pesticideUsed;
        this.irrigationPractice = irrigationPractice;
        this.fertilizerUsed = fertilizerUsed;
    }

    public String getCropName() {
        return cropName;
    }

    public String getCropGrowingDuration() {
        return cropGrowingDuration;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public String getHarvestingStorage() {
        return harvestingStorage;
    }

    public String getYieldCrop() {
        return yieldCrop;
    }

    public String getPesticideUsed() {
        return pesticideUsed;
    }

    public String getIrrigationPractice() {
        return irrigationPractice;
    }

    public String getFertilizerUsed() {
        return fertilizerUsed;
    }


    @NonNull
    @Override
    public String toString() {
        return "CropData{" +
                "cropName='" + cropName + '\'' +
                ", cropGrowingDuration='" + cropGrowingDuration + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", harvestingStorage='" + harvestingStorage + '\'' +
                ", yieldCrop='" + yieldCrop + '\'' +
                ", pesticideUsed='" + pesticideUsed + '\'' +
                ", irrigationPractice=" + irrigationPractice + '\'' +
                ", fertilizerUsed=" + fertilizerUsed +
                '}';
    }
}
