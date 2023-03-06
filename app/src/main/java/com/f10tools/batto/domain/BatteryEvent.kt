package com.f10tools.batto.domain

/*
* Represents an individual Battery Event
*
*   @property uuid unique identifier of the event as UUIDV4 format
*   @property status indicates the charge status one of BatteryManager.BATTERY_STATUS_*.
*   @property isCharging if the device was charging at the time of the event.
*   @property chargePlug how the device was being charged. Zero indicates battery while other values are one of BatteryManager.BATTERY_PLUGGED_*.
*   @property batteryPercentage expressed as a float the percentage of charge remaining in the battery.
*   @property batteryHealth recorded batter health, one of BatteryManager.BATTERY_HEALTH_*.
*   @property batteryTechnology displays the technology of the battery.
*   @property batteryPresent if a battery is in the device.
*   @property batteryTemperature temperature of battery in Celsius.
*   @property batteryVoltage voltage in unit V.
*   @property timestamp in UTC ISO 8601 format
*
* */
data class BatteryEvent(
    val uuid: String,
    val status: Int = -1,
    val isCharging: Boolean = false,
    val chargePlug: Int = -1,
    val batteryPercentage: Float = -1f,
    val batteryHealth: Int = -1,
    val batteryTechnology: String? = null,
    val batteryPresent: Boolean? = null,
    val batteryTemperature: Float? = null,
    val batteryVoltage: Float? = null,
    val timestamp: String,
)
