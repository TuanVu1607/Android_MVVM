package com.technology.android_mvvm.utils

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

object TextUtils {
    @BindingAdapter("marqueeEnabled")
    @JvmStatic
    fun setMarqueeEnabled(view: TextView, isMarqueeEnabled: Boolean) {
        view.setHorizontallyScrolling(true) // Enable horizontal scrolling
        view.ellipsize = android.text.TextUtils.TruncateAt.MARQUEE
        view.marqueeRepeatLimit = -1 // Repeat indefinitely
        view.maxLines = 1 // Limit 1 lines displayed
        view.isSelected = isMarqueeEnabled // Enable marquee effect
    }

    @BindingAdapter("drawableStart")
    @JvmStatic
    fun setDrawableStart(view: TextView, resourceId: Int) {
        val drawable = try {
            ContextCompat.getDrawable(view.context, resourceId)
        } catch (e: Exception) {
            null
        }
        drawable?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            val drawables = view.compoundDrawables
            view.setCompoundDrawables(it, drawables[1], drawables[2], drawables[3])
        }
    }

    @BindingAdapter("drawableStart")
    @JvmStatic
    fun setDrawableStart(view: TextView, drawable: Drawable?) {
        drawable?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            val drawables = view.compoundDrawablesRelative
            view.setCompoundDrawablesRelative(it, drawables[1], drawables[2], drawables[3])
        }
    }

    @BindingAdapter("drawableEnd")
    @JvmStatic
    fun setDrawableEnd(view: TextView, resourceId: Int) {
        val drawable = try {
            ContextCompat.getDrawable(view.context, resourceId)
        } catch (e: Exception) {
            null
        }
        drawable?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            val drawables = view.compoundDrawables
            view.setCompoundDrawables(drawables[0], drawables[1], it, drawables[3])
        }
    }

    @BindingAdapter("drawableEnd")
    @JvmStatic
    fun setDrawableEnd(view: TextView, drawable: Drawable?) {
        drawable?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            val drawables = view.compoundDrawablesRelative
            view.setCompoundDrawables(drawables[0], drawables[1], it, drawables[3])
        }
    }

    @BindingAdapter("drawableTop")
    @JvmStatic
    fun setDrawableTop(view: TextView, resourceId: Int) {
        val drawable = try {
            ContextCompat.getDrawable(view.context, resourceId)
        } catch (e: Exception) {
            null
        }
        drawable?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            val drawables = view.compoundDrawables
            view.setCompoundDrawables(drawables[0], it, drawables[2], drawables[3])
        }
    }

    @BindingAdapter("drawableTop")
    @JvmStatic
    fun setDrawableTop(view: TextView, drawable: Drawable?) {
        drawable?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            val drawables = view.compoundDrawablesRelative
            view.setCompoundDrawables(drawables[0], it, drawables[2], drawables[3])
        }
    }

    @BindingAdapter("drawableBottom")
    @JvmStatic
    fun setDrawableBottom(view: TextView, resourceId: Int) {
        val drawable = try {
            ContextCompat.getDrawable(view.context, resourceId)
        } catch (e: Exception) {
            null
        }
        drawable?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            val drawables = view.compoundDrawables
            view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], it)
        }
    }

    @BindingAdapter("drawableBottom")
    @JvmStatic
    fun setDrawableBottom(view: TextView, drawable: Drawable?) {
        drawable?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            val drawables = view.compoundDrawablesRelative
            view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], it)
        }
    }
}