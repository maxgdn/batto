package com.f10tools.batto.util

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

import com.f10tools.batto.domain.BatteryEvent
import java.util.UUID

fun Context.pollBattery(): BatteryEvent {
    val context = this
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
        context.registerReceiver(null, ifilter)
    }

    val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
    val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
            || status == BatteryManager.BATTERY_STATUS_FULL

    val chargePlug: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1

    val batteryPercentage: Float = batteryStatus?.let { intent ->
        val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        level * 100 / scale.toFloat()
    } ?: -1f
    val batteryHealth = batteryStatus?.getIntExtra(BatteryManager.EXTRA_HEALTH,-1) ?: -1
    val batteryTechnology = batteryStatus?.extras?.getString(BatteryManager.EXTRA_TECHNOLOGY)
    val batteryPresent = batteryStatus?.extras?.getBoolean(BatteryManager.EXTRA_PRESENT)
    val batteryTemperature = batteryStatus?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)?.toFloat()?.div(10)
    val batteryVoltage = batteryStatus?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)?.toFloat()?.div(1000)

    return BatteryEvent(
        uuid = UUID.randomUUID().toString(),
        status = status,
        isCharging = isCharging,
        chargePlug = chargePlug,
        batteryPercentage = batteryPercentage,
        batteryHealth = batteryHealth,
        batteryTechnology = batteryTechnology,
        batteryPresent = batteryPresent,
        batteryTemperature = batteryTemperature,
        batteryVoltage = batteryVoltage,
        timestamp = currentUTCTime()
    )
}