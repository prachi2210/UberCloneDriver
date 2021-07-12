package com.wizebrain.adebdriver.ui.auth.response

data class SignUpResponse(
    val base_url: String?,
    val `data`: Data?,
    val msg: String?,
    val status: String?
)


data class Data(
    val Is_Active: String?,
    val created_at: String?,
    val deviceToken: String?,
    val deviceType: String?,
    val drivingExperience: Any?,
    val email: String?,
    val email_verified_at: Any?,
    val id: String?,
    val loginStatus: String?,
    val name: String?,
    val notifications: String?,
    val password: String?,
    val phoneNumber: String?,
    val profilePic: Any?,
    val remember_token: Any?,
    val role: String?,
    val uniqueNumber: Any?,
    val updated_at: String?,
    val userRef: String?,
    val validationCode: String?
)