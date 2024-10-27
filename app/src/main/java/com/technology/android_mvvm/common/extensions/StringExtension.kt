package com.technology.android_mvvm.common.extensions

import com.technology.android_mvvm.utils.LoggerUtils
import java.util.Locale

fun String.capitalize(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

fun String.uppercase(): String = uppercase(Locale.getDefault())

fun String.lowercase(): String = lowercase(Locale.getDefault())

fun String.debug(message: String) {
    LoggerUtils.d(this, message)
}