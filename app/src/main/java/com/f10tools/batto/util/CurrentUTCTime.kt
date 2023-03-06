package com.f10tools.batto.util

import java.time.Instant

fun currentUTCTime(): String {
    return Instant.now().toString()
}