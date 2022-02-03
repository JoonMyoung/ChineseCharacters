package com.joonmyoung.chinesecharacters.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.joonmyoung.chinesecharacters.MainActivity
import com.joonmyoung.chinesecharacters.R

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth

        val joinBtn = findViewById<Button>(R.id.joinBtn)

        joinBtn.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.emailArea)
            val password = findViewById<TextInputEditText>(R.id.passwordArea)
            val passwordCheckBoxArea = findViewById<TextInputEditText>(R.id.passwordCheckArea)

            val emailCheck = email.text.toString()
            val passwordCheck = password.text.toString()
            val passwordCheckBox = passwordCheckBoxArea.text.toString()

            if (emailCheck.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()

            } else if (passwordCheck.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (passwordCheckBox.isEmpty()) {
                Toast.makeText(this, "비밀번호 체크를 확인해주세요", Toast.LENGTH_SHORT).show()
            } else if (password.text.toString() != passwordCheckBoxArea.text.toString()) {
                Toast.makeText(this, "비밀번호를 똑같이 입력해주세요", Toast.LENGTH_SHORT).show()
            }else{

                //회원가입
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success")

                            // 유저 고유아이디 가져오기
                            val user = auth.currentUser


                            // 메인으로 이동
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)




                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.exception)
                            Log.d("TAG",task.exception.toString())
                            if (task.exception.toString() == "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted."){
                                Toast.makeText(this, "이메일 형식의 아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                            }
                            if (task.exception.toString() == "com.google.firebase.auth.FirebaseAuthWeakPasswordException: The given password is invalid. [ Password should be at least 6 characters ]"){
                                Toast.makeText(this, "패스워드를 6글자 이상으로 입력해주세요", Toast.LENGTH_SHORT).show()
                            }
                            if (task.exception.toString() == "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account."){
                                Toast.makeText(this, "현재 이메일의 주소는 이미 사용 중 입니다", Toast.LENGTH_SHORT).show()
                            }


                        }
                    }
            }
        }

    }
}