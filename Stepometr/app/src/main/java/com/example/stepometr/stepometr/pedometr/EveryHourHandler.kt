package com.example.stepometr.stepometr.pedometr

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class EveryHourHandler : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        PedometerSharedPreferences.writeCurrentStepsAndTime(context)
    }

    companion object {
        private const val NOTIFY_ID = 0
    }
}