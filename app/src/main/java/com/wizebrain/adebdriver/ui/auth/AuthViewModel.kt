package com.wizebrain.adebdriver.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.wizebrain.adebdriver.data.api.repository.AppRepository
import com.wizebrain.adebdriver.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AuthViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun login(
        phoneNumber: String,
        password: String,
        token: String,
        latitude: String,
        longitude: String,
    ) = liveData(Dispatchers.IO) {

        val userPhoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaType())
        val userPassword = password.toRequestBody("multipart/form-data".toMediaType())
        val fieldType = "phoneNumber".toRequestBody("multipart/form-data".toMediaType())
        val OSType = "Android".toRequestBody("multipart/form-data".toMediaType())
        val deviceToken = token.toRequestBody("multipart/form-data".toMediaType())
        val latitude =latitude.toRequestBody("multipart/form-data".toMediaType())
        val longitude =longitude.toRequestBody("multipart/form-data".toMediaType())

        emit(Resource.loading(data = null))
        try {

            emit(
                Resource.success(
                    data = appRepository.getLogin(
                        userPhoneNumber,
                        userPassword,
                        fieldType,
                        OSType,
                        deviceToken,
                        latitude,
                        longitude
                    )
                )
            )

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun signUp(
        name: String,
        email: String,
        phoneNumber: String,
        password: String,
        uniqueNumber: String,
        drivingExperience: String,
        token: String,
    ) = liveData(Dispatchers.IO) {
        val userName = name.toRequestBody("multipart/form-data".toMediaType())
        val userEmail = email.toRequestBody("multipart/form-data".toMediaType())
        val userPhoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaType())
        val userPassword = password.toRequestBody("multipart/form-data".toMediaType())
        val OSType = "Android".toRequestBody("multipart/form-data".toMediaType())
        val deviceToken = token.toRequestBody("multipart/form-data".toMediaType())
        val userUniqueNumber = uniqueNumber.toRequestBody("multipart/form-data".toMediaType())
        val drivingExperience = drivingExperience.toRequestBody("multipart/form-data".toMediaType())


        emit(Resource.loading(data = null))
        try {

            emit(
                Resource.success(
                    data = appRepository.signUp(
                        userName,
                        userEmail,
                        userPhoneNumber,
                        userUniqueNumber,
                        drivingExperience,
                        userPassword,
                        OSType,
                        deviceToken
                    )
                )
            )

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun forgotPassword(phoneNumber: String) = liveData(Dispatchers.IO)
    {

        val fieldType = "phoneNumber".toRequestBody("multipart/form-data".toMediaType())
        val userPhoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaType())


        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.forgotPassword(
                        fieldType, userPhoneNumber
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun resendOtp(phoneNumber: String) = liveData(Dispatchers.IO)
    {

        val userPhoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaType())


        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.resendOtp(
                        userPhoneNumber
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun otpVerify(phoneNumber: String, otp: String) = liveData(Dispatchers.IO)
    {
        val userPhoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaType())
        val userOtp = otp.toRequestBody("multipart/form-data".toMediaType())

        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.otpVerify(
                        userPhoneNumber, userOtp
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun createNewPassword(userRef: String, phoneNumber: String) = liveData(Dispatchers.IO)
    {
        val userPhoneNumber = userRef.toRequestBody("multipart/form-data".toMediaType())
        val userPassword = phoneNumber.toRequestBody("multipart/form-data".toMediaType())
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.createNewPassword(
                        userPhoneNumber, userPassword
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun logout(userRef: String) = liveData(Dispatchers.IO)
    {
        val userRef = userRef.toRequestBody("multipart/form-data".toMediaType())

        /**/

        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.logout(
                        userRef
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun uploadDrivingLicense(
        userRef: String,
        dlNo: String,
        carGearType: String,
        carType:String,
        frontSideUrl1: File?,
        frontSideUrl2: File?
    ) = liveData(Dispatchers.IO)
    {


        val userRef = userRef.toRequestBody("multipart/form-data".toMediaType())
        val dlNo = dlNo.toRequestBody("multipart/form-data".toMediaType())
        val carGearType = carGearType.toRequestBody("multipart/form-data".toMediaType())
        val carType = carType.toRequestBody("multipart/form-data".toMediaType())

        var userFrontSidePhoto: MultipartBody.Part? = null
        var userBackSidePhoto: MultipartBody.Part? = null

        if (frontSideUrl1 != null) {
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), frontSideUrl1)
            userFrontSidePhoto = MultipartBody.Part.createFormData("frontSideUrl1", frontSideUrl1.name, requestFile)
        }

        if (frontSideUrl2 != null) {
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), frontSideUrl2)
            userBackSidePhoto = MultipartBody.Part.createFormData("frontSideUrl2", frontSideUrl2.name, requestFile)
        }

        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.uploadDrivingLicense(
                        userRef,dlNo,carGearType,carType,userFrontSidePhoto,userBackSidePhoto
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun uploadHealthReport(
        userRef: String,
        bloodGroup: String,
        surgery: String,
        healthReportFile: File?
    ) = liveData(Dispatchers.IO)
    {
        val userRef = userRef.toRequestBody("multipart/form-data".toMediaType())
        val bloodGroup = bloodGroup.toRequestBody("multipart/form-data".toMediaType())
        val surgery = surgery.toRequestBody("multipart/form-data".toMediaType())


        var driverHealthReportFile: MultipartBody.Part? = null


        if (healthReportFile != null) {
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), healthReportFile)
            driverHealthReportFile = MultipartBody.Part.createFormData("healthReportFile", healthReportFile.name, requestFile)
        }


        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.uploadHealthReport(
                        userRef,bloodGroup,surgery,driverHealthReportFile
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun uploadPersonalId(
        userRef: String,
        proofType: String,
        proofNo: String,
        dob: String,
        image1: File?,
        image2: File?,
        image3: File?
    ) = liveData(Dispatchers.IO)
    {
        val userRef = userRef.toRequestBody("multipart/form-data".toMediaType())
        val proofType = proofType.toRequestBody("multipart/form-data".toMediaType())
        val proofNo = proofNo.toRequestBody("multipart/form-data".toMediaType())
        val dob = dob.toRequestBody("multipart/form-data".toMediaType())
        var driverPic: MultipartBody.Part? = null
        var driverFrontSide: MultipartBody.Part? = null
        var driverBackSide: MultipartBody.Part? = null

        if (image1 != null) {
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), image1)
            driverPic = MultipartBody.Part.createFormData("image1", image1.name, requestFile)
        }

        if (image2 != null) {
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), image2)
            driverFrontSide = MultipartBody.Part.createFormData("image2", image2.name, requestFile)
        }

        if (image3 != null) {
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), image3)
            driverBackSide = MultipartBody.Part.createFormData("image3", image3.name, requestFile)
        }


        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = appRepository.uploadPersonalId(
                        userRef,proofType,proofNo,dob,driverPic,driverFrontSide,driverBackSide
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
