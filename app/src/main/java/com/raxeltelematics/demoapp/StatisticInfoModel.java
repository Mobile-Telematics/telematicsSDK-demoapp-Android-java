package com.raxeltelematics.demoapp;


import com.github.mikephil.charting.data.Entry;

import java.util.Date;
import java.util.List;


public class StatisticInfoModel {

    final static int TYPE_MANEUVERS = 0;
    final static int TYPE_SPEEDING = 1;
    final static int TYPE_MILEAGE = 2;
    final static int TYPE_PHONE_USAGE = 3;
    final static int TYPE_DRIVING_TIME = 4;

    private int rating;
    private int topLeftValue;
    private int topRightValue;
    private Integer bottomLeftValue;
    private Integer bottomRightValue;

    private List<Entry> grafhPoints1;
    private List<Entry> grafhPoints2;
    private List<Entry> grafhPoints3;
    private String subtitleText;
    private int type;
    private Date date;

    public StatisticInfoModel(
            int rating,
            int topLeftValue,
            int topRightValue,
            Integer bottomLeftValue,
            Integer bottomRightValue,
            List<Entry> grafhPoints1,
            List<Entry> grafhPoints2,
            List<Entry> grafhPoints3,
            String subtitleText,
            int type,
            Date date
    ) {
        this.rating = rating;
        this.topLeftValue = topLeftValue;
        this.topRightValue = topRightValue;
        this.bottomLeftValue = bottomLeftValue;
        this.bottomRightValue = bottomRightValue;
        this.grafhPoints1 = grafhPoints1;
        this.grafhPoints2 = grafhPoints2;
        this.grafhPoints3 = grafhPoints3;
        this.subtitleText = subtitleText;
        this.type = type;
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public int getTopLeftValue() {
        return topLeftValue;
    }

    public int getTopRightValue() {
        return topRightValue;
    }

    public Integer getBottomLeftValue() {
        return bottomLeftValue;
    }

    public Integer getBottomRightValue() {
        return bottomRightValue;
    }

    public List<Entry> getGrafhPoints1() {
        return grafhPoints1;
    }

    public List<Entry> getGrafhPoints2() {
        return grafhPoints2;
    }

    public List<Entry> getGrafhPoints3() {
        return grafhPoints3;
    }

    public String getSubtitleText() {
        return subtitleText;
    }

    public int getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

}
