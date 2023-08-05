package com.f10tools.batto.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface BatteryEventDao {
    @Query("SELECT * FROM battery_event")
    fun getAll(): Flowable<List<BatteryEventEntity>>

    @Query("SELECT * FROM battery_event WHERE uuid <> :uuid")
    fun findById(uuid: String): Flowable<List<BatteryEventEntity>>

    @Query("SELECT * from battery_event ORDER BY timestamp DESC LIMIT 1")
    fun getLatestBatteryEvent(): Flowable<List<BatteryEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(batteryEvent: BatteryEventEntity): Completable

    @Query("SELECT COUNT(*) FROM battery_event")
    fun totalBatteryEventCount(): Flowable<Int>

    /*
    * Temperature Queries
    * */
    //@Query("SELECT avg(batteryTemperature) FROM battery_event WHERE datetime(timestamp) BETWEEN datetime('now') AND datetime(:bound)")
    //
    @Query("SELECT avg(batteryTemperature) FROM battery_event WHERE datetime(timestamp) <= datetime('now') AND datetime(timestamp) >= datetime(:bound)")
    //@Query("SELECT avg(batteryTemperature) FROM battery_event")
    fun averageBatteryEventTemperature(bound: String): Flowable<Float>

    @Query("SELECT max(batteryTemperature) FROM battery_event WHERE datetime(timestamp) <= datetime('now') AND datetime(timestamp) >= datetime(:bound)")
    fun maxBatteryEventTemperature(bound: String): Flowable<Float>

    @Query("SELECT min(batteryTemperature) FROM battery_event WHERE datetime(timestamp) <= datetime('now') AND datetime(timestamp) >= datetime(:bound)")
    fun minBatteryEventTemperature(bound: String): Flowable<Float>

    /*
    * Current Queries
    * */
    @Query("SELECT avg(batteryCurrent) FROM battery_event WHERE datetime(timestamp) <= datetime('now') AND datetime(timestamp) >= datetime(:bound)")
    fun averageBatteryEventCurrent(bound: String): Flowable<Float>

    @Query("SELECT max(batteryCurrent) FROM battery_event WHERE datetime(timestamp) <= datetime('now') AND datetime(timestamp) >= datetime(:bound)")
    fun maxBatteryEventCurrent(bound: String): Flowable<Float>

    @Query("SELECT min(batteryTemperature) FROM battery_event WHERE datetime(timestamp) <= datetime('now') AND datetime(timestamp) >= datetime(:bound)")
    fun minBatteryEventCurrent(bound: String): Flowable<Float>

    /*
    *  Historic
    * */

    @Query("SELECT (batteryTemperature) FROM battery_event WHERE datetime(timestamp) <= datetime('now') AND datetime(timestamp) >= datetime(:bound) ORDER BY datetime(timestamp) DESC")
    fun historicTemperaturesByDate(bound: String): Flowable<List<Float>>

    @Query("SELECT (batteryChargeCounter) FROM battery_event WHERE datetime(timestamp) <= datetime('now') AND datetime(timestamp) >= datetime(:bound) ORDER BY datetime(timestamp) DESC")
    fun historicChargesByDate(bound: String): Flowable<List<Int>>

    @Query("SELECT (batteryVoltage) FROM battery_event WHERE datetime(timestamp) <= datetime('now') AND datetime(timestamp) >= datetime(:bound) ORDER BY datetime(timestamp) DESC")
    fun historicVoltagesByDate(bound: String): Flowable<List<Float>>

    @Query("SELECT (batteryEnergyCounter) FROM battery_event WHERE datetime(timestamp) <= datetime('now') AND datetime(timestamp) >= datetime(:bound) ORDER BY datetime(timestamp) DESC")
    fun historicEnergyByDate(bound: String): Flowable<List<Int>>

    @Query("SELECT (batteryPercentage) FROM battery_event WHERE datetime(timestamp) <= datetime('now') AND datetime(timestamp) >= datetime(:bound) ORDER BY datetime(timestamp) DESC")
    fun historicBatteryPercentagesByDate(bound: String): Flowable<List<Float>>

}