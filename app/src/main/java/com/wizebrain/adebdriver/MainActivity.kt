package com.wizebrain.adebdriver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.annotation.GlideExtension
import com.wizebrain.adebdriver.databinding.ActivityMainBinding
import com.wizebrain.adebdriver.model.RowData

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private var homeDataList: ArrayList<RowData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
        init()
    }

    fun init(){
        binding.header.titleText.visibility= View.GONE
        binding.header.endView.visibility=View.VISIBLE
        binding.header.endView.setImageResource(R.drawable.ic_me)

    }
}