package com.example.solutiontofarming.data;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class LandData implements Serializable {
    String landId;
    String landArea;
    String soilType;
    String location;
    String avgRainFall;
    String avgTemperature;
    String growingDurationSeason;
    String waterSupply;
    String spinWaterSource;

    public LandData(String landId, String landArea, String soilType, String location, String avgRainFall, String avgTemperature, String growingDurationSeason, String waterSupply, String spinWaterSource) {
        this.landId = landId;
        this.landArea = landArea;
        this.soilType = soilType;
        this.location = location;
        this.avgRainFall = avgRainFall;
        this.avgTemperature = avgTemperature;
        this.growingDurationSeason = growingDurationSeason;
        this.waterSupply = waterSupply;
        this.spinWaterSource = spinWaterSource;
    }

    public String getLandId() {
        return landId;
    }

    public String getLandArea() {
        return landArea;
    }

    public String getSoilType() {
        return soilType;
    }

    public String getLocation() {
        return location;
    }

    public String getAvgRainFall() {
        return avgRainFall;
    }

    public String getAvgTemperature() {
        return avgTemperature;
    }

    public String getGrowingDurationSeason() {
        return growingDurationSeason;
    }

    public String getWaterSupply() {
        return waterSupply;
    }

    public String getSpinWaterSource() {
        return spinWaterSource;
    }

    @NonNull
    @Override
    public String toString() {
        return "LandData{" +
                ", landId=" + landId + '\'' +
                ", landArea=" + landArea + '\'' +
                ", soilType=" + soilType + '\'' +
                ", location=" + location + '\'' +
                ", avgRainFall=" + avgRainFall + '\'' +
                ", avgTemperature=" + avgTemperature + '\'' +
                ", growingDurationSeason=" + growingDurationSeason + '\'' +
                ", waterSupply=" + waterSupply + '\'' +
                ", spinWaterSource=" + spinWaterSource+
                "}";
    }
}
