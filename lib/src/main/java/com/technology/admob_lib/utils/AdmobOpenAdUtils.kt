package com.technology.admob_lib.utils

import java.util.Date

private const val MAX_TIME_TO_SHOW_AD = 2

object AdmobOpenAdUtils {
    private var loadTime = 0L

    fun updateLoadTime() {
        loadTime = Date().time
    }

    fun isNotExpired(): Boolean {
        val dateDifference: Long = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * MAX_TIME_TO_SHOW_AD
    }
}