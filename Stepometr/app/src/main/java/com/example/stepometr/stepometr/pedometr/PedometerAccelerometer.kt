package com.example.stepometr.stepometr.pedometr

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class PedometerAccelerometer(context: Context) :
    SensorEventListener, Cloneable {
    private val mLastValues = FloatArray(3 * 2)
    private val mScale = FloatArray(2)
    private val mYOffset: Float
    private val mLastDirections = FloatArray(3 * 2)
    private val mLastExtremes = arrayOf(FloatArray(3 * 2), FloatArray(3 * 2))
    private val mLastDiff = FloatArray(3 * 2)
    private var mLastMatch = -1
    private val mSensorManager: SensorManager
    private val mSensor: Sensor
    private var mPedometerAccelerometerListener: PedometerAccelerometerListener? = null

    interface PedometerAccelerometerListener {
        fun StepHasBeenDone()
    }

    fun PedometerAccelerometerListenerSet(listener: PedometerAccelerometerListener?) {
        mPedometerAccelerometerListener = listener
    }

    @Throws(CloneNotSupportedException::class)
    override fun clone(): Any {
        return super.clone()
    }

    fun StartListen() {
        mSensorManager.registerListener(
            this@PedometerAccelerometer,
            mSensor,
            20000
        ) //SensorManager.SENSOR_DELAY_FASTEST); // SensorManager.SENSOR_DELAY_NORMAL
    }

    fun StopListen() {
        mSensorManager.unregisterListener(this@PedometerAccelerometer)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val sensor = event.sensor
        //Log.d(TAG, "new accelerometer value! " + sensor.getType());
        //Log.d(TAG, "accuracy: " + event.accuracy);
        //Log.d(TAG, "values: " + String.valueOf(event.values[0]) + " " + String.valueOf(event.values[1]) + " "+ String.valueOf(event.values[2]));
        synchronized(this) {
            if (sensor.type == Sensor.TYPE_ORIENTATION) {
            } else {
                val j =
                    if (sensor.type == Sensor.TYPE_ACCELEROMETER) 1 else 0
                if (j == 1) {
                    var vSum = 0f
                    for (i in 0..2) {
                        val v = mYOffset + event.values[i] * mScale[j]
                        vSum += v
                    }
                    val k = 0
                    val v = vSum / 3
                    val direction =
                        (if (v > mLastValues[k]) 1 else if (v < mLastValues[k]) -1 else 0).toFloat()
                    if (direction == -mLastDirections[k]) {
                        // Direction changed
                        val extType = if (direction > 0) 0 else 1 // minumum or maximum?
                        mLastExtremes[extType][k] = mLastValues[k]
                        val diff = Math.abs(
                            mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]
                        )
                        if (diff > mLimit) {
                            val isAlmostAsLargeAsPrevious =
                                diff > mLastDiff[k] * 2 / 3
                            val isPreviousLargeEnough =
                                mLastDiff[k] > diff / 3
                            val isNotContra = mLastMatch != 1 - extType
                            mLastMatch =
                                if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
                                    Log.i(TAG, "step")
                                    if (mPedometerAccelerometerListener != null) {
                                        mPedometerAccelerometerListener!!.StepHasBeenDone()
                                    }
                                    extType
                                } else {
                                    -1
                                }
                        }
                        mLastDiff[k] = diff
                    }
                    mLastDirections[k] = direction
                    mLastValues[k] = v
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        /*if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (accuracy != SensorManager.SENSOR_DELAY_FASTEST) {
                if (PedometerService.isListenAccelerometer() == true) {
                    mSensorManager.registerListener(PedometerAccelerometer.this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
                }
            }
        }*/
        Log.d(
            TAG,
            "onAccuracyChanged(), sensor:$sensor accuracy: $accuracy"
        )
    }

    companion object {
        private const val TAG = "StepDetector"
        private var mLimit = 15.0f
        fun setSensitivity(sensitivity: Float) {
            mLimit = sensitivity
            Log.d(TAG, "set new sensitivity: " + mLimit.toString())
        }
    }

    init {
        val h = 480 // TODO: remove this constant
        mYOffset = h * 0.5f
        mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)))
        mScale[1] = -(h * 0.5f * (1.0f / SensorManager.MAGNETIC_FIELD_EARTH_MAX))
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
}