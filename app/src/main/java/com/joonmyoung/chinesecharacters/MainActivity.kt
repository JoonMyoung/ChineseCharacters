package com.joonmyoung.chinesecharacters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.joonmyoung.chinesecharacters.databinding.ActivityMainBinding
import com.joonmyoung.chinesecharacters.inside.*
import com.joonmyoung.chinesecharacters.setting.BillingModule
import com.joonmyoung.chinesecharacters.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var mInterstitialAd: InterstitialAd? = null

    private lateinit var bm: BillingModule

    private var isPurchasedRemoveAds = false

        set(value) {
            field = value
        }
    private var mSkuDetails = listOf<SkuDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        bm = BillingModule(this, lifecycleScope, object: BillingModule.Callback {
            override fun onBillingModulesIsReady() {
                bm.querySkuDetail(BillingClient.SkuType.INAPP, SettingActivity.Sku.REMOVE_ADS) { skuDetails ->
                    mSkuDetails = skuDetails
                }
                bm.checkPurchased(SettingActivity.Sku.REMOVE_ADS) {
                    isPurchasedRemoveAds = it
                    if (isPurchasedRemoveAds) {
                        binding.adView.visibility = View.GONE
                    }
                }
            }

            override fun onSuccess(purchase: Purchase) {
                when (purchase.sku) {
                    SettingActivity.Sku.REMOVE_ADS -> {
                        isPurchasedRemoveAds = true
                    }
                }
            }

            override fun onFailure(errorCode: Int) {

            }
        })


        MobileAds.initialize(this)
        binding.adView.loadAd(AdRequest.Builder().build())



        binding.member1.setOnClickListener {
            val intent = Intent(this, InsideActivity1::class.java)
            startActivity(intent)
        }
        binding.member2.setOnClickListener {
            val intent = Intent(this, InsideActivity2::class.java)
            startActivity(intent)
        }
        binding.member3.setOnClickListener {
            val intent = Intent(this, InsideActivity3::class.java)
            startActivity(intent)
        }
        binding.member4.setOnClickListener {
            val intent = Intent(this, InsideActivity4::class.java)
            startActivity(intent)
        }
        binding.member5.setOnClickListener {
            val intent = Intent(this, InsideActivity5::class.java)
            startActivity(intent)
        }
        binding.member6.setOnClickListener {
            val intent = Intent(this, InsideActivity6::class.java)
            startActivity(intent)
        }
        binding.member7.setOnClickListener {
            val intent = Intent(this, InsideActivity7::class.java)
            startActivity(intent)
        }
        binding.member8.setOnClickListener {
            val intent = Intent(this, InsideActivity8::class.java)
            startActivity(intent)
        }
        binding.member9.setOnClickListener {
            val intent = Intent(this, InsideActivity9::class.java)
            startActivity(intent)
        }
        binding.member10.setOnClickListener {
            val intent = Intent(this, InsideActivity10::class.java)
            startActivity(intent)
        }
        binding.member11.setOnClickListener {
            val intent = Intent(this, InsideActivity11::class.java)
            startActivity(intent)
        }
        binding.member12.setOnClickListener {
            val intent = Intent(this, InsideActivity12::class.java)
            startActivity(intent)
        }
        binding.member13.setOnClickListener {
            val intent = Intent(this, InsideActivity13::class.java)
            startActivity(intent)
        }
        binding.member14.setOnClickListener {
            val intent = Intent(this, InsideActivity14::class.java)
            startActivity(intent)
        }
        binding.member15.setOnClickListener {

            val intent = Intent(this, InsideActivity15::class.java)
            startActivity(intent)
        }
        binding.settingBtn.setOnClickListener {

            if (!isPurchasedRemoveAds) {
                var adRequest = AdRequest.Builder().build()
                InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d("TAG", adError?.message)
                        mInterstitialAd = null

                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d("TAG", "Ad was loaded.")
                        mInterstitialAd = interstitialAd

                        if (mInterstitialAd != null) {
                            mInterstitialAd?.show(this@MainActivity)

                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.")
                        }

                    }
                })
            }


            val intent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(intent)

        }






    }


}