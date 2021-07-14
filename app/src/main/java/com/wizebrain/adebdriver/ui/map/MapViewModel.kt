package com.wizebrain.adebdriver.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.wizebrain.adebdriver.data.api.repository.AppRepository
import com.wizebrain.adebdriver.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class MapViewModel(private val appRepository: AppRepository) : ViewModel() {


    fun getBookingByDriver(driverRef: String) = liveData(Dispatchers.IO)
    {
        val driverRef = driverRef.toRequestBody("multipart/form-data".toMediaType())
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.getBookingByDriver(
                        driverRef
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun acceptRideByDriver(
        driverRef: String, rideId: String,
        type: String,
    ) = liveData(Dispatchers.IO)
    {
        val driverRef = driverRef.toRequestBody("multipart/form-data".toMediaType())


        val rideId = rideId.toRequestBody("multipart/form-data".toMediaType())

        val type = type.toRequestBody("multipart/form-data".toMediaType())

        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.acceptRideByDriver(
                        driverRef, rideId, type
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}