package com.telematics.demoapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.raxeltelematics.v2.sdk.TrackingApi;
import com.raxeltelematics.v2.sdk.server.model.StatisticPeriod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StatisticActivity extends AppCompatActivity {

    static final String EXTRA_STATISTIC_TYPE = "extra.statistic.type";
    static final String STATISTIC_TYPE_MANEUVERS = "statistic.type.maneuvers";
    static final String STATISTIC_TYPE_DRIVING_TIME = "statistic.type.driving.time";
    static final String STATISTIC_TYPE_SPEEDING = "statistic.type.speeding";
    static final String STATISTIC_TYPE_MILEAGE = "statistic.type.mileage";
    static final String STATISTIC_TYPE_PHONE_USAGE = "statistic.type.phone.usage";

    private CompositeDisposable disposables = new CompositeDisposable();

    StatisticInfoTextFormatterImpl formatter;
    Integer typeInt;
    String type;
    StatisticInfoModel modelObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        this.formatter = new StatisticInfoTextFormatterImpl((Context) this);

        type = getIntent().getExtras().getString(EXTRA_STATISTIC_TYPE);

        switch (type) {
            case STATISTIC_TYPE_MANEUVERS:
                typeInt = StatisticInfoModel.TYPE_MANEUVERS;
            case STATISTIC_TYPE_DRIVING_TIME:
                typeInt = StatisticInfoModel.TYPE_DRIVING_TIME;
            case STATISTIC_TYPE_PHONE_USAGE:
                typeInt = StatisticInfoModel.TYPE_PHONE_USAGE;
            case STATISTIC_TYPE_SPEEDING:
                typeInt = StatisticInfoModel.TYPE_SPEEDING;
            case STATISTIC_TYPE_MILEAGE:
                typeInt = StatisticInfoModel.TYPE_MILEAGE;
        }

        updateDStatisticTitles();
        initChart();
        loadStatistic();

        ((RadioGroup) this.findViewById(R.id.periodGroup)).setOnCheckedChangeListener((group, checkedId) ->
                loadStatistic()
        );
    }

    private void updateDStatisticTitles() {
        ((TextView) this.findViewById(R.id.title1View)).setText(getString(formatter.formatTopLeftTitle(typeInt)));
        ((TextView) this.findViewById(R.id.title2View)).setText(getString(formatter.formatTopRightTitle(typeInt)));
        ((TextView) this.findViewById(R.id.title3View)).setText(getString(formatter.formatBottomLeftTitle(typeInt)));
        ((TextView) this.findViewById(R.id.title4View)).setText(getString(formatter.formatBottomRightTitle(typeInt)));
    }

    private void loadStatistic() {

        StatisticPeriod period = StatisticPeriod.ALL_TIME;

        int id = ((RadioGroup) findViewById(R.id.periodGroup)).getCheckedRadioButtonId();

        if (id == R.id.day) {
            period = StatisticPeriod.DAY;
        }
        if (id == R.id.week) {
            period = StatisticPeriod.WEEK;
        }
        if (id == R.id.alltime) {
            period = StatisticPeriod.ALL_TIME;
        }

        final StatisticPeriod fPeriod = period;

        Disposable d = Single.fromCallable(() -> {
                    switch (type) {
                        case STATISTIC_TYPE_MANEUVERS: {
                            return StatisticDetailInfoMapper.convert(TrackingApi.getInstance().getDrivingDetailsStatistics(fPeriod, null));
                        }
                        case STATISTIC_TYPE_SPEEDING: {
                            return StatisticDetailInfoMapper.convert(TrackingApi.getInstance().getSpeedDetailStatistics(fPeriod, null));
                        }
                        case STATISTIC_TYPE_MILEAGE: {
                            return StatisticDetailInfoMapper.convert(TrackingApi.getInstance().getMileageDetailsStatistics(fPeriod, null));
                        }
                        case STATISTIC_TYPE_PHONE_USAGE: {
                            return StatisticDetailInfoMapper.convert(TrackingApi.getInstance().getPhoneDetailStatistics(fPeriod, null));
                        }
                        case STATISTIC_TYPE_DRIVING_TIME: {
                            return StatisticDetailInfoMapper.convert(TrackingApi.getInstance().getDrivingTimeDetailsStatistics(fPeriod, null));
                        }
                    }
                    return null;
                }
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            modelObj = model;
                            initGraph(model);
                        }
                );


        disposables.add(d);
    }

    private void initChart() {

        LineChart lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        Description desc = new Description();
        desc.setText("");

        lineChart.setDescription(desc);
        lineChart.setDrawGridBackground(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setDrawMarkers(true);
        lineChart.setMarker(new MyChartMarkerView((Context) StatisticActivity.this, R.layout.chart_hightlight));
        lineChart.setNoDataText("No chart data");

        /* Horizontal */
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setValueFormatter((IAxisValueFormatter) (new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                StatisticInfoModel model = modelObj;
                if (model != null) {
                    Log.d("TAG", "model not null=$model");
                }
                if (model.getGrafhPoints1() != null && !model.getGrafhPoints1().isEmpty()) {

                    if (model.getGrafhPoints1().size() > value) {
                        Object date = model.getGrafhPoints1().get(Math.round(value)).getData();
                        Log.d("TAG", "date not null=$date");
                        if (date != null)
                            return new SimpleDateFormat("dd MMM", Locale.getDefault()).format(date);
                        else return "";
                    } else {
                        return "";
                    }
                }
                Log.d("TAG", "return null");
                return "";
            }

            public int getDecimalDigits() {
                return 0;
            }
        }));


        /* Vertical Left */
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setDrawGridLines(true);
        axisLeft.setDrawLabels(true);
        axisLeft.setDrawAxisLine(true);
        axisLeft.setAxisMinimum(0.0f);

        /* Vertical Right */
        YAxis axisRight = lineChart.getAxisRight();
        axisRight.setDrawGridLines(true);
        axisRight.setDrawLabels(true);
        axisRight.setDrawAxisLine(true);
        axisRight.setAxisMinimum(0.0f);

        lineChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) (new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                List<ILineDataSet> dataSets = lineChart.getLineData().getDataSets();
                float x = e.getX();
                ILineDataSet iLineDataSet = dataSets.get(0);
                Entry entryForXPos = iLineDataSet.getEntryForIndex(Math.round(x));
                float y = entryForXPos.getY();
                float y2;
                float y3;

                ArrayList<Highlight> values = new ArrayList<Highlight>();
                values.add(new Highlight(x, y, 0));

                if (2 <= dataSets.size()) {
                    Entry entryForXPos1 = dataSets.get(1).getEntryForIndex(Math.round(x));
                    y2 = entryForXPos1.getY();
                    values.add(new Highlight(x, y2, 1));
                }
                if (3 <= dataSets.size()) {
                    Entry entryForXPos2 = dataSets.get(2).getEntryForIndex(Math.round(x));
                    y3 = entryForXPos2.getY();
                    values.add(new Highlight(x, y3, 2));
                }

                lineChart.highlightValues(values.toArray(new Highlight[0]));
            }

            @Override
            public void onNothingSelected() {

            }
        }));

    }

    private static final class MyChartMarkerView extends MarkerView {

        public MPPointF getOffset() {
            return new MPPointF((float) (-this.getWidth() / 2), (float) (-this.getHeight() / 2));
        }

        public MyChartMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
        }
    }

    protected void initGraph(StatisticInfoModel infoModel) {

        LineChart lineChart = this.findViewById(R.id.lineChart);

        List<Entry> graph1 = infoModel.getGrafhPoints1();
        LineDataSet dataSet = null;
        if (graph1 != null && graph1.size() != 0) {
            dataSet = getLineDataSet(graph1, ContextCompat.getColor(lineChart.getContext(), R.color.graphColor1));
        }

        List<Entry> graph2 = infoModel.getGrafhPoints2();
        LineDataSet dataSet2 = null;
        if (graph2 != null && graph2.size() != 0) {
            dataSet2 = getLineDataSet(graph2, ContextCompat.getColor(lineChart.getContext(), R.color.graphColor2));
        }

        List<Entry> graph3 = infoModel.getGrafhPoints3();
        LineDataSet dataSet3 = null;
        if (graph3 != null && graph3.size() != 0) {
            dataSet3 = getLineDataSet(graph3, ContextCompat.getColor(lineChart.getContext(), R.color.graphColor3));// change color!
        }

        if (dataSet != null) {

            LineData lineData = new LineData();
            lineData.addDataSet(dataSet);

            if (dataSet2 != null) {
                lineData.addDataSet(dataSet2);
            }
            if (dataSet3 != null) {
                lineData.addDataSet(dataSet3);
            }

            lineData.setDrawValues(false);
            lineChart.setData(lineData);

            initLabelAx();

            lineChart.invalidate();

        } else {
            ((TextView) findViewById(R.id.labelAx)).setVisibility(View.INVISIBLE);
            lineChart.clear();
        }
    }

    private LineDataSet getLineDataSet(List<Entry> graph1, int blue) {
        LineDataSet dataSet = new LineDataSet(graph1, ""); // add entries to dataset
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setColor(blue);
        //dataSet.setValueTextColor(Color.BLUE);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(blue);
        dataSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -999990f;
            }
        });
        dataSet.setDrawCircles(false);
        dataSet.setDrawCircleHole(false);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setFillAlpha(200);
        dataSet.setHighlightEnabled(true); // allow highlighting for DataSet

        // set this to false to disable the drawing of highlight indicator (lines)
        dataSet.setDrawHighlightIndicators(true);
        dataSet.setHighLightColor(Color.BLACK);
        dataSet.setDrawHorizontalHighlightIndicator(true);
        dataSet.setDrawVerticalHighlightIndicator(true);
        return dataSet;
    }

    private void initLabelAx() {

        TextView labelAx = (TextView) findViewById(R.id.labelAx);

        labelAx.setVisibility(View.VISIBLE);

        switch (type) {
            case STATISTIC_TYPE_MANEUVERS: {
                labelAx.setText(R.string.main_stat_times_label);
            }

            case STATISTIC_TYPE_MILEAGE: {
                labelAx.setText(R.string.main_stat_km_label);
            }

            case STATISTIC_TYPE_SPEEDING: {
                labelAx.setText(R.string.main_stat_km_label);
            }

            case STATISTIC_TYPE_PHONE_USAGE: {
                labelAx.setText(R.string.main_stat_min_label);
            }

            case STATISTIC_TYPE_DRIVING_TIME: {
                labelAx.setText(R.string.main_stat_h_label);
            }
            default: {
                labelAx.setText(R.string.main_stat_unknown_label);
            }
        }
    }

    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}
