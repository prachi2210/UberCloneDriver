package com.wizebrain.adebdriver.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.BaseActivity
import com.wizebrain.adebdriver.databinding.ActivityDriverProfileBinding
import com.wizebrain.adebdriver.databinding.ActivityLoginBinding
import com.wizebrain.adebdriver.ui.auth.LoginActivity

class DriverProfileActivity : BaseActivity(), ProfileAdapter.ProfileViewListener {
    private lateinit var binding: ActivityDriverProfileBinding
    private val profileAdapter: ProfileAdapter by lazy {
        ProfileAdapter(this,this)
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DriverProfileActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvProfile.adapter=profileAdapter

        //bloodGroupList = resources.getStringArray(R.array.blood_group)
    }

    override fun profileActivityCallback(position: Int) {
        //profileActiivtyCallback
    }
}