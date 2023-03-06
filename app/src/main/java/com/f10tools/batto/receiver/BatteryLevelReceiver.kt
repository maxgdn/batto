package com.f10tools.batto.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BatteryLevelReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        println("BATTERY_LEVEL_INTENT ${intent.action}")


    }
}