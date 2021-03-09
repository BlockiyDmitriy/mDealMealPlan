package com.example.stepometr.stepometr.pedometr

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import android.os.SystemClock
import android.preference.PreferenceManager
import android.util.Log
import android.widget.RemoteViews
import com.example.stepometr.MainActivity
import com.example.stepometr.R
import com.example.stepometr.stepometr.StepometrFragment
import com.example.stepometr.stepometr.pedometr.PedometerSharedPreferences.ReadStepsAndTime

import java.util.Arrays;
import java.util.Date;


class PedometerService : Service(), PedometerAccelerometer.PedometerAccelerometerListener {
    private val POWER_TAG = "MY_POWER_TAG"
    private lateinit var mPedometerAccelerometer: PedometerAccelerometer
    private var mLastSpeakTime: Long = 0
    private var mAlarmManager: AlarmManager? = null
    private var mAlarmIntent: PendingIntent? = null
    private var mWakeLock: PowerManager.WakeLock? = null
    override fun onCreate() {
        super.onCreate()
        sTime = 0
        steps = 0
        sMeters = 0
        isTimerEnable = false
        timerSteps = 0
        timerTimeStart = 0
        timerTimeOffset = 0
        mLastSpeakTime = System.currentTimeMillis()
        isListenAccelerometer = false
        mPedometerAccelerometer = PedometerAccelerometer(this@PedometerService)
        mPedometerAccelerometer.PedometerAccelerometerListenerSet(this)

        // Schedule every hour
        mAlarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, EveryHourHandler::class.java)
        mAlarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        mAlarmManager!!.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + MillisecondsBeforeNextHour(),
            AlarmManager.INTERVAL_HOUR, mAlarmIntent
        )

        // not turn off CPU if accelerometer listener is not null
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, POWER_TAG)

        // get step width
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )
        stepWidth = sharedPref.getInt(
            applicationContext.resources.getString(R.string.StepWidthPreference),
            20
        ).toFloat()
        stepWidth /= 100
        val sensit = sharedPref.getFloat(
            applicationContext.resources.getString(R.string.SensitivityPreference),
            5f
        )
        PedometerAccelerometer.setSensitivity(sensit)

        // update today's time and steps from SharedPreferences
        val date = Date()
        val stepsTime = ReadStepsAndTime(
            this, java.lang.String.valueOf(date.getDate())
                    + java.lang.String.valueOf(date.getMonth())
                    + java.lang.String.valueOf(date.getYear() + 1900)
        )
        Log.d("PedometerService", "steps_time: " + Arrays.toString(stepsTime))
        sTime = (stepsTime[24] * 1000).toLong() // seconds to miliseconds
        for (i in 0..23) {
            if (stepsTime[i] > steps) {
                steps = stepsTime[i]
            }
        }
        Log.d("PedometerService", "sTime: $sTime")
        Log.d("PedometerService", "sSteps: $steps")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val `val` = intent.getIntExtra(ACCELEROMETER_KEY, 2)
        if (`val` == ACCELEROMETER_OFF) {
            mWakeLock!!.release()
            isListenAccelerometer = false
            mPedometerAccelerometer.StopListen()
        }
        if (`val` == ACCELEROMETER_ON) {
            mWakeLock!!.acquire(10 * 60 * 1000L /*10 minutes*/)
            isListenAccelerometer = true
            mPedometerAccelerometer.StartListen()
        }
        if (mBuilder == null) {
            mBuilder = Notification.Builder(this)
        }
        getMyActivityNotification(this)
        startForeground(PEDOMETER_NOTIFICATION_ID, mNotification)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PedometerService", "onDestroy called")
        // Remove all our notifications
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.cancel(PEDOMETER_NOTIFICATION_ID)
        // Stop listen data from accelerometer
        mPedometerAccelerometer.StopListen()
        // If the alarm has been set, cancel it.
        if (mAlarmManager != null) {
            mAlarmManager!!.cancel(mAlarmIntent)
        }
        if (steps != 0) {
            Log.d("PedometerService", "saved sSteps")
            PedometerSharedPreferences.writeCurrentStepsAndTime(this) // store current steps and time
        }
        if (mWakeLock != null && mWakeLock!!.isHeld) {
            mWakeLock!!.release()
        }
    }

    // Listener, calls when step has been done
    override fun StepHasBeenDone() {
        Log.d("StepHasBeenDone", "StepHasBeenDone()")
        // increase steps
        steps++
        val meters = Math.round(steps * stepWidth)
        // check time
        val now = System.currentTimeMillis()
        val delta = now - mLastSpeakTime
        mLastSpeakTime = now
        if (delta / 1000 <= 5) { // less than 5 seconds
            sTime += delta
        }

        // timer increase steps
        if (isTimerEnable == true) {
            timerSteps++
        }
        if (sMeters != meters) {
            sMeters = meters
            UpdateNotification(this)
        }
    }

    // return milliseconds before next hour
    private fun MillisecondsBeforeNextHour(): Int {
        val date = Date()
        return (60 - date.getMinutes()) * 60 * 1000
    }

    companion object {
        const val ACCELEROMETER_KEY = "accelerometer_key"
        const val ACCELEROMETER_OFF = 0
        const val ACCELEROMETER_ON = 1
        private const val PEDOMETER_NOTIFICATION_ID = 1385

        // return current pedometer state, True - pedometer is on, False - pedometer is off
        var isListenAccelerometer = false
            private set

        // return steps with
        // preference settings
        var stepWidth = 0f
            private set

        // return the number of steps today
        // variables for pedometer
        var steps = 0
            private set
        private var sTime: Long = 0
        private var sMeters = 0

        // -----------------------------------------------
        // ------------- START TIMER SECTION -------------
        // -----------------------------------------------
        // variables for timer
        var isTimerEnable = false
        var timerSteps = 0
        var timerTimeStart = 0
        var timerTimeOffset = 0
        private var mNotification: Notification? = null
        private var mBuilder: Notification.Builder? = null

        // set steps with
        fun setStepWidth(stepWidth: Float, context: Context) {
            Companion.stepWidth = stepWidth
            Log.d("123", "sStepWidth: " + Companion.stepWidth.toString())
            UpdateNotification(context)
        }

        // return the today's total time
        val totalTime: Int
            get() {
                val result = sTime / 1000.toLong()
                return result.toInt()
            }

        // reset steps (if it is new day)
        fun resetSteps(context: Context) {
            steps = 0
            UpdateNotification(context)
        }

        // reset time (if it is new day)
        fun resetTime() {
            sTime = 0
        }

        // -----------------------------------------------
        // -------------- END TIMER SECTION --------------
        // -----------------------------------------------
        // prepare notification
        private fun getMyActivityNotification(context: Context) {
            // Convert steps to meters
            val meters = Math.round(steps * stepWidth)
            // prepare data
            val text: String = StepometrFragment.IntToString(meters)
            // create intent for updating service view
            val notificationIntent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context, 0,
                notificationIntent, 0
            ) // PendingIntent.FLAG_UPDATE_CURRENT

            // Set current distance in meters
            val remoteViews = RemoteViews(context.getPackageName(), R.layout.pedometer_service)
            remoteViews.setTextViewText(R.id.distance, text + "m")
            // Set image view (on or off pedometer)
            if (isListenAccelerometer == false) {
                remoteViews.setImageViewResource(R.id.state, R.mipmap.red_foot)
            } else {
                remoteViews.setImageViewResource(R.id.state, R.mipmap.green_foot)
            }

            // set receiver for ImageView
            val buttonStateIntent = Intent(context, NotificationStateButtonHandler::class.java)
            buttonStateIntent.putExtra("action", "close")
            val buttonClosePendingIntent =
                PendingIntent.getBroadcast(context, 0, buttonStateIntent, 0)
            remoteViews.setOnClickPendingIntent(R.id.state, buttonClosePendingIntent)

            mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
            if (mNotification == null) {
                mNotification = mBuilder?.build()
            }
            mNotification?.contentView = remoteViews


        }

        // update notification
        private fun UpdateNotification(context: Context) {
            getMyActivityNotification(context)
            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.notify(PEDOMETER_NOTIFICATION_ID, mNotification)
        }
    }
}