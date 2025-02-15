package com.example.airecycle;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private BarChart barchartBackground;
    private BarChart barChart;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        ArrayList<String> list = new ArrayList<>();

        list.add("0");

        barchartBackground = (BarChart)findViewById(R.id.chartBackground);
        barChart = (BarChart)findViewById(R.id.chart);

        List<BarEntry> entriesBackground = new ArrayList<>();

        entriesBackground.add(new BarEntry(1, 100));
        entriesBackground.add(new BarEntry(2, 100));
        entriesBackground.add(new BarEntry(3, 100));
        entriesBackground.add(new BarEntry(4, 100));

        List<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(1, 25));
        entries.add(new BarEntry(2, 50));
        entries.add(new BarEntry(3, 25));
        entries.add(new BarEntry(4, 75));

        BarDataSet barDataSetBackground = new BarDataSet(entriesBackground, "");
        barDataSetBackground.setColor(Color.parseColor("#F3F3F3"));
        barDataSetBackground.setDrawValues(false);

        BarDataSet barDataSet = new BarDataSet(entries, "");
        barDataSet.setColor(Color.parseColor("#6A655B"));

        barDataSet.setDrawValues(false);

        BarData barDataBackground = new BarData(barDataSetBackground);
        barDataBackground.setBarWidth(0.5f); // set custom bar width
        barchartBackground.setData(barDataBackground);
        barchartBackground.setFitBars(true); // make the x-axis fit exactly all bars

        barchartBackground.getAxisLeft().setDrawGridLines(false);
        barchartBackground.getAxisRight().setDrawGridLines(false);
        barchartBackground.getXAxis().setDrawGridLines(false);
        barchartBackground.getAxisLeft().setEnabled(false);
        barchartBackground.getAxisRight().setEnabled(false);
        barchartBackground.getXAxis().setEnabled(false);
        barchartBackground.getAxisLeft().setAxisMaximum(100f);
        barchartBackground.getAxisLeft().setAxisMinimum(0f);
        barchartBackground.setTouchEnabled(false);
        barchartBackground.setDragEnabled(false);


        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f); // set custom bar width
        barChart.setData(barData);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setEnabled(false);
        barChart.getAxisLeft().setAxisMaximum(100f);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);

        Description description = new Description();
        description.setText("");

        barchartBackground.invalidate();
        barchartBackground.setDescription(description);

        barChart.setDescription(description);
        barChart.invalidate();

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)) ;


        // 리사이클러뷰에 Adapter 객체 지정.
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list) ;
        recyclerView.setAdapter(adapter) ;


        try {
            UpdateThread updateThread = new UpdateThread(barChart, entries);
            new Thread(updateThread).start();
            list.clear();
            list.add(updateThread.getCount() + "");

        } catch (IOException exc) {
            exc.printStackTrace();
        }

        int[] colors = new int[4];

        for (int i = 0; i < 4; i += 1) {
            if (entries.get(i).getY() >= 75)
                colors[i] = Color.parseColor("#CA957B");
            else
                colors[i] = Color.parseColor("#6A655B");
        }

        barDataSet.setColors(colors);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ERICA1 = new LatLng(37.297475, 126.838134);

        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(ERICA1);
        markerOptions1.title("ERICA 캠퍼스1");
        markerOptions1.snippet("ERICA 캠퍼스1");
        mMap.addMarker(markerOptions1);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ERICA1, 15));
    }


}
