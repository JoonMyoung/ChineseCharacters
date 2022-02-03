package com.joonmyoung.chinesecharacters.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.joonmyoung.chinesecharacters.MainActivity
import com.joonmyoung.chinesecharacters.R
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val loginBtn = findViewById<Button>(R.id.loginBtn)

        loginBtn.setOnClickListener{

            val email = findViewById<TextInputEditText>(R.id.emailArea)
            val password = findViewById<TextInputEditText>(R.id.passwordArea)


            try {

                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        } else {

                            Toast.makeText(this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()

                        }

                    }

            } catch (e:Exception){

                Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()

            }

        }

    }
}