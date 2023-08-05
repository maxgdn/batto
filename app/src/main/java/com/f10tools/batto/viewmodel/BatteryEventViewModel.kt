package com.f10tools.batto.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.f10tools.batto.Constants.BATTERY_EVENT_WORK_NAME
import com.f10tools.batto.domain.BatteryEvent
import com.f10tools.batto.domain.BatteryEventStatistics
import com.f10tools.batto.repository.BatteryEventRepository
import com.f10tools.batto.workers.BatteryEventWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class BatteryEventViewModel @Inject constructor(
    application: Application,
    private val batteryEventRepository: BatteryEventRepository
): ViewModel() {

    private val workManager = WorkManager.getInstance(application)
    init {
        workManager.enqueueUniquePeriodicWork(
            BATTERY_EVENT_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            PeriodicWorkRequest.Builder(BatteryEventWorker::class.java, 15L, TimeUnit.MINUTES).build()
        )
    }

    private val _allBatteryEventsState = BehaviorSubject.create<List<BatteryEvent>>()
    val allBatteryEventsState: Observable<List<BatteryEvent>>
        get() = _allBatteryEventsState

    val setAllBatteryState: (List<BatteryEvent>) -> Unit = {
        _allBatteryEventsState.onNext(it)
    }

    fun getAllEvents() = viewModelScope.launch(Dispatchers.IO) {
        batteryEventRepository
            .getAllEvents()
            .subscribe(setAllBatteryState)
    }

    private val _latestBatteryEventsState = BehaviorSubject.create<BatteryEvent>()

    val latestBatteryEventsState: Observable<BatteryEvent>
        get() = _latestBatteryEventsState

    val setLatestBatteryEvent: (BatteryEvent) -> Unit = {
        _latestBatteryEventsState.onNext(it)
    }
    fun getLatestBatteryEvent() = viewModelScope.launch(Dispatchers.IO) {
        batteryEventRepository
            .getLatestBatteryEvent()
            .subscribe(setLatestBatteryEvent)
    }

    val setBatteryByIdState: (BatteryEvent) -> Unit = {
        println("BY_ID ${it}")
    }
    fun getBatteryEventById(id: String) = viewModelScope.launch {
        val idz = "3db6294f-fd0c-4c0c-b940-72db5b1cc220"
        batteryEventRepository.getById(idz).subscribe(setBatteryByIdState)
    }

    fun insertBatteryEvent(event: BatteryEvent) = viewModelScope.launch(Dispatchers.IO) {
        batteryEventRepository
            .insertEvent(event)
            .onErrorResumeNext {
                println(it.stackTrace)
                println(it.message)
                Completable.complete()
            }.subscribe(System.out::println)
    }

    private val _statisticsState = BehaviorSubject.create<BatteryEventStatistics>()

    val statisticsState: Observable<BatteryEventStatistics>
        get() = _statisticsState

    val setStatisticsState: (BatteryEventStatistics) -> Unit = {
        println("DOOPO ${it}")
        _statisticsState.onNext(it)
    }

    fun getBatteryEventStatistics(bound: String) = viewModelScope.launch(Dispatchers.IO) {
        batteryEventRepository
            .getBatteryEventStatistics(bound)
            .doOnError {
                println("ERROR")
                it.printStackTrace()
            }
            .subscribe(setStatisticsState)
    }
}