package com.f10tools.batto.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.f10tools.batto.repository.BatteryEventRepository
import com.f10tools.batto.util.pollBattery
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BatteryEventWorker @AssistedInject constructor(
    private val batteryEventRepository: BatteryEventRepository,
    @Assisted val context: Context,
    @Assisted val params: WorkerParameters
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Log.d("WORK_MANAGER_TEST", "YEP")
        val event = context.pollBattery()
        batteryEventRepository.insertEvent(event).blockingAwait()
        return Result.success()
    }
}