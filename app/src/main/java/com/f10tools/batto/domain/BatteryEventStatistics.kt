package com.f10tools.batto.domain

data class BatteryEventStatistics(
    val averageBatteryTemperature: Float,
    val minimumBatteryTemperature: Float,
    val maximumBatteryTemperature: Float,
    val averageCurrent: Float,
    val minimumCurrent: Float,
    val maximumCurrent: Float,
    val historicTemperatures: List<Float>,
    val historicCharges: List<Int>,
    val historicVoltages: List<Float>,
    val historicEnergy: List<Int>,
    val historicBatteryPercentage: List<Float>,
)
