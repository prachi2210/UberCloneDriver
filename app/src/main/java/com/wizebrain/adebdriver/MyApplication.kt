package com.wizebrain.adebdriver

import android.app.Application

class MyApplication : Application() {
    companion object {
        var active = false
    }


    override fun onCreate() {
        super.onCreate()
    }
}