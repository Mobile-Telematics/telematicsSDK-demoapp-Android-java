package com.raxeltelematics.demoapp;

import android.content.Context;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class StatisticInfoTextFormatterImpl {

    private SimpleDateFormat dayMonth;
    private SimpleDateFormat time =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
    private SimpleDateFormat dayFormat;
    private Context context;

    public StatisticInfoTextFormatterImpl(Context context) {
        this.context = context;
        DateFormatSymbols value = new DateFormatSymbols();

        dayMonth = new SimpleDateFormat("MMMM d, yyyy", value);
        dayFormat = new SimpleDateFormat("d MMMM", Locale.getDefault());

    }

    public CharSequence formatTopLeftValue(final int type, int value) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return String.valueOf(value);
            case StatisticInfoModel.TYPE_MILEAGE:
                return context.getString(R.string.FORMAT_КМ, value);
            case StatisticInfoModel.TYPE_SPEEDING:
                return context.getString(R.string.FORMAT_КМ, value);
            case StatisticInfoModel.TYPE_PHONE_USAGE:
                return context.getString(R.string.FORMAT_MIN, value);
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return context.getString(R.string.FORMAT_HRS, value);
            default:
                return "";
        }
    }

    public CharSequence formatTopRightValue(int type, int value) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return String.valueOf(value);
            case StatisticInfoModel.TYPE_MILEAGE:
                return context.getString(R.string.FORMAT_КМ, value);
            case StatisticInfoModel.TYPE_SPEEDING:
                return context.getString(R.string.FORMAT_КМ, value);
            case StatisticInfoModel.TYPE_PHONE_USAGE:
                return context.getString(R.string.FORMAT_MIN, value);
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return context.getString(R.string.FORMAT_HRS, value);
            default:
                return "";
        }
    }

    public CharSequence formatBottomLeftValue(int type, Integer value) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return String.valueOf(value);
            case StatisticInfoModel.TYPE_MILEAGE:
                return context.getString(R.string.FORMAT_КМ, value);
            case StatisticInfoModel.TYPE_SPEEDING:
                return context.getString(R.string.FORMAT_KM_SPEED, value);
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return context.getString(R.string.FORMAT_HRS, value);
            case StatisticInfoModel.TYPE_PHONE_USAGE:
            default:
                return "";
        }
    }

    public CharSequence formatBottomRightValue(int type, Integer value) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return String.valueOf(value);
            case StatisticInfoModel.TYPE_MILEAGE:
                return context.getString(R.string.FORMAT_КМ, value);
            case StatisticInfoModel.TYPE_SPEEDING:
                return context.getString(R.string.FORMAT_KM_SPEED, value);
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return context.getString(R.string.FORMAT_HRS, value);
            case StatisticInfoModel.TYPE_PHONE_USAGE:
            default:
                return "";
        }
    }

    public CharSequence formatTopLeftSubTitleUnits(int type) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return context.getString(R.string.main_stat_times_label);
            case StatisticInfoModel.TYPE_MILEAGE:
                return context.getString(R.string.main_stat_km_label);
            case StatisticInfoModel.TYPE_SPEEDING:
                return context.getString(R.string.main_stat_km_label);
            case StatisticInfoModel.TYPE_PHONE_USAGE:
                return context.getString(R.string.main_stat_min_label);
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return context.getString(R.string.main_stat_h_label);
            default:
                return "";
        }
    }

    public CharSequence formatTopRightSubTitleUnits(int type) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return context.getString(R.string.main_stat_times_label);
            case StatisticInfoModel.TYPE_MILEAGE:
                return context.getString(R.string.main_stat_km_label);
            case StatisticInfoModel.TYPE_SPEEDING:
                return context.getString(R.string.main_stat_km_label);
            case StatisticInfoModel.TYPE_PHONE_USAGE:
                return context.getString(R.string.main_stat_min_label);
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return context.getString(R.string.main_stat_h_label);
            default:
                return "";
        }
    }

    public CharSequence formatBottomLeftSubTitleUnits(int type) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return context.getString(R.string.main_stat_times_label);
            case StatisticInfoModel.TYPE_MILEAGE:
                return context.getString(R.string.main_stat_km_label);
            case StatisticInfoModel.TYPE_SPEEDING:
                return context.getString(R.string.main_stat_km_per_h_label);
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return context.getString(R.string.main_stat_h_label);
            case StatisticInfoModel.TYPE_PHONE_USAGE:
            default:
                return "";
        }
    }

    public CharSequence formatBottomRightSubTitleUnits(int type) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return context.getString(R.string.main_stat_times_label);
            case StatisticInfoModel.TYPE_MILEAGE:
                return context.getString(R.string.main_stat_km_label);
            case StatisticInfoModel.TYPE_SPEEDING:
                return context.getString(R.string.main_stat_km_per_h_label);
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return context.getString(R.string.main_stat_h_label);
            case StatisticInfoModel.TYPE_PHONE_USAGE:
            default:
                return "";
        }
    }

    public int formatTopLeftTitle(int type) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return R.string.main_stat_maneuver_top_left_title;
            case StatisticInfoModel.TYPE_MILEAGE:
                return R.string.main_stat_mileage_top_left_title;
            case StatisticInfoModel.TYPE_SPEEDING:
                return R.string.main_stat_speeding_top_left_title;
            case StatisticInfoModel.TYPE_PHONE_USAGE:
                return R.string.main_stat_phone_top_left_title;
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return R.string.main_stat_drivingtime_top_left_title;
            default:
                return R.string.empty_string;
        }
    }

    public int formatTopRightTitle(int type) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return R.string.main_stat_maneuver_top_right_title;
            case StatisticInfoModel.TYPE_MILEAGE:
                return R.string.main_stat_mileage_top_right_title;
            case StatisticInfoModel.TYPE_SPEEDING:
                return R.string.main_stat_speeding_top_right_title;
            case StatisticInfoModel.TYPE_PHONE_USAGE:
                return R.string.main_stat_phone_top_right_title;
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return R.string.main_stat_drivingtime_top_right_title;
            default:
                return R.string.empty_string;
        }
    }

    public int formatBottomLeftTitle(int type) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return R.string.main_stat_maneuver_bottom_left_title;
            case StatisticInfoModel.TYPE_MILEAGE:
                return R.string.main_stat_mileage_bottom_left_title;
            case StatisticInfoModel.TYPE_SPEEDING:
                return R.string.main_stat_speeding_bottom_left_title;
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return R.string.main_stat_drivingtime_bottom_left_title;
            case StatisticInfoModel.TYPE_PHONE_USAGE:
            default:
                return R.string.empty_string;
        }
    }

    public int formatBottomRightTitle(int type) {
        switch (type) {
            case StatisticInfoModel.TYPE_MANEUVERS:
                return R.string.main_stat_maneuver_bottom_right_title;
            case StatisticInfoModel.TYPE_MILEAGE:
                return R.string.main_stat_mileage_bottom_right_title;
            case StatisticInfoModel.TYPE_SPEEDING:
                return R.string.main_stat_speeding_bottom_right_title;
            case StatisticInfoModel.TYPE_DRIVING_TIME:
                return R.string.main_stat_drivingtime_bottom_right_title;
            case StatisticInfoModel.TYPE_PHONE_USAGE:
            default:
                return R.string.empty_string;
        }
    }

    public String getCurrentTime() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        String format = time.format(calendar.getTime());
        int end = format.length() - 2;
        CharSequence charSequence = format.substring(0, end);
        CharSequence charSequence2 = format.substring(end);

        return charSequence + ":" + charSequence2;
    }

    public String formatDate(Date date) {
        String str = dayMonth.format(date);
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
