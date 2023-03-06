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
    fun findById(uuid: String): Flowable<BatteryEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(batteryEvent: BatteryEventEntity): Completable
}