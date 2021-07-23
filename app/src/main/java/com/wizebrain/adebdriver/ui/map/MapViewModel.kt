package com.wizebrain.adebdriver.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.wizebrain.adebdriver.data.api.repository.AppRepository
import com.wizebrain.adebdriver.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class MapViewModel(private val appRepository: AppRepository) : ViewModel() {




    fun onlineStatusUpdate(userRef: String, type: String) = liveData(Dispatchers.IO)
    {
        val userRef = userRef.toRequestBody("multipart/form-data".toMediaType())
        val type = type.toRequestBody("multipart/form-data".toMediaType())
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.onlineStatusUpdate(
                        userRef, type
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }



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


    /*    suspend fun driverLocationUpdate(
        driverRef: RequestBody?,
        latitude: RequestBody?,
        longitude: RequestBody?,
    )= apiHelper.driverLocationUpdate(driverRef,latitude,longitude)*/


    fun driverLocationUpdate(
        driverRef: String, latitude: String,
        longitude: String,
    ) = liveData(Dispatchers.IO)
    {
        val driverRef = driverRef.toRequestBody("multipart/form-data".toMediaType())
        val latitude = latitude.toRequestBody("multipart/form-data".toMediaType())
        val longitude = longitude.toRequestBody("multipart/form-data".toMediaType())


        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.driverLocationUpdate(
                        driverRef, latitude, longitude
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun startTrip(
        rideId: String,
        type: String
    ) = liveData(Dispatchers.IO)
    {
        val rideId = rideId.toRequestBody("multipart/form-data".toMediaType())
        val type = type.toRequestBody("multipart/form-data".toMediaType())
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.startTrip(
                        rideId, type
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun addRating(
        rideId: String,
        userRef: String,
        driverRef: String,
        ratting: String,
        description: String
    ) = liveData(Dispatchers.IO)
    {
        val rideId = rideId.toRequestBody("multipart/form-data".toMediaType())
        val userRef = userRef.toRequestBody("multipart/form-data".toMediaType())
        val driverRef = driverRef.toRequestBody("multipart/form-data".toMediaType())
        val ratting = ratting.toRequestBody("multipart/form-data".toMediaType())
        val description = description.toRequestBody("multipart/form-data".toMediaType())

        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.addRating(
                        rideId, userRef, driverRef, ratting, description
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}