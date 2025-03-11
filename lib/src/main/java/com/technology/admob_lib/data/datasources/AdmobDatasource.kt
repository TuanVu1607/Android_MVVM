package com.technology.admob_lib.data.datasources

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.technology.admob_lib.domain.models.AdmobType
import com.technology.admob_lib.domain.models.AdmobTypeLoad
import com.technology.admob_lib.domain.models.AdmobUnitIdTest
import com.technology.admob_lib.utils.AdmobInterAdUtils
import com.technology.admob_lib.utils.AdmobOpenAdUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

class AdmobDatasource @Inject constructor(private val context: Context) {

    companion object {
        private const val TAG = "AdmobDatasource"
    }

    private val mainCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var timeShowInter: Date? = null

    // AdUnitIds
    private var _openAppAdId = AdmobUnitIdTest.OPEN_AD
    private var _openAppAdIdLow = AdmobUnitIdTest.OPEN_AD
    private var _bannerAdId = AdmobUnitIdTest.BANNER_AD
    private var _bannerAdIdLow = AdmobUnitIdTest.BANNER_AD
    private var _nativeAdId = AdmobUnitIdTest.NATIVE_AD
    private var _nativeAdIdLow = AdmobUnitIdTest.NATIVE_AD
    private var _interstitialAdId = AdmobUnitIdTest.INTERSTITIAL_AD
    private var _interstitialAdIdLow = AdmobUnitIdTest.INTERSTITIAL_AD
    private var _rewardedAdId = AdmobUnitIdTest.REWARDED_AD
    private var _rewardedAdIdLow = AdmobUnitIdTest.REWARDED_AD

    // AdLoading
    private var isOpenAdLoading = false
    private var isBannerAdLoading = false
    private var isNativeAdLoading = false
    private var isInterstitialAdLoading = false
    private var isRewardedAdLoading = false

    // Ads
    private var addOpenAds: AppOpenAd? = null
    private var bannerAds = mutableListOf<AdView?>()
    private var cachedBannerAds = mutableMapOf<String, AdView?>()
    private var nativeAds = mutableListOf<NativeAd?>()
    private var cachedNativeAds = mutableMapOf<String, NativeAd?>()
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    // Ads Visibility
    val isInterstitialAdVisibility = MutableSharedFlow<Boolean>()
    val isOpenAdAdVisibility = MutableSharedFlow<Boolean>()

    // AdStateFlow
    val isAdmobInitSuccess = MutableSharedFlow<Boolean>()
    val isOpenAdLoadSuccess = MutableSharedFlow<Boolean>()
    val isBannerAdLoadSuccess = MutableSharedFlow<Boolean>()
    val isNativeAdLoadSuccess = MutableSharedFlow<Boolean>()
    val isInterstitialAdLoadSuccess = MutableSharedFlow<Boolean>()
    val isRewardedAdLoadSuccess = MutableSharedFlow<Boolean>()
    val rewardedAdState = MutableSharedFlow<Boolean>()

    fun initAdmob(adUnitIds: Map<AdmobType, String>, isDebug: Boolean) {
        MobileAds.initialize(context) {
            val testDeviceIds =
                listOf("9421A7997964B00BB62B1DA74F4C6D9C")//Id máy
            val configuration = RequestConfiguration.Builder()
                .setTestDeviceIds(testDeviceIds)
                .build()
            MobileAds.setRequestConfiguration(configuration)
            if (!isDebug) {
                _openAppAdId = adUnitIds[AdmobType.TYPE_OPEN] ?: ""
                _openAppAdIdLow = adUnitIds[AdmobType.TYPE_OPEN_LOW] ?: ""
                _bannerAdId = adUnitIds[AdmobType.TYPE_BANNER] ?: ""
                _bannerAdIdLow = adUnitIds[AdmobType.TYPE_BANNER_LOW] ?: ""
                _nativeAdId = adUnitIds[AdmobType.TYPE_NATIVE] ?: ""
                _nativeAdIdLow = adUnitIds[AdmobType.TYPE_NATIVE_LOW] ?: ""
                _interstitialAdId = adUnitIds[AdmobType.TYPE_INTERSTITIAL] ?: ""
                _interstitialAdIdLow = adUnitIds[AdmobType.TYPE_INTERSTITIAL_LOW] ?: ""
                _rewardedAdId = adUnitIds[AdmobType.TYPE_REWARDED] ?: ""
                _rewardedAdIdLow = adUnitIds[AdmobType.TYPE_REWARDED_LOW] ?: ""
            }
            mainCoroutineScope.launch {
                isAdmobInitSuccess.emit(true)
            }
        }
    }

    //--------------------------------- Banner Ad --------------------------------//
    private fun finishLoadBannerAd() = mainCoroutineScope.launch {
        isBannerAdLoadSuccess.emit(true)
        isBannerAdLoading = false
    }

    fun loadBannerAd(type: AdmobTypeLoad = AdmobTypeLoad.TYPE_HIGH) {
        val admobUnitId = if (type == AdmobTypeLoad.TYPE_HIGH) _bannerAdId else _bannerAdIdLow
        if (admobUnitId.isEmpty()) {
            Timber.tag(TAG).w("BannerAdId is empty")
            finishLoadBannerAd()
            return
        }
        if (isBannerAdLoading) {
            Timber.tag(TAG).w("Banner Ad is loading")
            return
        }
        isBannerAdLoading = true
        bannerAds.add(
            AdView(context).apply {
                adUnitId = admobUnitId
                setAdSize(AdSize.BANNER)
                adListener = object : AdListener() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Timber.apply {
                            tag(TAG)
                            e("onAdFailedToLoad: Code => ${adError.code} == Message => ${adError.message}")
                        }
                        if (type == AdmobTypeLoad.TYPE_LOW) {
                            finishLoadBannerAd()
                        } else {
                            isBannerAdLoading = false
                            loadBannerAd(AdmobTypeLoad.TYPE_LOW)
                        }
                    }

                    override fun onAdLoaded() {
                        Timber.tag(TAG).d("onAdLoaded")
                        finishLoadBannerAd()
                    }
                }
                loadAd(AdRequest.Builder().build())
            }
        )
    }

    fun showBannerAd(tag: String, bannerAdView: AdView) {
        if (isBannerAdLoading) {
            Timber.tag(TAG).w("Banner Ad is loading")
            return
        }
        if (bannerAds.isEmpty()) {
            Timber.tag(TAG).e("Banner Ad not ready")
            loadBannerAd()
            return
        }
        if (!cachedBannerAds.keys.contains(tag)) {
            cachedBannerAds[tag] = bannerAds.first()
            bannerAds.removeAt(0)
            loadBannerAd()
        }
        bannerAdView.removeAllViews()
        bannerAdView.addView(cachedBannerAds[tag])
    }

    fun disposeBannerAd(tag: String) {
        cachedBannerAds[tag]?.destroy()
        cachedBannerAds[tag] = null
        cachedBannerAds.remove(tag)
    }

    fun destroyViewBannerAd(bannerAdView: AdView) {
        bannerAdView.setOnClickListener(null)
        bannerAdView.removeAllViews()
        bannerAdView.destroy()
    }

    fun destroyBannerAd(tag: String, bannerAdView: AdView) {
        disposeBannerAd(tag)
        destroyViewBannerAd(bannerAdView)
    }

    fun fullDestroyBannerAd() {
        for (key in cachedBannerAds.keys) {
            disposeBannerAd(key)
        }
        cachedBannerAds.clear()
        for (index in bannerAds.indices) {
            bannerAds[index]?.destroy()
            bannerAds[index] = null
        }
        bannerAds.clear()
    }
    //----------------------------------------------------------------------------//

    //--------------------------------- Native Ad --------------------------------//

    //----------------------------------------------------------------------------//

    //------------------------------ Interstitial Ad -----------------------------//
    private fun finishLoadInterstitialAd() = mainCoroutineScope.launch {
        isInterstitialAdLoadSuccess.emit(true)
        isInterstitialAdLoading = false
    }

    private fun setInterVisible(isShow: Boolean) = mainCoroutineScope.launch {
        isInterstitialAdVisibility.emit(isShow)
    }

    fun loadInterstitialAd(type: AdmobTypeLoad = AdmobTypeLoad.TYPE_HIGH) {
        val admobUnitId =
            if (type == AdmobTypeLoad.TYPE_HIGH) _interstitialAdId else _interstitialAdIdLow
        if (admobUnitId.isEmpty()) {
            Timber.tag(TAG).w("InterstitialAdId is empty")
            finishLoadInterstitialAd()
            return
        }
        if (isInterstitialAdLoading) {
            Timber.tag(TAG).w("Interstitial Ad is loading")
            return
        }
        isInterstitialAdLoading = true
        InterstitialAd.load(
            context,
            admobUnitId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Timber.apply {
                        tag(TAG)
                        e("Interstitial Ad failed to load: Code => ${loadAdError.code} == Message => ${loadAdError.message}")
                    }
                    if (type == AdmobTypeLoad.TYPE_LOW) {
                        finishLoadInterstitialAd()
                    } else {
                        isInterstitialAdLoading = false
                        loadInterstitialAd(AdmobTypeLoad.TYPE_LOW)
                    }
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    Timber.tag(TAG).d("Interstitial Ad loaded successfully")
                    finishLoadInterstitialAd()
                    interstitialAd = ad
                }
            })
    }

    fun showInterstitialAd(activity: Activity, timeout: Int) {
        if (isInterstitialAdLoading) {
            Timber.tag(TAG).w("Interstitial Ad is loading")
            return
        }
        if (interstitialAd == null) {
            Timber.tag(TAG).e("Interstitial Ad not ready")
            loadInterstitialAd()
            return
        }
        if (timeShowInter != null && AdmobInterAdUtils.getElapsedSeconds(timeShowInter!!) <= timeout) {
            Timber.tag(TAG).w("Interstitial Ad is on cooldown")
            return
        }
        mainCoroutineScope.launch {
            //Show Loading trong 2s rồi mới show ads
            interstitialAd?.apply {
                fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Timber.tag(TAG).d("Interstitial Ad dismissed")
                        setInterVisible(false)
                        timeShowInter = Date()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Timber.apply {
                            tag(TAG)
                            e("Interstitial Ad failed to show: Code => ${adError.code} == Message => ${adError.message}")
                        }
                        setInterVisible(true)
                        setInterVisible(false)
                    }

                    override fun onAdShowedFullScreenContent() {
                        Timber.tag(TAG).d("Interstitial Ad showed")
                        setInterVisible(true)
                        loadInterstitialAd()
                    }
                }
                show(activity)
            }
        }
    }

    fun destroyInterstitialAd() {
        interstitialAd = null
        setInterVisible(false)
    }
    //----------------------------------------------------------------------------//

    //------------------------------- Rewarded Ad --------------------------------//
    private fun finishLoadRewardedAd() = mainCoroutineScope.launch {
        isRewardedAdLoadSuccess.emit(true)
        isRewardedAdLoading = false
    }

    fun loadRewardedAd(type: AdmobTypeLoad = AdmobTypeLoad.TYPE_HIGH) {
        val admobUnitId = if (type == AdmobTypeLoad.TYPE_HIGH) _rewardedAdId else _rewardedAdIdLow
        if (admobUnitId.isEmpty()) {
            Timber.tag(TAG).w("RewardedAdId is empty")
            finishLoadRewardedAd()
            return
        }
        if (isRewardedAdLoading) {
            Timber.tag(TAG).w("Rewarded Ad is loading")
            return
        }
        isRewardedAdLoading = true
        RewardedAd.load(
            context,
            admobUnitId,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Timber.apply {
                        tag(TAG)
                        e("Rewarded Ad failed to load: Code => ${loadAdError.code} == Message => ${loadAdError.message}")
                    }
                    if (type == AdmobTypeLoad.TYPE_LOW) {
                        finishLoadRewardedAd()
                    } else {
                        isRewardedAdLoading = false
                        loadRewardedAd(AdmobTypeLoad.TYPE_LOW)
                    }
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Timber.tag(TAG).d("Rewarded Ad loaded successfully")
                    finishLoadRewardedAd()
                    rewardedAd = ad
                }
            })
    }

    fun showRewardedAd(activity: Activity) {
        var isEarned = false
        if (isRewardedAdLoading) {
            Timber.tag(TAG).w("Rewarded Ad is loading")
            return
        }
        if (rewardedAd == null) {
            Timber.tag(TAG).e("Rewarded Ad not ready")
            loadRewardedAd()
            return
        }
        rewardedAd?.apply {
            fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Timber.tag(TAG).d("Rewarded Ad dismissed")
                    mainCoroutineScope.launch {
                        rewardedAdState.emit(isEarned)
                    }
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Timber.apply {
                        tag(TAG)
                        e("Rewarded Ad failed to show: Code => ${adError.code} == Message => ${adError.message}")
                    }
                }

                override fun onAdShowedFullScreenContent() {
                    Timber.tag(TAG).d("Rewarded Ad showed")
                    loadRewardedAd()
                }
            }
            show(activity) { rewardItem ->
                isEarned = rewardItem.amount > 0
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                Timber.apply {
                    tag(TAG)
                    d("User earned the reward: $rewardAmount gems, belong to type: $rewardType")
                }
            }
        }
    }

    fun destroyRewardedAd() {
        rewardedAd = null
    }
    //----------------------------------------------------------------------------//

    //------------------------------- OpenApp Ad ---------------------------------//
    private fun finishLoadOpenAd() = mainCoroutineScope.launch {
        isOpenAdLoadSuccess.emit(true)
        isOpenAdLoading = false
    }

    private fun updateOpenAdVisibility(isShow: Boolean) = mainCoroutineScope.launch {
        isOpenAdAdVisibility.emit(isShow)
    }

    fun loadOpenAd(type: AdmobTypeLoad = AdmobTypeLoad.TYPE_HIGH) {
        val admobUnitId = if (type == AdmobTypeLoad.TYPE_HIGH) _openAppAdId else _openAppAdIdLow
        if (admobUnitId.isEmpty()) {
            finishLoadOpenAd()
            Timber.tag(TAG).w("OpenAdId is empty")
            return
        }
        if (isOpenAdLoading) {
            Timber.tag(TAG).w("Open Ad is loading")
            return
        }
        isOpenAdLoading = true
        AppOpenAd.load(
            context,
            admobUnitId,
            AdRequest.Builder().build(),
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Timber.tag(TAG).e("Open Ad failed to load: ${loadAdError.message}")
                    if (type == AdmobTypeLoad.TYPE_LOW) {
                        finishLoadOpenAd()
                    } else {
                        isOpenAdLoading = false
                        loadOpenAd(AdmobTypeLoad.TYPE_LOW)
                    }
                }

                override fun onAdLoaded(ad: AppOpenAd) {
                    Timber.tag(TAG).d("Open Ad loaded successfully")
                    AdmobOpenAdUtils.updateLoadTime()
                    finishLoadOpenAd()
                    addOpenAds = ad
                }
            })
    }

    fun showOpenAd(activity: Activity) {
        if (timeShowInter != null && AdmobInterAdUtils.getElapsedSeconds(timeShowInter!!) <= 5) {
            Timber.tag(TAG).w("Another Ad is already showing")
            return
        }
        if (isOpenAdLoading) {
            Timber.tag(TAG).w("Open Ad is loading")
            return
        }
        if (addOpenAds == null || !AdmobOpenAdUtils.isNotExpired()) {
            Timber.tag(TAG).e("Open Ad not ready")
            loadOpenAd()
            return
        }
        addOpenAds?.apply {
            fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Timber.tag(TAG).d("Open Ad dismissed")
                    updateOpenAdVisibility(false)
                    loadOpenAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Timber.tag(TAG).e("Open Ad failed to show: ${adError.message}")
                }

                override fun onAdShowedFullScreenContent() {
                    Timber.tag(TAG).d("Open Ad showed")
                    updateOpenAdVisibility(true)
                }
            }
            show(activity)
        }
    }

    fun destroyOpenAd() {
        addOpenAds = null
    }
    //----------------------------------------------------------------------------//
}
