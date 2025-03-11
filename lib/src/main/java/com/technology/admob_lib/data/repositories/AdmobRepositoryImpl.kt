package com.technology.admob_lib.data.repositories

import android.app.Activity
import com.google.android.gms.ads.AdView
import com.technology.admob_lib.data.datasources.AdmobDatasource
import com.technology.admob_lib.domain.models.AdmobType
import com.technology.admob_lib.domain.repositories.AdmobRepository
import javax.inject.Inject

class AdmobRepositoryImpl @Inject constructor(
    private val admobDataSource: AdmobDatasource
) : AdmobRepository {

    override suspend fun init(
        adUnitIds: Map<AdmobType, String>,
        isDebug: Boolean
    ) = admobDataSource.initAdmob(adUnitIds, isDebug)

    //--------------------------------- Banner Ad --------------------------------//
    override suspend fun loadBannerAd() = admobDataSource.loadBannerAd()

    override suspend fun showBannerAd(
        tag: String,
        bannerAdView: AdView
    ) = admobDataSource.showBannerAd(tag, bannerAdView)

    override fun disposeBannerAd(tag: String) = admobDataSource.disposeBannerAd(tag)

    override fun destroyViewBannerAd(bannerAdView: AdView) =
        admobDataSource.destroyViewBannerAd(bannerAdView)

    override fun destroyBannerAd(
        tag: String,
        bannerAdView: AdView
    ) = admobDataSource.destroyBannerAd(tag, bannerAdView)

    override fun fullDestroyBannerAd() = admobDataSource.fullDestroyBannerAd()

    //----------------------------------------------------------------------------//

    //--------------------------------- Native Ad --------------------------------//

    //----------------------------------------------------------------------------//

    //------------------------------ Interstitial Ad -----------------------------//
    override suspend fun loadInterstitialAd() = admobDataSource.loadInterstitialAd()

    override suspend fun showInterstitialAd(
        activity: Activity,
        timeout: Int
    ) = admobDataSource.showInterstitialAd(activity, timeout)

    override fun destroyInterstitialAd() = admobDataSource.destroyInterstitialAd()
    //----------------------------------------------------------------------------//

    //------------------------------- Rewarded Ad --------------------------------//
    override suspend fun loadRewardedAd() = admobDataSource.loadRewardedAd()

    override suspend fun showRewardedAd(activity: Activity) =
        admobDataSource.showRewardedAd(activity)

    override fun destroyRewardedAd() = admobDataSource.destroyRewardedAd()
    //----------------------------------------------------------------------------//

    //------------------------------- OpenApp Ad ---------------------------------//
    override suspend fun loadOpenAppAd() = admobDataSource.loadOpenAd()

    override suspend fun showOpenAppAd(activity: Activity) = admobDataSource.showOpenAd(activity)

    override fun destroyOpenAppAd() = admobDataSource.destroyOpenAd()
    //----------------------------------------------------------------------------//

    //--------------------------------- State ------------------------------------//
    override suspend fun isInterVisible() = admobDataSource.isInterstitialAdVisibility

    override suspend fun isOpenAdVisible() = admobDataSource.isOpenAdAdVisibility

    override suspend fun isAdmobInit() = admobDataSource.isAdmobInitSuccess

    override suspend fun loadOpenAdState() = admobDataSource.isOpenAdLoadSuccess

    override suspend fun loadBannerAdState() = admobDataSource.isBannerAdLoadSuccess

    override suspend fun loadNativeAdState() = admobDataSource.isNativeAdLoadSuccess

    override suspend fun loadInterstitialAdState() = admobDataSource.isInterstitialAdLoadSuccess

    override suspend fun loadRewardedAdState() = admobDataSource.isRewardedAdLoadSuccess

    override suspend fun rewardAdState() = admobDataSource.rewardedAdState
    //----------------------------------------------------------------------------//
}