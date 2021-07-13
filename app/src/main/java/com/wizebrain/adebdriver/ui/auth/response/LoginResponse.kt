package com.wizebrain.adebdriver.ui.auth.response

data class LoginResponse(
    val UserInfo: UserInfo?,
    val base_url: String?,
    val msg: String?,
    val status: String?,
    val varification: String?
)

data class UserInfo(
    val Is_Active: String?,
    val created_at: String?,
    val deviceToken: String?,
    val deviceType: String?,
    val drivingExperience: String?,
    val email: String?,
    val email_verified_at: Any?,
    val id: String?,
    val loginStatus: String?,
    val name: String?,
    val notifications: String?,
    val password: String?,
    val phoneNumber: String?,
    val profilePic: String?,
    val remember_token: Any?,
    val uniqueNumber: String?,
    val updated_at: String?,
    val userRef: String?,
    val validationCode: String?
)