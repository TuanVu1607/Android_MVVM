package com.technology.admob_lib.utils

import android.view.View
import android.view.ViewGroup.OnHierarchyChangeListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

object AdmobNativeAdUtils {
    //--------------------------------- Main Handle ------------------------------//
    private fun handleOnHierarchyChangeListener() = object : OnHierarchyChangeListener {
        override fun onChildViewAdded(parent: View, child: View) {
            if (child is ImageView) {
                child.adjustViewBounds = true
            }
        }

        override fun onChildViewRemoved(parent: View, child: View) {}
    }

    private fun handleIconAppView(ad: NativeAd, iconView: ImageView) {
        ad.icon?.let {
            iconView.setImageDrawable(it.drawable)
            iconView.isVisible = true
        } ?: run {
            iconView.isVisible = false
        }
    }

    private fun handleMediaView(adView: NativeAdView, mediaView: MediaView) {
        mediaView.setImageScaleType(ImageView.ScaleType.FIT_XY)
        mediaView.setOnHierarchyChangeListener(handleOnHierarchyChangeListener())
        adView.mediaView = mediaView
    }

    private fun handleActionNativeAd(ad: NativeAd, adView: NativeAdView, vararg views: View) {
        for (view in views) {
            when (view) {
                is Button -> view.text = ad.callToAction
                is TextView -> view.text = ad.callToAction
                else -> {}
            }
            adView.callToActionView = view
        }
    }
    //----------------------------------------------------------------------------//
}