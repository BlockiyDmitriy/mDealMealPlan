package com.example.client.ui.statistic;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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


    String[] nutritionalStr = {"Белки", "Жиры", "Углеводы"};
    int[] nutritionalValue = {100, 200, 300};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        setupPieChart();
    }

    public void setupPieChart() {

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        for (int i = 0; i < nutritionalStr.length; i++) {
            data.add(new ValueDataEntry(nutritionalStr[i], nutritionalValue[i]));
        }

        pie.data(data);
        pie.title("Статистика дня");
        anyChartView.setChart(pie);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}