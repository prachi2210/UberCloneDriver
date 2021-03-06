package com.wizebrain.adebdriver.data.api.repository

import com.wizebrain.adebdriver.data.api.ApiHelper
import com.wizebrain.adebdriver.data.api.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AppRepository(var apiHelper: ApiHelper) {


    suspend fun getLogin(
        userEmail: RequestBody,
        userPassword: RequestBody,
        fieldType: RequestBody,
        OSType: RequestBody,
        deviceToken: RequestBody,
        latitude: RequestBody?,
        longitude: RequestBody?
    ) = apiHelper.getLogin(
        userEmail,
        userPassword,
        fieldType,
        OSType,
        deviceToken,
        latitude,
        longitude
    )


    suspend fun signUp(
        name: RequestBody?,
        email: RequestBody?,
        phoneNumber: RequestBody?,
        uniqueNumber: RequestBody?,
        drivingExperience: RequestBody?,
        password: RequestBody?,
        deviceType: RequestBody?,
        deviceToken: RequestBody?,
    ) = apiHelper.signUp(
        name,
        email,
        phoneNumber,
        uniqueNumber,
        drivingExperience,
        password,
        deviceType,
        deviceToken
    )


    suspend fun logout(
        userRef: RequestBody?,

        ) = apiHelper.logout(userRef)


    suspend fun startTrip(
        rideId: RequestBody?,
        type: RequestBody?,
        rideDistance: RequestBody?
    ) = apiHelper.startTrip(rideId,type,rideDistance)



    suspend fun addRating(
        rideId: RequestBody?,
        userRef: RequestBody?,
        driverRef: RequestBody?,
        ratting: RequestBody?,
        description: RequestBody?
    )= apiHelper.addRating(rideId,userRef,driverRef,ratting,description)



    suspend fun otpVerify(
        phoneNumber: RequestBody,
        otp: RequestBody
    ) = apiHelper.otpVerify(
        phoneNumber, otp
    )


    suspend fun forgotPassword(
        fieldType: RequestBody,
        phoneNumber: RequestBody

    ) = apiHelper.forgotPassword(fieldType, phoneNumber)

    suspend fun resendOtp(
        phoneNumber: RequestBody?
    ) = apiHelper.resendOtp(phoneNumber)


    suspend fun getBookingByDriver(
        driverRef: RequestBody?,
    )= apiHelper.getBookingByDriver(driverRef)



    suspend fun acceptRideByDriver(
        driverRef: RequestBody?,
        rideId: RequestBody?,
        type: RequestBody?,
    )= apiHelper.acceptRideByDriver(driverRef,rideId,type)


    suspend fun driverLocationUpdate(
        driverRef: RequestBody?,
        latitude: RequestBody?,
        longitude: RequestBody?,
    )= apiHelper.driverLocationUpdate(driverRef,latitude,longitude)

    suspend fun createNewPassword(
        phoneNumber: RequestBody?,
        password: RequestBody?
    ) = apiHelper.createNewPassword(phoneNumber, password)

    suspend fun driverStatus(
        driverRef: RequestBody?,

        ) = apiHelper.driverStatus(driverRef)



    suspend fun driverStats(
        driverRef: RequestBody?,

        ) = apiHelper.driverStats(driverRef)


    suspend fun uploadDrivingLicense(
        userRef: RequestBody?,
        dlNo: RequestBody?,
        carGearType: RequestBody?,
        carType:RequestBody?,
        frontSideUrl1: MultipartBody.Part?,
        frontSideUrl2: MultipartBody.Part?
    ) = apiHelper.uploadDrivingLicense(userRef, dlNo, carGearType,carType, frontSideUrl1, frontSideUrl2)


    suspend fun uploadPersonalId(
        userRef: RequestBody?,
        proofType: RequestBody?,
        proofNo: RequestBody?,
        dob: RequestBody?,
        image1: MultipartBody.Part?,
        image2: MultipartBody.Part?,
        image3: MultipartBody.Part?
    ) = apiHelper.uploadPersonalId(
        userRef,
        proofType,
        proofNo,
        dob,
        image1,
        image2,
        image3
    )


    suspend fun uploadHealthReport(
        userRef: RequestBody?,
        bloodGroup: RequestBody?,
        surgery: RequestBody?,

        healthReportFile: MultipartBody.Part?
    ) = apiHelper.uploadHealthReport(userRef, bloodGroup, surgery, healthReportFile)



    suspend fun onlineStatusUpdate(
        userRef: RequestBody?,
        type: RequestBody?,
    )= apiHelper.onlineStatusUpdate(userRef, type)



    /*

     suspend fun getSignUp(
             firstName: RequestBody?,
             lastName: RequestBody?,
             userName: RequestBody?,
             email: RequestBody?,
             sabbath: RequestBody?,
             stateDeath: RequestBody?,
             bilbleCount: RequestBody?,
             sdaProphet: RequestBody?,
             boyScouts: RequestBody?,
             diet: RequestBody?,
             ministryChurch: RequestBody?,
             churchName: RequestBody?,
             privacyPolicy: RequestBody?,
             password: RequestBody?,
             deviceType: RequestBody?,
             deviceToken: RequestBody?,
             latitude: RequestBody?,
             longitude: RequestBody?
     ) = apiHelper.getSignUp(
             firstName,
             lastName,
             userName,
             email,
             sabbath,
             stateDeath,
             bilbleCount,
             sdaProphet,
             boyScouts,
             diet,
             ministryChurch,
             churchName,
             privacyPolicy,
             password,
             deviceType,
             deviceToken,
             latitude,
             longitude
     )


     suspend fun forgotPassword(
             email: RequestBody

     ) = apiHelper.forgotPassword(
             email
     )


     suspend fun createNewPassword(
             email: RequestBody,
             password: RequestBody

     ) = apiHelper.createNewPassword(
             email, password
     )


     suspend fun updateProfilePic(
             userRef: RequestBody,
             profilePic: MultipartBody.Part?

     ) = apiHelper.updateProfilePic(userRef, profilePic!!)

     suspend fun addMoreInfo(
             userRef: RequestBody,
             height: RequestBody,
             hairColor: RequestBody,
             phoneNumber: RequestBody,
             country: RequestBody,
             gender: RequestBody,
             birthday: RequestBody

     ) = apiHelper.addMoreInfo(userRef, height, hairColor, phoneNumber, country, gender, birthday)


     suspend fun getNotification(
             userRef: RequestBody?,
     ) = apiHelper.getNotification(userRef)


     suspend fun getHome(
             userRef: RequestBody?,
             latitude: RequestBody?,
             longitude: RequestBody?,
             OSType: RequestBody,
             deviceToken: RequestBody

     ) = apiHelper.getHome(userRef, latitude, longitude, OSType, deviceToken)


     suspend fun likeProfile(
             fromRef: RequestBody?,
             toRef: RequestBody?,
             type: RequestBody?,
     ) = apiHelper.likeProfile(fromRef, toRef, type)


     suspend fun filterSearch(
             fromRef: RequestBody?,
             gender: RequestBody?,
             ageMin: RequestBody?,
             ageMax: RequestBody?,
             distance: RequestBody?,
             Latitude: RequestBody?,
             Longitude: RequestBody?,
             height: RequestBody?,
             bodyType: RequestBody?,
             language: RequestBody?,
             ethincity: RequestBody?,
             religion: RequestBody?,
             smoke: RequestBody?,
             drinks: RequestBody?,
             myIntrest: RequestBody?,
             education: RequestBody?,
             pets: RequestBody?,
     ) = apiHelper.filterSearch(
             fromRef,
             gender,
             ageMin,
             ageMax,
             distance,
             Latitude,
             Longitude,
             height,
             bodyType,
             language,
             ethincity,
             religion,
             smoke,
             drinks,
             myIntrest,
             education,
             pets
     )


     suspend fun editUserProfile(
             userRef: RequestBody?,
             profilePic: MultipartBody.Part?,
             photo: MultipartBody.Part?,
             video: MultipartBody.Part?,
             gender: RequestBody?,
             language: RequestBody?,
             height: RequestBody?,
             black: RequestBody?,

             ) = apiHelper.editUserProfile(
             userRef,
             profilePic,
             photo,
             video,
             gender,
             language,
             height,
             black
     )


     suspend fun updateUserProfile(
             userRef: RequestBody?,
             profilePic: MultipartBody.Part?,
             photo: MultipartBody.Part?,
             video: MultipartBody.Part?,
             gender: RequestBody?,
             Language: RequestBody?,
             height: RequestBody?,
             hairColor: RequestBody?,
             phoneNumber: RequestBody?,
             country: RequestBody?,
             birthday: RequestBody?,
             aboutme: RequestBody?,
             location: RequestBody?,
             smoke: RequestBody?,
             drink: RequestBody?,
             education: RequestBody?,
             bodytype: RequestBody?,
             ethnicity: RequestBody?,
             religion: RequestBody?,
             myInterest: RequestBody?,
             pet: RequestBody?,
             chruch: RequestBody?,
             diet: RequestBody?,
             ministry: RequestBody?,
             photosCount: RequestBody?,
             photo1: MultipartBody.Part?,
             photo2: MultipartBody.Part?,
             photo3: MultipartBody.Part?,
             photo4: MultipartBody.Part?,
             photo5: MultipartBody.Part?,
             photo6: MultipartBody.Part?,
     ) = apiHelper.updateUserProfile(
             userRef,
             profilePic,
             photo,
             video,
             gender,
             Language,
             height,
             hairColor,
             phoneNumber,
             country,
             birthday,
             aboutme,
             location,
             smoke,
             drink,
             education,
             bodytype,
             ethnicity,
             religion,
             myInterest,
             pet,
             chruch,
             diet,
             ministry,
             photosCount,
             photo1,
             photo2,
             photo3,
             photo4,
             photo5,
             photo6
     )


     suspend fun getMatchedList(
             userRef: RequestBody?,
     ) = apiHelper.getMatchedList(
             userRef
     )


     suspend fun unMatchUser(
             fromRef: RequestBody?,
             toRef: RequestBody?,
     ) = apiHelper.unMatchUser(
             fromRef, toRef
     )

     suspend fun sendMessageNotification(
             fromRef: RequestBody?,
             toRef: RequestBody?,
             message: RequestBody?,
     ) = apiHelper.sendMessageNotification(
             fromRef, toRef, message
     )


     suspend fun peopleProfile(
             value: RequestBody?,
             key: RequestBody?,
     ) = apiHelper.peopleProfile(
             value,
             key
     )


     suspend fun sendMatchedRequest(
             userRef: RequestBody?,
             matchedRef: RequestBody?,
     ) = apiHelper.sendMatchedRequest(
             userRef, matchedRef
     )


     suspend fun getFriendRequest(
             userRef: RequestBody?
     ) = apiHelper.getFriendRequest(
             userRef
     )


     suspend fun acceptMatchedRequest(
             userRef: RequestBody?,
             requestedRef: RequestBody?,
             isAccept: RequestBody?,
     ) = apiHelper.acceptMatchedRequest(
             userRef, requestedRef, isAccept
     )


     suspend fun getVisitors(
             userRef: RequestBody?,
     ) = apiHelper.getVisitors(
             userRef
     )

     suspend fun toLikeUserList(
             userRef: RequestBody?,
     ) = apiHelper.toLikeUserList(
             userRef
     )


     suspend fun fromLikeUsers(
             userRef: RequestBody?,
     ) = apiHelper.fromLikeUsers(
             userRef
     )


     suspend fun addVisitor(
             userRef: RequestBody?,
             visitRef: RequestBody?,
     ) = apiHelper.addVisitor(
             userRef, visitRef
     )

     suspend fun dislikeUserList(
             userRef: RequestBody?,
     ) = apiHelper.dislikeUserList(
             userRef
     )

     suspend fun addBlock(
             userRef: RequestBody?,
             blockUserRef: RequestBody?,
             action: RequestBody?
     ) = apiHelper.addBlock(
             userRef, blockUserRef, action
     )


     suspend fun reportUser(
             userRef: RequestBody?,
             reportUserRef: RequestBody?,
             action: RequestBody?,
     ) = apiHelper.reportUser(
             userRef, reportUserRef, action
     )

     suspend fun contactUs(
             userName: RequestBody?,
             userEmail: RequestBody?,
             message: RequestBody?,
     ) = apiHelper.contactUs(
             userName, userEmail, message
     )

     suspend fun blockUserList(
             userRef: RequestBody?,
     ) = apiHelper.blockUserList(
             userRef
     )


     suspend fun manageNotifications(
             userRef: RequestBody?,
             onOff: RequestBody?,
     ) = apiHelper.manageNotifications(
             userRef, onOff
     )


     suspend fun deletePhoto(
             userRef: RequestBody?,
             photoId: RequestBody?,
     ) = apiHelper.deletePhoto(
             userRef, photoId
     )

     suspend fun changePassword(
             userRef: RequestBody?,
             oldPass: RequestBody?,
             newPass: RequestBody?,
     ) = apiHelper.changePassword(
             userRef, oldPass, newPass
     )


     suspend fun deleteAccount(
             userRef: RequestBody?,
     ) = apiHelper.deleteAccount(
             userRef
     )

     suspend fun uploadChatImage(
             chatImg: MultipartBody.Part?
     ) = apiHelper.uploadChatImage(
             chatImg
     )


     suspend fun getAgoraToken(
             userId: RequestBody?,
     ) = apiHelper.getAgoraToken(
             userId)


     suspend fun checkCallStatus(
             sendToRef: RequestBody?,
             sendFromRef: RequestBody?,
     ) = apiHelper.checkCallStatus(
             sendToRef, sendFromRef)


     suspend fun connectDisconnectCall(
             sendToRef: RequestBody?,
             sendFromRef: RequestBody?,
             callAction: RequestBody?,
     ) = apiHelper.connectDisconnectCall(
             sendToRef, sendFromRef, callAction)
 */
}