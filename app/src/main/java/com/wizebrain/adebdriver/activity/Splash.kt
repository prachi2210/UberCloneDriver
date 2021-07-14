package com.wizebrain.adebdriver.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.wizebrain.adebdriver.R

class Splash : AppCompatActivity() {

    private val SPLASH_TIMER = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({
            val i = Intent(this@Splash, LoginActivity::class.java)
            startActivity(i)
            finishAffinity()
            finish()

        }, SPLASH_TIMER.toLong())
    }
  //  }
}