package com.f10tools.batto.repository

import com.f10tools.batto.database.AppDatabase
import com.f10tools.batto.database.fromBatteryEvent
import com.f10tools.batto.database.toBatteryEvent
import com.f10tools.batto.domain.BatteryEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class BatteryEventRepositoryImpl @Inject constructor(private val db: AppDatabase): BatteryEventRepository {
    override fun getAllEvents(): Flowable<List<BatteryEvent>> {
        return db
            .batteryEventDao()
            .getAll()
            .map {
                it.map { it.toBatteryEvent() }
            }
    }

    override fun getById(id: String): Flowable<BatteryEvent> {
        return db
            .batteryEventDao()
            .findById(id)
            .map { it.toBatteryEvent() }
    }

    override fun insertEvent(event: BatteryEvent): Completable {
        val entity = fromBatteryEvent(event)

        return db
            .batteryEventDao()
            .insertOne(entity)
    }
}