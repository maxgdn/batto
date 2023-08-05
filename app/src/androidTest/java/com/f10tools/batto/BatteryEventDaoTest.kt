package com.f10tools.batto

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.f10tools.batto.database.AppDatabase
import com.f10tools.batto.database.BatteryEventEntity
import com.f10tools.batto.database.fromBatteryEvent
import com.f10tools.batto.domain.BatteryEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executors


@RunWith(AndroidJUnit4::class)
class BatteryEventDaoTest {
    private lateinit var mDatabase: AppDatabase

    @Before
    @Throws(Exception::class)
    fun initDb() {
        Log.d("TEST", "Z")
        mDatabase = inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            AppDatabase::class.java
        )
        .allowMainThreadQueries()
        .build()
        Log.d("TEST", "X")
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        mDatabase.close()
    }

//    @JvmField
//    @Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertAndGetUserById() {
        runTest {
            val batteryEvent = BatteryEventTestStates.TYPICAL

            val batteryEventEntity = fromBatteryEvent(batteryEvent)

            Log.d("TEST", "A")
            mDatabase.batteryEventDao()
                .insertOne(batteryEventEntity)
                .blockingAwait()
            Log.d("TEST", "B")

            val setBatteryByIdState: (BatteryEventEntity) -> Unit = {
                Log.d("TEST", "UUID ${it.uuid}")
            }

            //Log.d("TEST", "UUID ${batteryEventEntity.uuid}")

            mDatabase.batteryEventDao()
                .findById(batteryEventEntity.uuid)
                .test()
                .awaitCount(1)
                .assertValue {
                    println("IT ${it}")
                    true
                }

//            mDatabase.batteryEventDao()
//                .getAll()
//                .test()
//                .awaitCount(1)
//                .assertValue {
//                    println("WOO ${it}")
//                    true
//                }

            Log.d("TEST", "QUACK")
//            Assert.assertEquals(retrievedById.uuid,batteryEventEntity.uuid)
//            Assert.assertEquals(retrievedById.status,batteryEventEntity.status)
//            Assert.assertEquals(retrievedById.isCharging,batteryEventEntity.isCharging)
//            Assert.assertEquals(retrievedById.chargePlug,batteryEventEntity.chargePlug)
//            Assert.assertEquals(retrievedById.batteryPercentage,batteryEventEntity.batteryPercentage)
//            Assert.assertEquals(retrievedById.batteryHealth,batteryEventEntity.batteryHealth)
//            Assert.assertEquals(retrievedById.batteryTechnology,batteryEventEntity.batteryTechnology)
//            Assert.assertEquals(retrievedById.batteryPresent,batteryEventEntity.batteryPresent)
//            Assert.assertEquals(retrievedById.batteryTemperature,batteryEventEntity.batteryTemperature)
//            Assert.assertEquals(retrievedById.batteryVoltage,batteryEventEntity.batteryVoltage)
//            Assert.assertEquals(retrievedById.timestamp,batteryEventEntity.timestamp)

            //assert(true)
        }
    }
}