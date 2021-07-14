package com.wizebrain.adebdriver.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.wizebrain.adebdriver.ui.auth.response.UserInfo


class PreferenceManager(context: Context) {
    private val PREFS_FILE_NAME = "ADEB User"
    private val USER_ID = "user id"
    private val USER_REF = "user ref"
    private val USER_Email = "user email"
    private val TIME_TYPE = "time type"
    private val USER_NAME = "user name"
    private val DEVICE_TOKEN = "device token"

    private val PHONENUMBER = "phoneNumber"
    private val NAME = "name"
    private val PHOTO = "photo"


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    private val mEditor: SharedPreferences.Editor = sharedPreferences.edit()


    fun clearPrefs() {
        mEditor.apply {
            clear()
            commit()
        }
    }

    fun saveName(name: String?) {
        mEditor.putString(NAME, name)
        mEditor.apply()
    }

    fun savePhoto(photo: String?) {

        mEditor.putString(PHOTO, photo)
        mEditor.apply()
    }

    fun savePhoneNumber(phoneNumber: String?) {
        mEditor.putString(PHONENUMBER, phoneNumber)
        mEditor.apply()
    }





    fun saveUserEmail(email: String) {
        mEditor.putString(USER_Email, email)
        mEditor.apply()
    }

    fun saveCabTime(type: String) {
        mEditor.putString(TIME_TYPE, type)
        mEditor.apply()
    }

    fun saveUserRef(user_id: String?) {

        mEditor.putString(USER_REF, user_id)
        mEditor.apply()
    }

    fun saveUserID(user_id: String?) {

        mEditor.putString(USER_ID, user_id)
        mEditor.apply()
    }


    fun saveDeviceToken(userToken: String?) {

        mEditor.putString(DEVICE_TOKEN, userToken)
        mEditor.apply()
    }


    fun getTimeType(): String {
        return sharedPreferences.getString(TIME_TYPE, "")!!
    }


    fun getUserEmail(): String {
        return sharedPreferences.getString(USER_Email, "")!!

    }


    fun getDeviceToken(): String {
        return sharedPreferences.getString(DEVICE_TOKEN, "")!!

    }

    fun getUserREf(): String {
        return sharedPreferences.getString(USER_REF, "")!!
    }

    fun getUserId(): String {
        return sharedPreferences.getString(USER_ID, "")!!
    }

    fun getPhoneNumber(): String {
        return sharedPreferences.getString(PHONENUMBER, "")!!
    }

    fun getName(): String {
        return sharedPreferences.getString(NAME, "")!!
    }

    fun getPhoto(): String {
        return sharedPreferences.getString(PHOTO, "")!!
    }

    fun saveUserDetails(selfDetails: UserInfo?) {
//        this.prefsEditor = appSharedPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(selfDetails)
        mEditor.putString("user_details", json)
        mEditor.commit()
    }

    fun getUserDetails(): UserInfo? {
        val user_data= sharedPreferences.getString("user_details","")
        return Gson().fromJson(user_data, UserInfo::class.java)
    }
}