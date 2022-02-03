package com.joonmyoung.chinesecharacters.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.joonmyoung.chinesecharacters.databinding.ActivitySettingBinding
import com.joonmyoung.chinesecharacters.login.IntroActivity


class SettingActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivitySettingBinding.inflate(layoutInflater)
    }
    private lateinit var bm: BillingModule

    private var mSkuDetails = listOf<SkuDetails>()
        set(value) {
            field = value
            setSkuDetailsView()
        }



    // 이전에 광고 제거 구매 여부
    private var isPurchasedRemoveAds = false
        set(value) {
            field = value
            updateRemoveAdsView()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (SettingData.check == false){
            binding.change.setText("한자모드")
        }else if (SettingData.check == true){
            binding.change.setText("훈음모드")
        }


        binding.change.setOnClickListener {

            if (SettingData.check == false){
                SettingData.check = true
                Toast.makeText(this, "변경 완료", Toast.LENGTH_SHORT).show()
                binding.change.setText("훈음모드")
                Log.d("TAG",SettingData.check.toString())
            }

            else if (SettingData.check == true){
                SettingData.check = false
                Toast.makeText(this, "변경 완료", Toast.LENGTH_SHORT).show()
                binding.change.setText("한자모드")
                Log.d("TAG",SettingData.check.toString())
            }


        }
        binding.logoutBtn.setOnClickListener {
            val auth = Firebase.auth
            auth.signOut()
            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        binding.backBtn.setOnClickListener {
            finish()
        }

        bm = BillingModule(this, lifecycleScope, object: BillingModule.Callback {
            override fun onBillingModulesIsReady() {
                bm.querySkuDetail(BillingClient.SkuType.INAPP, Sku.REMOVE_ADS) { skuDetails ->
                    mSkuDetails = skuDetails
                }
                bm.checkPurchased(Sku.REMOVE_ADS) {
                    isPurchasedRemoveAds = it
                }
            }

            override fun onSuccess(purchase: Purchase) {
                when (purchase.sku) {
                    Sku.REMOVE_ADS -> {
                        isPurchasedRemoveAds = true

                    }
                }
            }

            override fun onFailure(errorCode: Int) {
                when (errorCode) {
                    BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> {
                        Toast.makeText(this@SettingActivity, "이미 구입한 상품입니다.", Toast.LENGTH_LONG).show()
                    }
                    BillingClient.BillingResponseCode.USER_CANCELED -> {
                        Toast.makeText(this@SettingActivity, "구매를 취소하셨습니다.", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this@SettingActivity, "error: $errorCode", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        setClickListeners()


    }
    private fun setClickListeners() {
        with(binding) {
            // 광고 제거 구매 버튼 클릭
            addDelete.setOnClickListener {
                mSkuDetails.find { it.sku == Sku.REMOVE_ADS }?.let { skuDetail ->

                    bm.purchase(skuDetail)


                } ?: also {
                    Toast.makeText(this@SettingActivity, "상품을 찾을 수 없습니다.", Toast.LENGTH_LONG).show()
                }
            }

        }
    }


    private fun setSkuDetailsView() {
        val builder = StringBuilder()
        for (skuDetail in mSkuDetails) {
            builder.append("<${skuDetail.title}>\n")
            builder.append(skuDetail.price)
            builder.append("\n======================\n\n")
        }
        binding.tvSku.text = builder
    }

    private fun updateRemoveAdsView() {
        binding.tvRemoveAds.text = "광고 제거 여부: ${if (isPurchasedRemoveAds) "O" else "X"}"


    }

    override fun onResume() {
        super.onResume()
        bm.onResume(BillingClient.SkuType.INAPP)
    }

    object Sku {
        const val REMOVE_ADS = "remove_ads"

    }


}


