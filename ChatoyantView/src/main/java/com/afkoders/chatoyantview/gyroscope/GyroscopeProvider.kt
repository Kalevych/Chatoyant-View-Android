package com.afkoders.chatoyantview.gyroscope

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import com.afkoders.chatoyantview.chatoyant_view.ChatoyantHelper

/**
 * Created by Kalevych Oleksandr on 19.01.2021.
 */

class GyroscopeProvider {
    var sensorManager: SensorManager? = null
    var sensor: Sensor? = null

    fun onInit(context: Context, chatoyantHelper: ChatoyantHelper) {
        if (sensorManager == null) {
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager?.registerListener(chatoyantHelper, sensor, SensorManager.SENSOR_DELAY_UI)
        }
        else {
            sensorManager?.registerListener(chatoyantHelper, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun onResumeView(chatoyantHelper: ChatoyantHelper) {
        sensorManager?.registerListener(chatoyantHelper, sensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun onPauseView(chatoyantHelper: ChatoyantHelper) {
        sensorManager?.unregisterListener(chatoyantHelper)
    }

    companion object {
        val instance = GyroscopeProvider()
    }
}