package com.technology.android_mvvm.utils

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String = "No internet connection"
}