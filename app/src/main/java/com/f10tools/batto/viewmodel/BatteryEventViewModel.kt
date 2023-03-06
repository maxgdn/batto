package com.f10tools.batto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f10tools.batto.domain.BatteryEvent
import com.f10tools.batto.repository.BatteryEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class BatteryEventViewModel @Inject constructor(
    private val batteryEventRepository: BatteryEventRepository
): ViewModel() {

    private val _allBatteryEventsState = BehaviorSubject.create<List<BatteryEvent>>()
    val allBatteryEventsState: Observable<List<BatteryEvent>>
        get() = _allBatteryEventsState

    val setBatteryState: (List<BatteryEvent>) -> Unit = {
        _allBatteryEventsState.onNext(it)
    }

    fun getAllEvents() = viewModelScope.launch(Dispatchers.IO) {
        batteryEventRepository
            .getAllEvents()
            .subscribe(setBatteryState)
    }

    fun getBatteryEventById(id: String): Flowable<BatteryEvent> = batteryEventRepository.getById(id)

    fun insertBatteryEvent(event: BatteryEvent) = viewModelScope.launch(Dispatchers.IO) {
        batteryEventRepository
            .insertEvent(event)
            .onErrorResumeNext {
                println(it.stackTrace)
                println(it.message)
                Completable.complete()
            }.subscribe(System.out::println)
    }
}