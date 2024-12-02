package com.technology.android_mvvm.common.extensions

import java.util.Locale

fun String.capitalize(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

fun String.uppercase(): String = uppercase(Locale.getDefault())

fun String.lowercase(): String = lowercase(Locale.getDefault())