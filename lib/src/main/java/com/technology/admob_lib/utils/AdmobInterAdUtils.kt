package com.technology.admob_lib.utils

import java.util.Calendar
import java.util.Date

object AdmobInterAdUtils {
    //Tính số giây đã trôi qua
    fun getElapsedSeconds(inputDate: Date): Long {
        val today = Calendar.getInstance()
        val givenDate = Calendar.getInstance()

        givenDate.time = inputDate

        // Tính khoảng cách thời gian (milliseconds)
        val diffMillis = today.timeInMillis - givenDate.timeInMillis

        // Chuyển đổi sang giây
        return diffMillis / 1000
    }
}