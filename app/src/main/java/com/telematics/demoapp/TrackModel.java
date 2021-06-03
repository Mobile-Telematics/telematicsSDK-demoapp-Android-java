package com.telematics.demoapp;

import androidx.annotation.Nullable;

public class TrackModel {
    private String addressStart;
    private String addressEnd;
    private String endDate;
    private String startDate;
    private String trackId;
    private int accelerationCount;
    private int decelerationCount;
    private double distance;
    private double duration;
    private double rating;
    private double phoneUsage;
    private String originalCode;
    private Boolean hasOriginChanged;
    private double midOverSpeedMileage;
    private double highOverSpeedMileage;
    private String drivingTips;
    private String shareType;
    private String cityStart;
    private String cityFinish;

    public String getAddressStart() {
        return addressStart;
    }

    public void setAddressStart(String addressStart) {
        this.addressStart = addressStart;
    }

    public String getAddressEnd() {
        return addressEnd;
    }

    public void setAddressEnd(String addressEnd) {
        this.addressEnd = addressEnd;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public int getAccelerationCount() {
        return accelerationCount;
    }

    public void setAccelerationCount(int accelerationCount) {
        this.accelerationCount = accelerationCount;
    }

    public int getDecelerationCount() {
        return decelerationCount;
    }

    public void setDecelerationCount(int decelerationCount) {
        this.decelerationCount = decelerationCount;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPhoneUsage() {
        return phoneUsage;
    }

    public void setPhoneUsage(double phoneUsage) {
        this.phoneUsage = phoneUsage;
    }

    public String getOriginalCode() {
        return originalCode;
    }

    public void setOriginalCode(String originalCode) {
        this.originalCode = originalCode;
    }

    public Boolean getHasOriginChanged() {
        return hasOriginChanged;
    }

    public void setHasOriginChanged(Boolean hasOriginChanged) {
        this.hasOriginChanged = hasOriginChanged;
    }

    public double getMidOverSpeedMileage() {
        return midOverSpeedMileage;
    }

    public void setMidOverSpeedMileage(double midOverSpeedMileage) {
        this.midOverSpeedMileage = midOverSpeedMileage;
    }

    public double getHighOverSpeedMileage() {
        return highOverSpeedMileage;
    }

    public void setHighOverSpeedMileage(double highOverSpeedMileage) {
        this.highOverSpeedMileage = highOverSpeedMileage;
    }

    public String getDrivingTips() {
        return drivingTips;
    }

    public void setDrivingTips(String drivingTips) {
        this.drivingTips = drivingTips;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getCityStart() {
        return cityStart;
    }

    public void setCityStart(String cityStart) {
        this.cityStart = cityStart;
    }

    public String getCityFinish() {
        return cityFinish;
    }

    public void setCityFinish(String cityFinish) {
        this.cityFinish = cityFinish;
    }

    public TrackModel(@Nullable String addressStart,
                      @Nullable String addressEnd,
                      @Nullable String endDate,
                      @Nullable String startDate,
                      @Nullable String trackId,
                      int accelerationCount,
                      int decelerationCount,
                      double distance,
                      double duration,
                      double rating,
                      double phoneUsage,
                      @Nullable String originalCode,
                      boolean hasOriginChanged,
                      double midOverSpeedMileage,
                      double highOverSpeedMileage,
                      @Nullable String drivingTips,
                      @Nullable String shareType,
                      @Nullable String cityStart,
                      @Nullable String cityFinish) {
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
        this.endDate = endDate;
        this.startDate = startDate;
        this.trackId = trackId;
        this.accelerationCount = accelerationCount;
        this.decelerationCount = decelerationCount;
        this.distance = distance;
        this.duration = duration;
        this.rating = rating;
        this.phoneUsage = phoneUsage;
        this.originalCode = originalCode;
        this.hasOriginChanged = hasOriginChanged;
        this.midOverSpeedMileage = midOverSpeedMileage;
        this.highOverSpeedMileage = highOverSpeedMileage;
        this.drivingTips = drivingTips;
        this.shareType = shareType;
        this.cityStart = cityStart;
        this.cityFinish = cityFinish;
    }


}