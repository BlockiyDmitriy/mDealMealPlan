package com.example.stepometr.stepometr.pedometr

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationStateButtonHandler : BroadcastReceiver() {
    private var mPedometerService: Intent? = null
    override fun onReceive(context: Context, intent: Intent) {
        // Change pedometer state (on to off, or off to on)
        if (!PedometerService.isListenAccelerometer) {
            sendIntentToService(context, PedometerService.ACCELEROMETER_ON)
        } else {
            sendIntentToService(context, PedometerService.ACCELEROMETER_OFF)
        }
    }

    // Create new intent and send it to service for transmitting in service new value of
    // accelerometer listener (on or off)
    private fun sendIntentToService(context: Context, lister_state: Int) {
        mPedometerService = Intent(context, PedometerService::class.java)
        mPedometerService!!.putExtra(PedometerService.ACCELEROMETER_KEY, lister_state)
        context.startService(mPedometerService)
    }
}