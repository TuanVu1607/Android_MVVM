package com.technology.android_mvvm.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextUtils {
    @BindingAdapter("marqueeEnabled")
    @JvmStatic
    fun setMarqueeEnabled(view: TextView, isMarqueeEnabled: Boolean) {
        view.setHorizontallyScrolling(true) // Enable horizontal scrolling
        view.ellipsize = android.text.TextUtils.TruncateAt.MARQUEE
        view.marqueeRepeatLimit = -1 // Repeat indefinitely
        view.maxLines = 1 // Limit 1 lines displayed
        view.isSelected = true // Enable marquee effect
    }
}