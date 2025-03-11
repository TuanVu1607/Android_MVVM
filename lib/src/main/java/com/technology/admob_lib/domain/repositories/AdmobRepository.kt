package com.technology.admob_lib.domain.repositories

import android.app.Activity
import com.google.android.gms.ads.AdView
import com.technology.admob_lib.domain.models.AdmobType
import kotlinx.coroutines.flow.MutableSharedFlow

interface AdmobRepository {
    suspend fun init(adUnitIds: Map<AdmobType, String>, isDebug: Boolean)

    //--------------------------------- Banner Ad --------------------------------//
    suspend fun loadBannerAd()

    suspend fun showBannerAd(tag: String, bannerAdView: AdView)

    fun disposeBannerAd(tag: String)

    fun destroyViewBannerAd(bannerAdView: AdView)

    fun destroyBannerAd(tag: String, bannerAdView: AdView)

    fun fullDestroyBannerAd()
    //----------------------------------------------------------------------------//

    //--------------------------------- Native Ad --------------------------------//

    //----------------------------------------------------------------------------//

    //------------------------------ Interstitial Ad -----------------------------//
    suspend fun loadInterstitialAd()

    suspend fun showInterstitialAd(activity: Activity, timeout: Int)

    fun destroyInterstitialAd()
    //----------------------------------------------------------------------------//

    //------------------------------- Rewarded Ad --------------------------------//
    suspend fun loadRewardedAd()

    suspend fun showRewardedAd(activity: Activity)

    fun destroyRewardedAd()
    //----------------------------------------------------------------------------//

    //------------------------------- OpenApp Ad ---------------------------------//
    suspend fun loadOpenAppAd()

    suspend fun showOpenAppAd(activity: Activity)

    fun destroyOpenAppAd()
    //----------------------------------------------------------------------------//

    //--------------------------------- State ------------------------------------//
    suspend fun isInterVisible(): MutableSharedFlow<Boolean>

    suspend fun isOpenAdVisible(): MutableSharedFlow<Boolean>

    suspend fun isAdmobInit(): MutableSharedFlow<Boolean>

    suspend fun loadOpenAdState(): MutableSharedFlow<Boolean>

    suspend fun loadBannerAdState(): MutableSharedFlow<Boolean>

    suspend fun loadNativeAdState(): MutableSharedFlow<Boolean>

    suspend fun loadInterstitialAdState(): MutableSharedFlow<Boolean>

    suspend fun loadRewardedAdState(): MutableSharedFlow<Boolean>

    suspend fun rewardAdState(): MutableSharedFlow<Boolean>
    //----------------------------------------------------------------------------//
}