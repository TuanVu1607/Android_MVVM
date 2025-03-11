package com.technology.admob_lib.data.datasources

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform

class AdsConsentManager private constructor(context: Context) {
    private val consentInformation: ConsentInformation =
        UserMessagingPlatform.getConsentInformation(context)

    fun interface OnConsentGatheringCompleteListener {
        fun consentCollectingComplete(error: FormError?)
    }

    val canRequestAds: Boolean
        get() = consentInformation.canRequestAds()

    val isPrivacyRequired: Boolean
        get() =
            consentInformation.privacyOptionsRequirementStatus ==
                    ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED

    fun collectConsent(
        activity: Activity,
        onConsentGatheringCompleteListener: OnConsentGatheringCompleteListener,
    ) {
        val debugSettings =
            ConsentDebugSettings.Builder(activity)
                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                .addTestDeviceHashedId( "9421A7997964B00BB62B1DA74F4C6D9C")//Id máy
                .build()

        val consentParam =
            ConsentRequestParameters.Builder().setConsentDebugSettings(debugSettings).build()

        consentInformation.requestConsentInfoUpdate(
            activity,
            consentParam,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { formError ->
                    onConsentGatheringCompleteListener.consentCollectingComplete(formError)
                }
            },
            { requestConsentError ->
                onConsentGatheringCompleteListener.consentCollectingComplete(requestConsentError)
            },
        )

    }

    fun showPrivacyForm(
        activity: Activity,
        onConsentFormDismissedListener: ConsentForm.OnConsentFormDismissedListener,
    ) {
        UserMessagingPlatform.showPrivacyOptionsForm(activity, onConsentFormDismissedListener)
    }

    companion object {
        @Volatile
        private var curInstance: AdsConsentManager? = null

        fun getCurInstance(context: Context) =
            curInstance
                ?: synchronized(this) {
                    curInstance ?: AdsConsentManager(context).also { curInstance = it }
                }
    }

    fun reset() {
        consentInformation.reset()
    }

    fun getConsentStatus(): Int {
        return consentInformation.consentStatus
    }
}