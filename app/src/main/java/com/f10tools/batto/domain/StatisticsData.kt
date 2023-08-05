package com.f10tools.batto.domain

data class StatisticsData(
    val averageBatteryTemperature: Float,
    val minimumBatteryTemperature: Float,
    val maximumBatteryTemperature: Float,
    val averageCurrent: Float,
    val minimumCurrent: Float,
    val maximumCurrent: Float,
)
