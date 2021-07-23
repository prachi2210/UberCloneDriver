package com.wizebrain.adebdriver.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


class PreferenceManager(context: Context) {
    private val PREFS_FILE_NAME = "ADEB Driver"
    private val USER_ID = "user id"
    private val USER_REF = "user ref"
    private val USER_Email = "user email"
    private val TIME_TYPE = "time type"
    private val USER_NAME = "user name"
    private val DEVICE_TOKEN = "device token"
    private val LATITUDE = "latitude"
    private val LONGITUDE = "longitude"
    private val PHONENUMBER = "phoneNumber"
    private val NAME = "name"
    private val PHOTO = "photo"
    private val PERSONAL_ID = "personal_id"
    private val ONLINE_STATUS="online_status"
    private val DRIVER_LICENSE = "driver_license"
    private val HEALTH = "health"
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


    fun saveCurrentLatitude(latitude: String) {
        mEditor.putString(LATITUDE, latitude)
        mEditor.apply()
    }


    fun saveCurrentLongitude(longitude: String) {
        mEditor.putString(LONGITUDE, longitude)
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





    fun saveDriveLicense(documentType:String?) {

        mEditor.putString(DRIVER_LICENSE, documentType)
        mEditor.apply()
    }


    fun getDriveLicense():String{
        return sharedPreferences.getString(DRIVER_LICENSE, "")!!
    }


    fun saveHealthReport(documentType:String?) {

        mEditor.putString(HEALTH, documentType)
        mEditor.apply()
    }


    fun getHealthReport():String{
        return sharedPreferences.getString(HEALTH, "")!!
    }



    fun savePersonalID(documentType:String?) {

        mEditor.putString(PERSONAL_ID, documentType)
        mEditor.apply()
    }


    fun saveOnlineStatus(status: String) {
        mEditor.putString(ONLINE_STATUS, status)
        mEditor.apply()
    }


    fun getOnlineStatus():String{
        return sharedPreferences.getString(ONLINE_STATUS, "")!!
    }

    fun getPersonalID():String{
        return sharedPreferences.getString(PERSONAL_ID, "")!!
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
    fun getLatitude(): String {
        return sharedPreferences.getString(LATITUDE, "")!!
    }

    fun getLongitude(): String {
        return sharedPreferences.getString(LONGITUDE, "")!!
    }



}