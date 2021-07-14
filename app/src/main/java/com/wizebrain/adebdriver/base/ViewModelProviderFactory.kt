package com.wizebrain.adebdriver.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.wizebrain.adebdriver.data.api.repository.AppRepository
import com.wizebrain.adebdriver.ui.auth.AuthViewModel
import com.wizebrain.adebdriver.ui.map.MapViewModel

class ViewModelProviderFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(AppRepository(apiHelper)) as T
        }

        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(AppRepository(apiHelper)) as T
        }
/*
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(AppRepository(apiHelper)) as T
        }

        if (modelClass.isAssignableFrom(NavigationViewModel::class.java)) {
            return NavigationViewModel(AppRepository(apiHelper)) as T
        }*/
        throw IllegalArgumentException("Unknown class name")
    }
}






