package com.f10tools.batto.repository

import com.f10tools.batto.database.AppDatabase
import com.f10tools.batto.database.fromBatteryEvent
import com.f10tools.batto.database.toBatteryEvent
import com.f10tools.batto.domain.BatteryEvent
import com.f10tools.batto.domain.BatteryEventStatistics
import com.f10tools.batto.domain.HistoricListData
import com.f10tools.batto.domain.StatisticsData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler

import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import java.util.function.BiFunction
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
            .filter{ it.isNotEmpty() }
            .map { it[0] }
            .map { it.toBatteryEvent() }
    }

    override fun getLatestBatteryEvent(): Flowable<BatteryEvent> {
        return db
            .batteryEventDao()
            .getLatestBatteryEvent()
            .filter{ it.isNotEmpty() }
            .map { it[0] }
            .map { it.toBatteryEvent() }
    }

    override fun insertEvent(event: BatteryEvent): Completable {
        val entity = fromBatteryEvent(event)
        println("INSERTING ${entity}")
        return db
            .batteryEventDao()
            .insertOne(entity)
    }

    override fun getBatteryEventStatistics(bound: String): Flowable<BatteryEventStatistics> {
        val statsValuesZipper: (
            Float, Float, Float, Float, Float, Float,
        ) -> StatisticsData = {
                avgTemp: Float,
                minTemp: Float,
                maxTemp: Float,
                avgCurrent: Float,
                minCurrent: Float,
                maxCurrent: Float,
            ->
            StatisticsData(
                avgTemp,
                minTemp,
                maxTemp,
                avgCurrent,
                minCurrent,
                maxCurrent
            )
        }

        val historicListZipper: (
            List<Float>, List<Int>, List<Float>, List<Int>, List<Float>
        ) -> HistoricListData = {
                historicTemperatures: List<Float>,
                historicCharges: List<Int>,
                historicVoltages: List<Float>,
                historicEnergy: List<Int>,
                historicBatteryPercentage: List<Float>
            ->
            HistoricListData(
                historicTemperatures,
                historicCharges,
                historicVoltages,
                historicEnergy,
                historicBatteryPercentage
            )
        }

        val historicListDataZip = Flowable.zip(
            db.batteryEventDao().historicTemperaturesByDate(bound),
            db.batteryEventDao().historicChargesByDate(bound),
            db.batteryEventDao().historicVoltagesByDate(bound),
            db.batteryEventDao().historicEnergyByDate(bound),
            db.batteryEventDao().historicBatteryPercentagesByDate(bound),
            historicListZipper
        )

        val statsDataZip = Flowable.zip(
            db.batteryEventDao().averageBatteryEventTemperature(bound),
            db.batteryEventDao().minBatteryEventTemperature(bound),
            db.batteryEventDao().maxBatteryEventTemperature(bound),
            db.batteryEventDao().averageBatteryEventCurrent(bound),
            db.batteryEventDao().minBatteryEventCurrent(bound),
            db.batteryEventDao().maxBatteryEventTemperature(bound),
            statsValuesZipper
        )

        val batteryEventStatisticsZipper: (StatisticsData, HistoricListData) -> BatteryEventStatistics = { a, b ->
            BatteryEventStatistics(
                a.averageBatteryTemperature,
                a.minimumBatteryTemperature,
                a.maximumBatteryTemperature,
                a.averageCurrent,
                a.minimumCurrent,
                a.maximumCurrent,
                b.historicTemperatures,
                b.historicCharges,
                b.historicVoltages,
                b.historicEnergy,
                b.historicBatteryPercentage
            )
        }

        return Flowable.zip(
            statsDataZip,
            historicListDataZip,
            batteryEventStatisticsZipper
        )
    }
}