package com.wizebrain.adebdriver.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wizebrain.adebdriver.MainActivity
import com.wizebrain.adebdriver.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

    }

    fun init(){
sign_up_txt.setOnClickListener { startActivity(Intent(this@LoginActivity, SignUpActivity::class.java)) }
 login_btn.setOnClickListener { startActivity(Intent(this@LoginActivity, MainActivity::class.java)) }

    }

}