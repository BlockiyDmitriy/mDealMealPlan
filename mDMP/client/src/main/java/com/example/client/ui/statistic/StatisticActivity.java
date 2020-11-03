package com.example.client.ui.statistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.client.R;

import java.util.ArrayList;
import java.util.List;


public class StatisticActivity extends AppCompatActivity {
//    https://www.anychart.com/ru/technical-integrations/samples/android-charts/
//    https://www.youtube.com/watch?v=qWBA2ikLJjU

    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        setupPieChart();
    }

    public void setupPieChart(){

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("John", 10000));
        data.add(new ValueDataEntry("Jake", 12000));
        data.add(new ValueDataEntry("Peter", 18000));

        pie.data(data);
        pie.title("chart");
        anyChartView.setChart(pie);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statistic, menu); //запуск меню
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statistic_back: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}