package com.example.stepometr.stepometr

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.stepometr.R
import com.example.stepometr.stepometr.pedometr.PedometerService
import com.example.stepometr.stepometr.pedometr.PedometerSharedPreferences.ReadStepsAndTime

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


class StepometrFragment : Fragment() {

    private lateinit var viewModel: StepometrViewModel

    private lateinit var mGraph: LineChart

    private var mCurrentSteps = 0
    private val mTodaySteps = 0
    private val mStepWidth = 0f

    private lateinit var time: TextView
    private lateinit var steps: TextView
    private lateinit var speed: TextView
    private lateinit var meters: TextView
    private val stateButton: Button? = null
    private lateinit var pedometerService: Intent

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(StepometrViewModel::class.java)

        val root = inflater.inflate(R.layout.stepometr_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeFragment", "onResume()")
        Log.d("HomeFragment", "onCreateView()")
        timer = Timer() // Create timer

        timerTask = object : TimerTask() {
            override fun run() {
                val refresh = Handler(Looper.getMainLooper())
                refresh.post(Runnable { UpdateScreenData() })
            }

        }
        timer?.schedule(timerTask, 10, 1000)
    }

    override fun onPause() {
        super.onPause()
        Log.d("HomeFragment", "onPause()");
        // Stop timer
        timer?.cancel();
        timer?.purge();
    }

    // convert int to string with space between the thousands
    fun IntToString(value: Int): String {
        var meter_text = (value % 1000).toString()
        if (value / 1000 != 0) {
            while (meter_text.length < 3) {
                meter_text = "0$meter_text"
            }
        }
        if (value / 1000 != 0) {
            meter_text = (value / 1000).toString() + " " + meter_text
        }
        return meter_text
    }

    // convert time in second to string HH:MM:SS
    fun TimeToString(seconds: Int): String? {
        var time = ""
        if (seconds / 3600 < 10) {
            time += "0"
        }
        time += (seconds / 3600).toString() + ":"
        val Second = seconds % 60
        val minutes = seconds % 3600 / 60
        if (minutes < 10) {
            time += "0"
        }
        time += "$minutes:"
        if (Second < 10) {
            time += "0"
        }
        time += Second.toString()
        return time
    }

    // Create new intent and send it to service for transmitting in service new value of
    // accelerometer listener (on or off)
    private fun SendIntentToService(lister_state: Int) {
        pedometerService = Intent(activity, PedometerService::class.java)
        pedometerService.putExtra(PedometerService.ACCELEROMETER_KEY, lister_state)
        requireActivity().startService(pedometerService)
    }

    // Update (if it is need) data on screen
    private fun UpdateScreenData() {
        if (mCurrentSteps !== PedometerService.steps) {
            mCurrentSteps = PedometerService.steps
            val time: Int = PedometerService.totalTime
            val meters = Math.round(mCurrentSteps * mStepWidth).toInt()
            var speed = 0
            if (time != 0) {
                speed = (meters.toFloat() / time.toFloat() * 360f).toInt()
            }
            steps.setText(IntToString(mCurrentSteps) + " " + resources.getString(R.string.steps))
            this.meters.setText(IntToString(meters) + requireContext().resources.getString(R.string.History_Meters_Abbreviation))
            this.time.setText(TimeToString(time))
            this.speed.setText(
                (speed.toFloat() / 100f).toString() + " " + requireContext().resources.getString(
                    R.string.History_Speed_Abbreviation
                )
            )
            if (meters >= mTodaySteps) {
                this.meters.setTextColor(Color.GREEN)
            } else if (meters >= mTodaySteps - 1000) {
                this.meters.setTextColor(Color.YELLOW)
            } else {
                this.meters.setTextColor(Color.RED)
            }
        }
    }

    // create graph with today's steps by hours
    private fun GraphUpdate() {
        // read saved data
        val date = Date()
        val mY = ReadStepsAndTime(
            requireContext(), java.lang.String.valueOf(date.date)
                    + java.lang.String.valueOf(date.month) + java.lang.String.valueOf(date.year + 1900)
        )
        if (PedometerService.steps !== mY.get(date.getHours()) && PedometerService.steps !== 0) {
            mY[date.hours] = PedometerService.steps
        }
        for (i in 0 until mY.size - 2) {
            mY[i] = (mY[i] * mStepWidth) as Int
        }

        // set some settings for graph
        mGraph.getLegend().setEnabled(false) // disable legend
        val description = Description()
        description.setText("")
        mGraph.setDescription(description) // disable description text
        val xAxis: XAxis = mGraph.getXAxis()
        xAxis.setDrawGridLines(true)
        xAxis.setAxisMaximum(24f)
        xAxis.setAxisMinimum(0f)
        xAxis.setLabelCount(24)
        xAxis.setDrawGridLines(false)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        val leftAxis: YAxis = mGraph.getAxisLeft()
        var max = getMaxValue(mY)
        while (max % 500 != 0) {
            max++
        }
        if (max != 0) {
            leftAxis.setAxisMaximum(max.toFloat())
        }
        leftAxis.setAxisMinimum(0f)
        leftAxis.setLabelCount(5)
        leftAxis.setDrawGridLines(true)
        val rightAxis: YAxis = mGraph.getAxisRight()
        rightAxis.setDrawLabels(false)
        rightAxis.setDrawGridLines(false)
        mGraph.setDoubleTapToZoomEnabled(false)
        xAxis.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(v: Float, axisBase: AxisBase?): String? {
                if (v % 2 == 0f) {
                    @OptIn(kotlin.ExperimentalStdlibApi::class)
                    return v as Int.toString()
                }
                return ""
            }
        })

        // set data to graph
        var previous_value = mY[0]
        val mDataSets: MutableList<ILineDataSet> = ArrayList<ILineDataSet>()
        val mEntries = ArrayList<Entry>()
        for (i in 0..date.hours) {
            if (mY[i] == 0 || i == 0) {
                mEntries.add(
                    MutableMap.MutableEntry<Any?, Any?>(
                        i,
                        mY[i]
                    )
                )
            } else {
                mEntries.add(MutableMap.MutableEntry<Any?, Any?>(i, mY[i] - previous_value))
                previous_value = mY[i]
            }
        }
        Collections.sort(mEntries, EntryXComparator())
        val mDataSet = LineDataSet(mEntries, "Label")
        mDataSet.setDrawFilled(true)
        mDataSet.setDrawCircles(false)
        mDataSets.add(mDataSet)
        val lineData = LineData(mDataSets)
        lineData.setDrawValues(false)
        lineData.setHighlightEnabled(false)
        mGraph.setData(lineData)
        mGraph.invalidate() // refresh data
    }

    // getting the maximum value
    private fun getMaxValue(array: IntArray): Int {
        var maxValue = array[0]
        for (i in 1 until array.size) {
            if (array[i] > maxValue) {
                maxValue = array[i]
            }
        }
        return maxValue
    }
}