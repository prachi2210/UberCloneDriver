package com.wizebrain.adebdriver.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wizebrain.adebdriver.R


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    fun init(){
      //  signup_btn.setOnClickListener { startActivity(Intent(this@SignUpActivity, LoginActivity::class.java)) }
    //    login_btn.setOnClickListener { startActivity(Intent(this@SignUpActivity, MainActivity::class.java)) }

    }
}