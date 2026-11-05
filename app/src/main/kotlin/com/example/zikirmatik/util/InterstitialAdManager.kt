package com.example.zikirmatik.util

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object InterstitialAdConfig {
    /**
     * Google AdMob Geçiş Reklamı (Interstitial) Birimi Kimliği
     */
    const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3207706111232143/3333701697"

    // Reklamın kullanıcıyı rahatsız etmemesi için en az kaç turda bir ve kaç saniye arayla çıkacağı
    const val LAPS_BETWEEN_ADS = 3
    const val MIN_SECONDS_BETWEEN_ADS = 90L
}

class InterstitialAdManager(private val context: Context) {

    private var interstitialAd: InterstitialAd? = null
    private var isLoading = false
    private var lastAdShowTime = 0L
    private var completedLapsSinceLastAd = 0

    init {
        loadAd()
    }

    fun loadAd() {
        if (isLoading || interstitialAd != null) return
        isLoading = true

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            InterstitialAdConfig.INTERSTITIAL_AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoading = false
                    setupAdCallbacks()
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    isLoading = false
                }
            }
        )
    }

    private fun setupAdCallbacks() {
        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                lastAdShowTime = System.currentTimeMillis()
                completedLapsSinceLastAd = 0
                // Sonraki gösterim için arka planda yeni reklam yükle
                loadAd()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                interstitialAd = null
                loadAd()
            }

            override fun onAdShowedFullScreenContent() {
                // Reklam ekranda gösterildi
            }
        }
    }

    /**
     * Her tur (33/99 zikir) tamamlandığında çağrılır.
     * Kullanıcıyı bölmemek için sadece belirli aralıklarla gösterir.
     */
    fun onLapCompleted(activity: Activity?) {
        completedLapsSinceLastAd++
        val now = System.currentTimeMillis()
        val elapsedSeconds = (now - lastAdShowTime) / 1000

        if (completedLapsSinceLastAd >= InterstitialAdConfig.LAPS_BETWEEN_ADS &&
            elapsedSeconds >= InterstitialAdConfig.MIN_SECONDS_BETWEEN_ADS
        ) {
            if (activity != null && interstitialAd != null) {
                interstitialAd?.show(activity)
            } else {
                loadAd()
            }
        }
    }
}
