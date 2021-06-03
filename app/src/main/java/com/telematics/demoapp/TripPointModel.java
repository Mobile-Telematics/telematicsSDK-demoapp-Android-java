package com.telematics.demoapp;

public class TripPointModel {
    double latitude;
    double longitude;
    int speedColor;
    int alertTypeImage;
    boolean usePhone;

    public TripPointModel(double latitude, double longitude, int speedColor, int alertTypeImage, boolean usePhone) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speedColor = speedColor;
        this.alertTypeImage = alertTypeImage;
        this.usePhone = usePhone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getSpeedColor() {
        return speedColor;
    }

    public void setSpeedColor(int speedColor) {
        this.speedColor = speedColor;
    }

    public int getAlertTypeImage() {
        return alertTypeImage;
    }

    public void setAlertTypeImage(int alertTypeImage) {
        this.alertTypeImage = alertTypeImage;
    }

    public boolean isUsePhone() {
        return usePhone;
    }

    public void setUsePhone(boolean usePhone) {
        this.usePhone = usePhone;
    }
}
