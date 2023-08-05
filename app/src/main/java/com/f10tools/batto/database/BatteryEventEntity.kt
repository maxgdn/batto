package com.f10tools.batto.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.f10tools.batto.domain.BatteryEvent

@Entity(tableName = "battery_event")
data class BatteryEventEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "status") val status: Int,
    @ColumnInfo(name = "isCharging") val isCharging: Boolean,
    @ColumnInfo(name = "chargePlug") val chargePlug: Int,
    @ColumnInfo(name = "batteryPercentage") val batteryPercentage: Float,
    @ColumnInfo(name = "batteryHealth") val batteryHealth: Int,
    @ColumnInfo(name = "batteryTechnology") val batteryTechnology: String?,
    @ColumnInfo(name = "batteryPresent") val batteryPresent: Boolean?,
    @ColumnInfo(name = "batteryTemperature") val batteryTemperature: Float?,
    @ColumnInfo(name = "batteryVoltage") val batteryVoltage: Float?,
    @ColumnInfo(name = "batteryCurrent") val batteryCurrent: Int,
    @ColumnInfo(name = "batteryCurrentAverage") val batteryCurrentAverage: Int,
    @ColumnInfo(name = "batteryEnergyCounter") val batteryEnergyCounter: Int,
    @ColumnInfo(name = "batteryChargeCounter") val batteryChargeCounter: Int,
    @ColumnInfo(name = "timestamp") val timestamp: String,
)

fun BatteryEventEntity.toBatteryEvent(): BatteryEvent {
    return BatteryEvent(
        uuid = uuid,
        status = status,
        isCharging = isCharging,
        chargePlug = chargePlug,
        batteryPercentage = batteryPercentage,
        batteryHealth = batteryHealth,
        batteryTechnology = batteryTechnology,
        batteryPresent = batteryPresent,
        batteryTemperature = batteryTemperature,
        batteryVoltage = batteryVoltage,
        batteryCurrent = batteryCurrent,
        batteryCurrentAverage = batteryCurrentAverage,
        batteryEnergyCounter = batteryEnergyCounter,
        batteryChargeCounter = batteryChargeCounter,
        timestamp = timestamp
    )
}

fun fromBatteryEvent(event: BatteryEvent): BatteryEventEntity {
    return BatteryEventEntity(
        uuid = event.uuid,
        status = event.status,
        isCharging = event.isCharging,
        chargePlug = event.chargePlug,
        batteryPercentage = event.batteryPercentage,
        batteryHealth = event.batteryHealth,
        batteryTechnology = event.batteryTechnology,
        batteryPresent = event.batteryPresent,
        batteryTemperature = event.batteryTemperature,
        batteryVoltage = event.batteryVoltage,
        batteryCurrent = event.batteryCurrent,
        batteryCurrentAverage = event.batteryCurrentAverage,
        batteryEnergyCounter = event.batteryEnergyCounter,
        batteryChargeCounter = event.batteryChargeCounter,
        timestamp = event.timestamp
    )
}
