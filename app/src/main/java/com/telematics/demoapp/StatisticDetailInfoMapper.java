package com.telematics.demoapp;


import com.github.mikephil.charting.data.Entry;
import com.raxeltelematics.v2.sdk.server.model.sdk.DiagramEntity;
import com.raxeltelematics.v2.sdk.server.model.sdk.DrivingDetails;
import com.raxeltelematics.v2.sdk.server.model.sdk.DrivingTimeDetails;
import com.raxeltelematics.v2.sdk.server.model.sdk.MileageDetails;
import com.raxeltelematics.v2.sdk.server.model.sdk.PhoneDetails;
import com.raxeltelematics.v2.sdk.server.model.sdk.SpeedDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatisticDetailInfoMapper {

    private static SimpleDateFormat fullDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());

    public static StatisticInfoModel convert(DrivingDetails response) {
        return new StatisticInfoModel(
                response.getDrivingRating(),
                response.getHeavyBrakingFrequencyFor100Km(),
                response.getActiveAccelerationFrequencyFor100Km(),
                response.getHeavyBrakingCountForPeriod(),
                response.getActiveAccelerationCountForPeriod(),
                convertGraph(response.getHeavyBrakingDiagrams()),
                convertGraph(response.getActiveAccelerationDiagram()),
                Collections.emptyList(),
                "Maneuver",
                StatisticInfoModel.TYPE_MANEUVERS,
                new Date());
    }

    public static StatisticInfoModel convert(MileageDetails response) {
        int averageMileageForWeek = (int) response.getAverageMileageForWeek();
        int averageMileageForMonth = (int) response.getAverageMileageForMonth();
        int mileageSummary = (int) response.getMileageSummary();
        int expectedYearMileage = (int) response.getExpectedYearMileage();

        return new StatisticInfoModel(
                response.getMileageRating(),
                averageMileageForWeek,
                averageMileageForMonth,
                mileageSummary,
                expectedYearMileage,
                convertGraph(response.getMileageDiagram()),
                Collections.emptyList(),
                Collections.emptyList(),
                "Mileage",
                StatisticInfoModel.TYPE_MILEAGE,
                new Date()
        );
    }

    public static StatisticInfoModel convert(SpeedDetails response) {
        int drivingOverSpeedLimitFor100Km = (int) response.getDrivingOverSpeedLimitFor100Km();
        int drivingOverSpeedLimitMore20KmFor100Km = (int) response.getDrivingOverSpeedLimitMore20KmFor100Km();
        return
                new StatisticInfoModel(
                        response.getSpeedRating(),
                        drivingOverSpeedLimitFor100Km,
                        drivingOverSpeedLimitMore20KmFor100Km,
                        response.getMaximumSpeed(),
                        response.getAverageSpeed(),
                        convertGraph(response.getDrivingOverSpeedLimitDiagram()),
                        convertGraph(response.getDrivingOverSpeedLimitMore20KmDiagram()),
                        null,
                        "Speeding",
                        StatisticInfoModel.TYPE_SPEEDING,
                        new Date()
                );
    }

    public static StatisticInfoModel convert(DrivingTimeDetails response) {

        return
                new StatisticInfoModel(
                        (int) response.getRatingTimeOfDay(),
                        (int) response.getNightHours(),
                        (int) response.getDailyAverageDrivingTimeMin(),
                        (int) response.getRushHours(),
                        (int) response.getTotalDriveTimeHours(),
                        convertGraph(response.getNightHoursDiagram()),
                        convertGraph(response.getRushHoursDiagram()),
                        convertGraph(response.getDayHoursDiagram()),
                        "Maneuvers",
                        StatisticInfoModel.TYPE_DRIVING_TIME,
                        new Date()
                );
    }

    public static StatisticInfoModel convert(PhoneDetails response) {
        return
                new StatisticInfoModel(
                        response.getRating(),
                        response.getPhoneUse(),
                        response.getPhoneUseOver20km(),
                        null,
                        null,
                        convertGraph(response.getUsingPhoneWhileDrivingDiagram()),
                        convertGraph(response.getUsingPhoneWhileDrivingOverSpeedLimitDiagram()),
                        null,
                        "Phone Usage",
                        StatisticInfoModel.TYPE_PHONE_USAGE,
                        new Date()
                );
    }

    private static List<Entry> convertGraph(DiagramEntity[] diagrams) {
        if (diagrams == null) {
            return null;
        }
        ArrayList<Entry> entries = new ArrayList<>(diagrams.length + 2);
        for (int i = 0; i < diagrams.length; i++) {
            DiagramEntity diagram = diagrams[i];
            Date date = parseDate(diagram.getDate());
            entries.add(new Entry(i + 1, diagram.getValue(), date));
        }

        if (!entries.isEmpty()) {
            entries.add(0, new Entry(0, 0));
            Entry object1 = entries.get(entries.size() - 1);
            entries.add(new Entry(object1.getX() + 1, 0));
        }

        return entries;
    }

    private static Date parseDate(String startDate) {
        try {
            return fullDate.parse(startDate);
        } catch (ParseException e) {
            return null;
        }
    }

}
