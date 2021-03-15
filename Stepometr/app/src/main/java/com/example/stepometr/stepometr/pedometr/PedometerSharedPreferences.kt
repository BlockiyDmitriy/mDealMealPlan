package com.example.stepometr.stepometr.pedometr


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.stepometr.R

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


object PedometerSharedPreferences {
    // write current steps and time values
    fun writeCurrentStepsAndTime(context: Context?) {
        var currentDate: String
        var jsonObject: JSONObject? = null
        var sharedPreferences: SharedPreferences? = null
        if (context != null) {
            sharedPreferences = context.getSharedPreferences(
                context.getResources().getString(R.string.SharedPreferences_MainKey),
                Context.MODE_PRIVATE
            )
        }
        else{
            throw Exception("Context = null")
        }
        val date = Date()
        // key format day - month - year
        currentDate = ""
        currentDate += java.lang.String.valueOf(date.getDate())
        currentDate += java.lang.String.valueOf(date.getMonth())
        currentDate += java.lang.String.valueOf(date.getYear() + 1900)

        // load data from service
        var steps: Int = PedometerService.steps
        var time: Int = PedometerService.totalTime
        if (sharedPreferences?.contains(currentDate) == false) {
            // no preference for this day yet, reset data in service (new calculation)
            if (date.getHours() === 0) { // midnight, new day
                PedometerService.resetTime()
                PedometerService.resetSteps(context)
                steps = 0
                time = steps
            }
        } else {
            // preference already present, read it and then write data to the end
            if (sharedPreferences != null) {

                val string = sharedPreferences.getString(currentDate, null)
                try {
                    jsonObject = JSONObject(string)
                } catch (e: JSONException) {
                    e.stackTrace
                }
            }
            else{
                throw Exception("SharedPreferences = null")
            }
        }
        if (jsonObject == null) {
            jsonObject = JSONObject()
        }
        try {
            jsonObject.put(java.lang.String.valueOf(date.getHours()), steps)
        } catch (e: JSONException) {
            e.stackTrace
        }
        Log.d("PedometerPreferences", "saved jsonObject: $jsonObject")
        Log.d("PedometerPreferences", "saved date: $currentDate")
        sharedPreferences.edit().putString(currentDate, jsonObject.toString()).commit()
        sharedPreferences.edit().putInt(currentDate + "t", time).commit()
    }

    // read (and write, if it need) steps and time values for day
    fun ReadStepsAndTime(context: Context, Date: String): IntArray {
        val stepsAndTime = IntArray(25)
        val mSharedPreferences: SharedPreferences
        var jsonObject = JSONObject()
        Log.d("PedometerPreferences", "read date: $Date")
        mSharedPreferences = context.getSharedPreferences(
            context.getResources().getString(R.string.SharedPreferences_MainKey),
            Context.MODE_PRIVATE
        )
        if (mSharedPreferences.contains(Date) == false) {
            Log.d("PedometerPreferences", "no preference for this day yet, all is zero")
            for (i in 0..24) {
                // no preference for this day yet, all is zero
                stepsAndTime[i] = 0
            }
        } else {
            val string = mSharedPreferences.getString(Date, null)
            Log.d("PedometerPreferences", "preference: $string")
            try {
                jsonObject = JSONObject(string)
            } catch (e: JSONException) {
                e.stackTrace
            }
            // load steps
            for (i in 0..23) {
                if (jsonObject.has(i.toString()) == true) {
                    try {
                        stepsAndTime[i] = jsonObject.getInt(i.toString())
                    } catch (e: JSONException) {
                        e.stackTrace
                    }
                } else {
                    stepsAndTime[i] = 0
                }
            }
            stepsAndTime[24] = mSharedPreferences.getInt(Date + "t", 0)
        }
        return stepsAndTime
    }

    // clear all preferences
    fun ClearAllPreferences(context: Context) {
        val mSharedPreferences: SharedPreferences = context.getSharedPreferences(
            context.getResources().getString(R.string.SharedPreferences_MainKey),
            Context.MODE_PRIVATE
        )
        mSharedPreferences.edit().clear().commit()
    }
}