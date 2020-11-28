package com.example.clientwithbottmmenu.ui.statistic;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.clientwithbottmmenu.R;

import java.util.ArrayList;
import java.util.List;

public class StatisticFragment extends Fragment {

    AnyChartView anyChartView;


    String[] nutritionalStr = {"Белки", "Жиры", "Углеводы"};
    int[] nutritionalValue = {100, 200, 300};

    public StatisticFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistic, container, false);
        anyChartView = (AnyChartView) v.findViewById(R.id.any_chart_view);
        setupPieChart();
        return v;
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
}