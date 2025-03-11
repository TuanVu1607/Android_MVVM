package com.technology.android_mvvm.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager


/**
 * Request to hide the soft input window from the Activity.
 */
fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

/**
 * Detect if the activity was launched from Recent Apps List.
 */
fun Activity.isActivityLaunchedFromHistory(): Boolean {
    val flags = intent.flags
    return flags and Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY != 0
}

/**
 * Restart the application by start the Activity in new task from recent history.
 *
 * @param clazz Activity class
 */
fun Activity.restartApp(clazz: Class<*>) {
    val intent = Intent(this, clazz).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
    finish()
    Runtime.getRuntime().exit(0)
}