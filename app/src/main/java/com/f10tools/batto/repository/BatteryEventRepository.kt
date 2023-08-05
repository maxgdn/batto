package com.f10tools.batto.repository

import com.f10tools.batto.domain.BatteryEvent
import com.f10tools.batto.domain.BatteryEventStatistics
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface BatteryEventRepository {

    fun getAllEvents(): Flowable<List<BatteryEvent>>

    fun getById(id: String): Flowable<BatteryEvent>

    fun getLatestBatteryEvent(): Flowable<BatteryEvent>

    fun insertEvent(event: BatteryEvent): Completable

    fun getBatteryEventStatistics(bound: String): Flowable<BatteryEventStatistics>
}