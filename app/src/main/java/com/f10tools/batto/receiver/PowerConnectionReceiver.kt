package com.f10tools.batto.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class PowerConnectionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        println("POWER_CONNECTION_INTENT ${intent.action}")


    }
}