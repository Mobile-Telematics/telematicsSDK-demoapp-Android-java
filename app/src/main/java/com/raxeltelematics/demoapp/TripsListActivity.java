package com.raxeltelematics.demoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raxeltelematics.v2.sdk.TrackingApi;
import com.raxeltelematics.v2.sdk.server.model.Locale;
import com.raxeltelematics.v2.sdk.server.model.sdk.Track;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TripsListActivity extends AppCompatActivity {

    private RecyclerView.Adapter viewAdapter;
    private RecyclerView.LayoutManager viewManager;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips_list);

        viewManager = new LinearLayoutManager(this);

        RecyclerView recycleView = findViewById(R.id.recycleView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycleView.setHasFixedSize(true);

        // use a linear layout manager
        recycleView.setLayoutManager(viewManager);

        loadData();
    }

    private void loadData() {

        Disposable d = Single.fromCallable(() ->
                TrackingApi.getInstance().getTracks(Locale.EN, null, null, 0, 10)
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> {
                            updateData(value);
                        }
                );
        disposables.add(d);
    }


    void updateData(Track[] result) {
        if (result != null) {

            List<TrackModel> viewModel = new ArrayList<>();

            for (Track track : result) {
                TrackModel trackModel = new TrackModel(
                        track.getAddressStart(),
                        track.getAddressEnd(),
                        track.getEndDate(),
                        track.getStartDate(),
                        track.getTrackId(),
                        track.getAccelerationCount(),
                        track.getDecelerationCount(),
                        track.getDistance(),
                        track.getDuration(),
                        track.getRating(),
                        track.getPhoneUsage(),
                        track.getOriginalCode(),
                        track.getHasOriginChanged(),
                        track.getMidOverSpeedMileage(),
                        track.getHighOverSpeedMileage(),
                        track.getDrivingTips(),
                        track.getShareType(),
                        track.getCityStart(),
                        track.getCityFinish()
                );
                viewModel.add(trackModel);
            }

            TrackAdapter viewAdapter = new TrackAdapter(viewModel);
            viewAdapter.setOnItemClickListener((view, position, trackId) -> {
                showTrackDetails(trackId);
            });
            RecyclerView recycleView = findViewById(R.id.recycleView);
            recycleView.setAdapter(viewAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }

    private void showTrackDetails(String trackId) {
        startActivity(new Intent(this, TrackDetailsActivity.class).putExtra(TrackDetailsActivity.EXTRA_TRACK_ID, trackId));
    }
}
