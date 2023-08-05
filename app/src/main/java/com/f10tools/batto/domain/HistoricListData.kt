package com.f10tools.batto.domain

data class HistoricListData(
    val historicTemperatures: List<Float>,
    val historicCharges: List<Int>,
    val historicVoltages: List<Float>,
    val historicEnergy: List<Int>,
    val historicBatteryPercentage: List<Float>,
)
