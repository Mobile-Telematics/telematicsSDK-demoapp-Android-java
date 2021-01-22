package com.raxeltelematics.demoapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.here.android.mpa.common.GeoBoundingBox;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPolyline;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.mapping.MapPolyline;
import com.here.android.mpa.mapping.SupportMapFragment;
import com.raxeltelematics.v2.sdk.TrackingApi;
import com.raxeltelematics.v2.sdk.server.model.Locale;
import com.raxeltelematics.v2.sdk.server.model.sdk.TrackDetails;
import com.raxeltelematics.v2.sdk.server.model.sdk.TrackOriginDictionary;
import com.raxeltelematics.v2.sdk.server.model.sdk.TrackPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TrackDetailsActivity extends AppCompatActivity {

    final static String EXTRA_TRACK_ID = "extra.track.id";

    private CompositeDisposable disposables = new CompositeDisposable();

    List<String> arrayOriginTypesStr = List.of(
            "OriginalDriver",
            "Passanger",
            "Bus",
            "Taxi",
            "Train",
            "Bicycle",
            "Motorcycle",
            "Walking",
            "Running",
            "Other"
    );

    List<Integer> arrayOriginDescr = List.of(
            R.string.origin_driver,
            R.string.origin_passenger,
            R.string.origin_bus,
            R.string.origin_taxi,
            R.string.origin_train,
            R.string.origin_bicycle,
            R.string.origin_motocycle,
            R.string.origin_walking,
            R.string.origin_run,
            R.string.origin_other
    );

    private Map map = null;

    private String trackId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details_v2);

        checkPermissions();
    }

    private void onAllPermissionsSuccess() {

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.trip_view_map);

        supportMapFragment.init(error -> {
            if (error == OnEngineInitListener.Error.NONE) {
                map = supportMapFragment.getMap();
                trackId = getIntent().getExtras().getString(EXTRA_TRACK_ID);
                loadTrackDetails(trackId);
            }
        });
    }


    private void loadTrackDetails(String trackId) {

        Disposable d = Single.fromCallable(() ->
                TrackingApi.getInstance().getTrackDetails(trackId, Locale.EN)
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value ->
                        {
                            updateDetailsView(value);
                            TrackPoint[] points = value.getPoints();
                            if (points != null) {
                                List<TripPointModel> tripPointModels = new ArrayList<>();
                                for (TrackPoint point : points) {
                                    TripPointModel tripPointModel = convert(point);
                                    tripPointModels.add(tripPointModel);
                                }
                                initMap(tripPointModels);
                            }
                        }
                );

        disposables.add(d);
    }

    private void updateDetailsView(TrackDetails details) {

        if (details != null) {

            Button originButton = (Button) findViewById(R.id.originButton);

            originButton.setText(getString(
                    arrayOriginDescr.get(arrayOriginTypesStr.indexOf(details.getOriginalCode()))
            ));
            originButton.setEnabled(!details.getHasOriginChanged());
            if (!details.getHasOriginChanged()) {
                originButton.setOnClickListener(v -> {
                    originButton.setEnabled(false);
                    loadDict(originButton);
                });
            }
            ((TextView) findViewById(R.id.totalRating)).setText(Math.round(details.getRating()) + "/5");
            ((TextView) findViewById(R.id.distance)).setText(String.format("%.1f km", details.getDistance()));
            ((TextView) findViewById(R.id.timeInTrip)).setText(String.format("%.1f mins", details.getDuration()));

            ((TextView) findViewById(R.id.startAddress)).setText(details.getAddressStart());
            ((TextView) findViewById(R.id.stopAddress)).setText(details.getAddressEnd());
            ((TextView) findViewById(R.id.startTime)).setText(details.getStartDate());
            ((TextView) findViewById(R.id.stopTime)).setText(details.getEndDate());
            ((TextView) findViewById(R.id.accelCount)).setText("times: " + details.getAccelerationCount());
            ((TextView) findViewById(R.id.speedsCount)).setText(String.format("%.1f km", (details.getHighOverSpeedMileage() + details.getMidOverSpeedMileage())));
            ((TextView) findViewById(R.id.breaksCount)).setText("times: " + details.getDecelerationCount());
            ((TextView) findViewById(R.id.phoneCount)).setText(Math.round(details.getPhoneUsage()) + " mins");
        }
    }

    private TripPointModel convert(TrackPoint tripPoint) {

        return new TripPointModel(
                tripPoint.getLatitude(),
                tripPoint.getLongitude(),
                getSpeedColor(tripPoint.getSpeedType()),
                getAlertImageId(tripPoint.getAlertType()),
                tripPoint.getPhoneUsage()
        );
    }

    private int getSpeedColor(String type) {
        int color;

        switch (type) {
            case "norm":
                color = R.color.colorSpeedTypeNormal;
            case "mid":
                color = R.color.colorSpeedTypeMid;
            case "high":
                color = R.color.colorSpeedTypeHigh;
            default:
                color = R.color.colorSpeedTypeNormal;
        }

        return color;
    }

    private int getAlertImageId(String type) {
        int res;
        switch (type) {
            case "acc":
                res = R.drawable.ic_dot_rapid_acc;
            case "deacc":
                res = R.drawable.ic_dot_harsh_braking;
            default:
                res = 0;
        }
        return res;
    }

    private void loadDict(Button originButton) {

        Disposable d = Single.fromCallable(() ->
                TrackingApi.getInstance().getTrackOriginDict(Locale.EN)
        )
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                    originButton.setEnabled(true);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value ->
                        {
                            originButton.setEnabled(true);
                            List<String> items = new ArrayList<>();
                            for (TrackOriginDictionary item : value) {
                                items.add(item.getName());
                            }
                            String[] sItems = new String[items.size()];
                            items.toArray(sItems);
                            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                            dialog.setTitle("Change origin to");
                            dialog.setSingleChoiceItems(sItems, 0, (dialog1, which) -> {
                                dialog1.dismiss();
                                changeOriginTo(value[(which)]);
                            });
                            dialog.show();
                        }
                );
    }

    private void changeOriginTo(TrackOriginDictionary trackOriginDictionary) {

        Button originButton = (Button) findViewById(R.id.originButton);

        Disposable disposable = Single.fromCallable(() ->
                TrackingApi.getInstance().changeTrackOrigin(trackId, trackOriginDictionary.getCode())
        )
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                    originButton.setEnabled(true);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(value ->
                        loadTrackDetails(trackId)
                );
    }

    private void initMap(List<TripPointModel> tripPoints) {

        List<GeoCoordinate> listCoordinates = new ArrayList<>();
        List<MapPolyline> listLines = new ArrayList<>();
        List<MapMarker> listMarkers = new ArrayList<>();
        Image imageStart = getImage(R.drawable.marker_a_);
        Image imageStop = getImage(R.drawable.marker_b_);

        for (int i = 0; i < tripPoints.size(); i++) {

            TripPointModel point = tripPoints.get(i);

            double latitude = point.latitude;
            double longitude = point.longitude;
            GeoCoordinate geo = new GeoCoordinate(latitude, longitude, 0.0);
            if (i > 0) {
                listCoordinates.add(geo);
                addLine(listCoordinates, listLines, point);
                listCoordinates = new ArrayList<>();
                listCoordinates.add(geo);

            } else if (i == 0) {
                addMarker(listMarkers, imageStart, geo);
                listCoordinates.add(geo);
            }

            if (i == tripPoints.size() - 1) {
                addMarker(listMarkers, imageStop, geo);
            }

            if (point.alertTypeImage != 0) {
                addMarker(listMarkers, getImage(point.alertTypeImage), geo);
            }
        }

        if (listCoordinates.size() > 1) {
            addLine(listCoordinates, listLines, tripPoints.get(tripPoints.size() - 1));
        }

        List<MapObject> listMapObjects = new ArrayList<>();
        listMapObjects.addAll(listLines);
        if (listMarkers.size() > 0) {
            listMapObjects.addAll(listMarkers);
        }

        updateMarkers(tripPoints, listMapObjects);
    }

    private void addLine(List<GeoCoordinate> listCoordinates,
                         List<MapPolyline> listLines,
                         TripPointModel point
    ) {
        if (point.usePhone) {
            MapPolyline line2 = new MapPolyline(new GeoPolyline(listCoordinates));
            line2.setLineColor(ContextCompat.getColor(this, R.color.trips_phone_line_color));
            line2.setLineWidth(25);
            listLines.add(line2);
        }
        MapPolyline line = new MapPolyline(new GeoPolyline(listCoordinates));
        int speedColor = point.speedColor;
        line.setLineColor(ContextCompat.getColor(this, speedColor));
        line.setLineWidth(15);
        listLines.add(line);
    }

    private void addMarker(List<MapMarker> listMarkers, Image imageStop, GeoCoordinate geo) {
        MapMarker marker = new MapMarker();
        marker.setCoordinate(geo);
        marker.setIcon(imageStop);
        listMarkers.add(marker);
    }

    private void updateMarkers(List<TripPointModel> tripPoints, List<MapObject> listMapObjects) {
        if (map != null) {
            map.removeMapObjects(listMapObjects);
            map.addMapObjects(listMapObjects);
            if (!tripPoints.isEmpty()) {
                TripPointModel tripPointModel = tripPoints.get(0);
                double latitude = tripPointModel.latitude;
                latitude -= 0.02;
                centerMapByRoute(tripPoints);
                map.setUseSystemLanguage();
            }
        }
    }

    private void centerMapByRoute(List<TripPointModel> tripPoints) {
        List<GeoCoordinate> geoCoords = new ArrayList<>();
        for (TripPointModel tripPointModel : tripPoints) {
            geoCoords.add(new GeoCoordinate(tripPointModel.latitude, tripPointModel.longitude));
        }
        GeoPolyline geoPolyline = new GeoPolyline(geoCoords);
        GeoBoundingBox boundingBox = geoPolyline.getBoundingBox();
        boundingBox.expand(850F, 850F);
        map.zoomTo(boundingBox, Map.Animation.NONE, Map.MOVE_PRESERVE_ORIENTATION);
    }

    private Image getImage(Integer resId) {
        Image image = new Image();
        try {
            image.setImageResource(resId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        } else {
            onAllPermissionsSuccess();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            for (int index = 0; index < permissions.length; ++index) {
                if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            onAllPermissionsSuccess();
        }
    }

    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}
