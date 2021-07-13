package com.wizebrain.adebdriver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wizebrain.adebdriver.model.Dataclass
import kotlinx.android.synthetic.main.header_layout.*

class MainActivity : AppCompatActivity() {
    private var homeDataList: ArrayList<Dataclass> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init(){
        title_text.visibility=View.GONE
        endView.visibility=View.VISIBLE
        endView.setImageResource(R.drawable.ic_me)
    }
}