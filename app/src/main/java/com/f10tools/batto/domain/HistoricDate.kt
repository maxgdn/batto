package com.f10tools.batto.domain

import java.time.Instant
import java.time.temporal.ChronoUnit

object HistoricDate {
    fun oneDayAgo(): String {
        return Instant.now().run {
            minus(1, ChronoUnit.DAYS)
        }.toString()
    }
    fun oneWeekAgo(): String {
        return Instant.now().run {
            minus(7, ChronoUnit.DAYS)
        }.toString()
    }
    fun oneMonthAgo(): String {
        return Instant.now().run {
            minus(1, ChronoUnit.MONTHS)
        }.toString()
    }
    fun oneYearAgo(): String {
        return Instant.now().run {
            minus(1, ChronoUnit.YEARS)
        }.toString()
    }
    fun allTime(): String {
        return Instant.EPOCH.toString()
    }
}