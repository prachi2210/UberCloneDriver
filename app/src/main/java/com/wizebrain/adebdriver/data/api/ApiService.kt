package com.wizebrain.adebdriver.data.api

import com.wizebrain.adebdriver.ui.auth.response.MessageResponse
import com.wizebrain.adebdriver.ui.auth.response.SignUpResponse
import com.wizebrain.adebdriver.ui.auth.response.LoginResponse
import com.wizebrain.adebdriver.ui.map.response.GetBookingResponse
import com.wizebrain.adebdriver.ui.map.response.RideAcceptResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @Multipart
    @POST("api/v1/driverRegister")
    suspend fun signUp(
        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("uniqueNumber") uniqueNumber: RequestBody?,
        @Part("drivingExperience") drivingExperience: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("deviceType") deviceType: RequestBody?,
        @Part("deviceToken") deviceToken: RequestBody?,
    ): Response<SignUpResponse>


    @Multipart
    @POST("api/v1/userLogin")
    suspend fun login(
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("FieldType") fieldType: RequestBody?,
        @Part("deviceType") deviceType: RequestBody?,
        @Part("deviceToken") deviceToken: RequestBody?,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?
    ): Response<LoginResponse>


    @Multipart
    @POST("api/v1/userOut")
    suspend fun logout(
        @Part("userRef") userRef: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/startTrip")
    suspend fun startTrip(
        @Part("rideId") rideId: RequestBody?,
        @Part("type") type: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/addRating")
    suspend fun addRating(
        @Part("rideId") rideId: RequestBody?,
        @Part("userRef") userRef: RequestBody?,
        @Part("toRef") driverRef: RequestBody?,
        @Part("rating") ratting: RequestBody?,
        @Part("description") description: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/otpVerify")
    suspend fun otpVerify(
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("otp") otp: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/ForgetPassword")
    suspend fun forgotPassword(
        @Part("FieldType") fieldType: RequestBody?,
        @Part("phoneNumber") phoneNumber: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/resendOtp")
    suspend fun resendOtp(
        @Part("phoneNumber") phoneNumber: RequestBody?
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/getBookingByDriver")
    suspend fun getBookingByDriver(
        @Part("driverRef") driverRef: RequestBody?
    ): Response<GetBookingResponse>


    @Multipart
    @POST("api/v1/acceptRideByDriver")
    suspend fun acceptRideByDriver(
        @Part("driverRef") driverRef: RequestBody?,
        @Part("rideId") rideId: RequestBody?,
        @Part("type") type: RequestBody?,
    ): Response<RideAcceptResponse>


    @Multipart
    @POST("api/v1/driverLocationUpdate")
    suspend fun driverLocationUpdate(
        @Part("driverRef") driverRef: RequestBody?,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?,
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/CreateNewPassword")
    suspend fun createNewPassword(
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("password") password: RequestBody?
    ): Response<MessageResponse>



    @Multipart
    @POST("api/v1/uploadDrivingLicense")
    suspend fun uploadDrivingLicense(
        @Part("userRef") userRef: RequestBody?,
        @Part("dlNo") dlNo: RequestBody?,
        @Part("carGearType") carGearType: RequestBody?,
        @Part("carType") carType: RequestBody?,
        @Part frontSideUrl1: MultipartBody.Part?,
        @Part frontSideUrl2: MultipartBody.Part?
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/uploadPersonalId")
    suspend fun uploadPersonalId(
        @Part("userRef") userRef: RequestBody?,
        @Part("proofType") proofType: RequestBody?,
        @Part("proofNo") proofNo: RequestBody?,
        @Part("dob") dob: RequestBody?,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?
    ): Response<MessageResponse>


    @Multipart
    @POST("api/v1/uploadHealthReport")
    suspend fun uploadHealthReport(
        @Part("userRef") userRef: RequestBody?,
        @Part("bloodGroup") bloodGroup: RequestBody?,
        @Part("surgery") surgery: RequestBody?,
        @Part healthReportFile: MultipartBody.Part?
    ): Response<MessageResponse>

    @Multipart
    @POST("api/v1/onlineStatusUpdate")
    suspend fun onlineStatusUpdate(
        @Part("userRef") userRef: RequestBody?,
        @Part("type") type: RequestBody?,
    ): Response<MessageResponse>


}






