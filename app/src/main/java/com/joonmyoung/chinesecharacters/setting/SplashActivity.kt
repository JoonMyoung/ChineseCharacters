package com.joonmyoung.chinesecharacters.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.joonmyoung.chinesecharacters.MainActivity
import com.joonmyoung.chinesecharacters.R
import com.joonmyoung.chinesecharacters.login.FirebaseAuthUtil
import com.joonmyoung.chinesecharacters.login.IntroActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


//        val uid = FirebaseAuthUtil.getUid()
//        Log.d("TAG",uid)
//
//        if (uid =="null"){
//            Handler().postDelayed({
//                val intent = Intent(this,IntroActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                startActivity(intent)
//                finish()
//            },3000)
//
//        }else{
//
//        }
        Handler().postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        },3000)

    }


}