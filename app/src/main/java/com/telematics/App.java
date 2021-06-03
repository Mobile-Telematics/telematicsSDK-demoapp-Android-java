package com.telematics;

import android.app.Application;
import android.util.Log;

import com.raxeltelematics.v2.sdk.Settings;
import com.raxeltelematics.v2.sdk.TrackingApi;

import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RxJavaPlugins.setErrorHandler(throwable -> {
            throwable.printStackTrace();
        });

        Settings settings = new Settings(
                Settings.getStopTrackingTimeHigh(),
                Settings.getAccuracyHigh(),
                true,
                true,
                false
        );
        TrackingApi api = TrackingApi.getInstance();
        api.initialize(this, settings);
    }
}
