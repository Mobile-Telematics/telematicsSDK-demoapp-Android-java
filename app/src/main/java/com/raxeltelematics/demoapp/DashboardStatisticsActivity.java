package com.raxeltelematics.demoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.raxeltelematics.v2.sdk.TrackingApi;
import com.raxeltelematics.v2.sdk.server.model.sdk.DashboardInfo;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DashboardStatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_dashboard);
        loadDashboard();
        ((Button) findViewById(R.id.maneuversStatView)).setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                startActivity((new Intent((Context) DashboardStatisticsActivity.this, StatisticActivity.class))
                        .putExtra(
                                StatisticActivity.EXTRA_STATISTIC_TYPE,
                                StatisticActivity.STATISTIC_TYPE_MANEUVERS
                        ));
            }
        }));
        ((Button) findViewById(R.id.speedingStatView)).setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                startActivity((new Intent((Context) DashboardStatisticsActivity.this, StatisticActivity.class))
                        .putExtra(
                                StatisticActivity.EXTRA_STATISTIC_TYPE,
                                StatisticActivity.STATISTIC_TYPE_SPEEDING
                        ));
            }
        }));
        ((Button) findViewById(R.id.mileageStatView)).setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                startActivity((new Intent((Context) DashboardStatisticsActivity.this, StatisticActivity.class))
                        .putExtra(
                                StatisticActivity.EXTRA_STATISTIC_TYPE,
                                StatisticActivity.STATISTIC_TYPE_MILEAGE
                        ));
            }
        }));
        ((Button) findViewById(R.id.phoneStatView)).setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                startActivity((new Intent((Context) DashboardStatisticsActivity.this, StatisticActivity.class))
                        .putExtra(
                                StatisticActivity.EXTRA_STATISTIC_TYPE,
                                StatisticActivity.STATISTIC_TYPE_PHONE_USAGE
                        ));
            }
        }));
    }

    private void loadDashboard() {

        Disposable d = Single.fromCallable(() ->
                TrackingApi.getInstance().getDashboardInfo(null)
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> updateDashboard(value)
                );
    }

    private void updateDashboard(DashboardInfo result) {
        if (result != null) {
            ((TextView) findViewById(R.id.ratingTitleTextView)).setText(result.getRating() + " points out of 100");
            ((TextView) findViewById(R.id.maneuversView)).setText(result.getDrivingLevel() + " points out of 100");
            ((TextView) findViewById(R.id.speedingView)).setText(result.getSpeedLevel() + "points out of 100");
            ((TextView) findViewById(R.id.mileageView)).setText(result.getMileageLevel() + " points out of 100");
            ((TextView) findViewById(R.id.phoneView)).setText(result.getPhoneLevel() + " points out of 100");
        }
    }
}
