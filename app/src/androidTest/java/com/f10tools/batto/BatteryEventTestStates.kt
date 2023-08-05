package com.f10tools.batto

import android.os.BatteryManager.BATTERY_HEALTH_COLD
import android.os.BatteryManager.BATTERY_HEALTH_DEAD
import android.os.BatteryManager.BATTERY_HEALTH_GOOD
import android.os.BatteryManager.BATTERY_HEALTH_OVERHEAT
import android.os.BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE
import android.os.BatteryManager.BATTERY_HEALTH_UNKNOWN
import android.os.BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE
import android.os.BatteryManager.BATTERY_PLUGGED_AC
import android.os.BatteryManager.BATTERY_PLUGGED_DOCK
import android.os.BatteryManager.BATTERY_PLUGGED_USB
import android.os.BatteryManager.BATTERY_PLUGGED_WIRELESS
import android.os.BatteryManager.BATTERY_STATUS_CHARGING
import android.os.BatteryManager.BATTERY_STATUS_DISCHARGING
import android.os.BatteryManager.BATTERY_STATUS_FULL
import android.os.BatteryManager.BATTERY_STATUS_NOT_CHARGING
import android.os.BatteryManager.BATTERY_STATUS_UNKNOWN
import com.f10tools.batto.domain.BatteryEvent

object BatteryEventTestStates {

    /*
    * Proto BatteryEvent
    * */
    val PROTO = BatteryEvent(
        uuid="ad7105d6-e84a-447a-a00a-6be0db974bfe",
        status=3,
        isCharging=false,
        chargePlug=0,
        batteryPercentage=88.0f,
        batteryHealth=2,
        batteryTechnology="Li-ion",
        batteryPresent=true,
        batteryTemperature=28.2f,
        batteryVoltage=4.085f,
        batteryCurrent = 252,
        batteryCurrentAverage = 246,
        batteryEnergyCounter = 214740, //TODO find real values
        batteryChargeCounter = 3350000,
        timestamp="2023-03-10T03:41:18.599Z"
    )

    /*
    * Battery status
    * */
    val CHARGING = PROTO.copy(
        uuid = "a714764e-8e13-4b18-b8b8-cbed32c15ddc",
        status = BATTERY_STATUS_CHARGING,
        isCharging = true,
        chargePlug = BATTERY_PLUGGED_AC,
    )
    val DISCHARGING = PROTO.copy(
        uuid = "0f773663-320d-4da6-8174-fd768545cd01",
        status = BATTERY_STATUS_DISCHARGING,
    )
    val FULL = PROTO.copy(
        uuid = "5d211630-c48f-43f4-a3f8-677a1c2dafd2",
        status = BATTERY_STATUS_FULL,
    )
    val NOT_CHARGING = PROTO.copy(
        uuid = "66f45bf7-fd31-4b1c-9680-48eb3ea21b80",
        status = BATTERY_STATUS_NOT_CHARGING,
    )
    val UNKNOWN = PROTO.copy(
        uuid = "a4a607e1-cd72-4e2a-a335-fc26e580ec40",
        status = BATTERY_STATUS_UNKNOWN,
    )

    /*
    * Battery charging [isCharging]
    * */
    val IS_CHARGING = PROTO.copy(
        uuid = "0feeb7f3-4058-4879-a9b0-01f5ad8526a0",
        status = BATTERY_STATUS_CHARGING,
        isCharging = true,
        chargePlug = BATTERY_PLUGGED_AC,
    )
    val IS_DISCHARGING = PROTO.copy(
        uuid = "4d6c5929-696a-4db2-8b4e-83ab42afeb64",
        status = BATTERY_STATUS_DISCHARGING,
        isCharging = false,
    )

    /*
    * Battery chargePlug
    * */
    val PLUGGED_AC = PROTO.copy(
        uuid = "8730aa37-67da-4e0c-be7f-e3f06e900060",
        status = BATTERY_STATUS_CHARGING,
        chargePlug = BATTERY_PLUGGED_AC,
        isCharging = true,
    )
    val PLUGGED_DOCK = PROTO.copy(
        uuid = "7766b8be-b2a6-42f9-ba5a-8032c45966fc",
        status = BATTERY_STATUS_CHARGING,
        chargePlug = BATTERY_PLUGGED_DOCK,
        isCharging = true,
    )
    val PLUGGED_USB = PROTO.copy(
        uuid = "fd62b7a9-5953-4f0f-9701-84a93dd4d14d",
        status = BATTERY_STATUS_CHARGING,
        chargePlug = BATTERY_PLUGGED_USB,
        isCharging = true,
    )
    val PLUGGED_WIRELESS = PROTO.copy(
        uuid = "85f75044-6429-4f90-ae04-54afbd4ae422",
        status = BATTERY_STATUS_CHARGING,
        chargePlug = BATTERY_PLUGGED_WIRELESS,
        isCharging = true,
    )

    /*
    * Charge levels
    * */
    val FULL_CHARGE = PROTO.copy(
        uuid = "85f75044-6429-4f90-ae04-54afbd4ae422",
        status = BATTERY_STATUS_FULL,
        batteryPercentage = 100f
    )
    val `80_PERCENT_CHARGE` = PROTO.copy(
        uuid = "5aaddd28-0fc3-43db-9cfb-79ce5499fbf6",
        batteryPercentage = 80f
    )
    val `60_PERCENT_CHARGE` = PROTO.copy(
        uuid = "047ecbca-3a84-48aa-8794-4308f259c429",
        batteryPercentage = 60f
    )
    val `40_PERCENT_CHARGE` = PROTO.copy(
        uuid = "b4f8b338-7617-4bf2-95ef-f2fc968fc1b7",
        batteryPercentage = 40f
    )
    val `20_PERCENT_CHARGE` = PROTO.copy(
        uuid = "42e58abe-94bb-44ab-ad04-01e717b44b69",
        batteryPercentage = 20f
    )
    val `10_PERCENT_CHARGE` = PROTO.copy(
        uuid = "c0869181-473f-4212-892c-e9e277d2ce8a",
        batteryPercentage = 10f
    )
    val `5_PERCENT_CHARGE` = PROTO.copy(
        uuid = "cc2e2563-57ce-4a40-805c-b88ed34ddf01",
        batteryPercentage = 5f
    )
    val `1_PERCENT_CHARGE` = PROTO.copy(
        uuid = "f01596f1-9be1-45ab-87d8-45b833062289",
        batteryPercentage = 1f
    )
    val NO_CHARGE = PROTO.copy(
        uuid = "2ac1ef22-40b0-4873-ad58-f03653528465",
        status = BATTERY_STATUS_UNKNOWN,
        batteryPercentage = 0f
    )

    /*
    * Battery health
    * */
    val HEALTH_GOOD = PROTO.copy(
        uuid = "44d0a3b9-b3fe-42a1-a9c0-c299d59e75f8",
        batteryHealth = BATTERY_HEALTH_GOOD
    )
    val HEALTH_DEAD = PROTO.copy(
        uuid = "3f50bd4c-fc8b-4307-8629-d0300167ced0",
        batteryHealth = BATTERY_HEALTH_DEAD
    )
    val HEALTH_OVERHEAT = PROTO.copy(
        uuid = "b0e6a536-2ece-420f-90e7-9e03d8ecf1e2",
        batteryHealth = BATTERY_HEALTH_OVERHEAT,
        batteryTemperature = 45f
    )
    val HEALTH_COLD = PROTO.copy(
        uuid = "d994d35c-e9f7-44b8-a321-6f3eee139ae4",
        batteryHealth = BATTERY_HEALTH_COLD,
        batteryTemperature = -10f
    )
    val HEALTH_OVER_VOLTAGE = PROTO.copy(
        uuid = "63e60de9-4aba-49d5-94c9-f7de2a3d6e91",
        batteryHealth = BATTERY_HEALTH_OVER_VOLTAGE,
        batteryVoltage = 16.1f
    )
    val HEALTH_FAILURE =  PROTO.copy(
        uuid = "2ac31212-0cc8-4d6d-9cfb-b18e8cec8db3",
        batteryHealth = BATTERY_HEALTH_UNSPECIFIED_FAILURE
    )
    val HEALTH_UNKNOWN =  PROTO.copy(
        uuid = "e58249c1-222d-4312-a1b4-46a7d487aa87",
        batteryHealth = BATTERY_HEALTH_UNKNOWN
    )

    /*
    * Battery present
    * */
    val BATTERY_PHYSICALLY_PRESENT = PROTO.copy(
        uuid = "78599cb0-42c5-454a-8515-43760b278dd9",
        batteryPresent = true
    )
    val BATTERY_PHYSICALLY_NOT_PRESENT = PROTO.copy(
        uuid = "43dd36bb-d8be-44a2-a1f9-07e7ec0f5c23",
        batteryPresent = false
    )

    /*
    * Battery temperature
    * */
    val HOT_TEMPERATURE = PROTO.copy(
        uuid = "424b1dce-f2aa-4954-9b97-b40c865913cc",
        batteryHealth = BATTERY_HEALTH_OVERHEAT,
        batteryTemperature = 45f
    )
    val NORMAL_TEMPERATURE = PROTO.copy(
        uuid = "47628301-4188-497c-b891-4b0d23307ab0",
        batteryHealth = BATTERY_HEALTH_GOOD,
        batteryTemperature = 33f
    )
    val COLD_TEMPERATURE = PROTO.copy(
        uuid = "87af3b26-7bd9-4151-9ab8-22958ef4d292",
        batteryHealth = BATTERY_HEALTH_COLD,
        batteryTemperature = -10f
    )

    /*
    * Battery technology -- TODO how does this surface, what are the possible varied string values
    * */

    /*
    * Battery voltage
    * */
    val BATTERY_VOLTAGE_NONE = PROTO.copy(
        uuid = "38f785a2-ed43-4db2-b174-cd3c2eb49879",
        batteryVoltage = 0f
    )
    val BATTERY_VOLTAGE_LOW = PROTO.copy(
        uuid = "9342ef36-5b4c-4205-a9ec-4c7eee5f0020",
        batteryVoltage = 0.5f
    )
    val BATTERY_VOLTAGE_NORMAL = PROTO.copy(
        uuid = "8d3f1f9e-29da-4cac-a0ef-706e4d15e819",
    )
    val BATTERY_VOLTAGE_OVERPOWERED = PROTO.copy(
        uuid = "3e5e8f77-f41e-4e6e-b9fa-80d6139e6f15",
        batteryHealth = BATTERY_HEALTH_OVER_VOLTAGE,
        batteryVoltage = 16.1f
    )


    /*
    * Battery current
    * */
    val BATTERY_CURRENT_NONE = PROTO.copy(
        uuid = "f5e4770b-3ce3-4644-ac59-b05213141b90",
        batteryCurrent = 0
    )
    val BATTERY_CURRENT_LOW = PROTO.copy(
        uuid = "4f535176-14ce-4557-b12e-26018efcd1a5",
        batteryCurrent = 50
    )
    val BATTERY_CURRENT_NORMAL = PROTO.copy(
        uuid = "c69a3fac-8d0b-418e-a1df-83bc7c6e7cee",
    )
    val BATTERY_CURRENT_HIGH = PROTO.copy(
        uuid = "d084cb5b-d427-42cd-9913-413eb08c33d5",
        batteryCurrent = 500
    )
}