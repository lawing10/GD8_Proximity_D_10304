package com.example.gd8_proximity_d_10304

import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var square: TextView

    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_N
    O)
    square = findViewById(R.id.tv_square)
    setUpSensorStuff()

    sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
// Specify the sensor you want to listen to
    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also
    { accelerometer ->
        sensorManager.registerListener(
            this,
            accelerometer,
            SensorManager.SENSOR_DELAY_FASTEST,
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }
    override fun onSensorChanged(event: SensorEvent?) {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            return
        }
        override fun onSensorChanged(event: SensorEvent?) {
            // Checks for the sensor we have registered
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                //Log.d("Main", "onSensorChanged: sides ${event.values[0]}
                front/back ${event.values[1]} ")
                // Sides = Tilting phone left(10) and right(-10)
                val sides = event.values[0]
                // Up/Down = Tilting phone up(10), flat (0), upside-down(-
                10)

                val upDown = event.values[1]
                square.apply {
                    rotationX = upDown * 3f
                    rotationY = sides * 3f
                    rotation = -sides
                    translationX = sides * -10
                    translationY = upDown * 10
                }
                // Changes the colour of the square if it's completely flat
                val color = if (upDown.toInt() == 0 && sides.toInt() == 0)
                    Color.GREEN else Color.RED
                square.setBackgroundColor(color)
                square.text = "up/down ${upDown.toInt()}\nleft/right
                ${sides.toInt()}"

                override fun onDestroy() {
                    sensorManager.unregisterListener(this)
                    super.onDestroy()
                }

            }

    }
}
