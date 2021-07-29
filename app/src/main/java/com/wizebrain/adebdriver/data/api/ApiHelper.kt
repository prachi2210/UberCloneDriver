package com.wizebrain.adebdriver.data.api

import com.wizebrain.adebdriver.data.api.ApiService
import com.wizebrain.adebdriver.ui.auth.response.MessageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

class ApiHelper(private val apiService: ApiService) {

    suspend fun getLogin(
        userEmail: RequestBody,
        userPassword: RequestBody,
        fieldType: RequestBody,
        OSType: RequestBody,
        deviceToken: RequestBody,
        latitude: RequestBody?,
        longitude: RequestBody?
    ) = apiService.login(
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
        password: RequestBody?,
        uniqueNumber: RequestBody?,
        drivingExperience: RequestBody?,
        deviceType: RequestBody?,
        deviceToken: RequestBody?,
    ) = apiService.signUp(
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

        ) = apiService.logout(userRef)




    suspend fun startTrip(
      rideId: RequestBody?,
      type: RequestBody?,
      rideDistance: RequestBody?
    ) = apiService.startTrip(rideId,type,rideDistance)



    suspend fun addRating(
         rideId: RequestBody?,
       userRef: RequestBody?,
         driverRef: RequestBody?,
        ratting: RequestBody?,
     description: RequestBody?
    )= apiService.addRating(rideId,userRef,driverRef,ratting,description)


    suspend fun otpVerify(
        phoneNumber: RequestBody,
        otp: RequestBody

    ) = apiService.otpVerify(phoneNumber, otp)


    suspend fun forgotPassword(
        fieldType: RequestBody,
        phoneNumber: RequestBody

    ) = apiService.forgotPassword(fieldType, phoneNumber)


    suspend fun resendOtp(
        phoneNumber: RequestBody?
    ) = apiService.resendOtp(phoneNumber)




    suspend fun getBookingByDriver(
        driverRef: RequestBody?,
    )= apiService.getBookingByDriver(driverRef)



    suspend fun acceptRideByDriver(
        driverRef: RequestBody?,
        rideId: RequestBody?,
        type: RequestBody?,
    )= apiService.acceptRideByDriver(driverRef,rideId,type)


    suspend fun driverLocationUpdate(
     driverRef: RequestBody?,
     latitude: RequestBody?,
      longitude: RequestBody?,
    )= apiService.driverLocationUpdate(driverRef,latitude,longitude)

    suspend fun createNewPassword(
        phoneNumber: RequestBody?,
        password: RequestBody?
    ) = apiService.createNewPassword(phoneNumber, password)


    suspend fun uploadDrivingLicense(
        userRef: RequestBody?,
        dlNo: RequestBody?,
        carGearType: RequestBody?,
        carType: RequestBody?,
        frontSideUrl1: MultipartBody.Part?,
        frontSideUrl2: MultipartBody.Part?
    ) = apiService.uploadDrivingLicense(userRef, dlNo, carGearType,carType, frontSideUrl1, frontSideUrl2)


    suspend fun uploadPersonalId(
        userRef: RequestBody?,
        proofType: RequestBody?,
        proofNo: RequestBody?,
        dob: RequestBody?,
        image1: MultipartBody.Part?,
        image2: MultipartBody.Part?,
        image3: MultipartBody.Part?
    ) = apiService.uploadPersonalId(
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
    ) = apiService.uploadHealthReport(userRef, bloodGroup, surgery, healthReportFile)





    suspend fun onlineStatusUpdate(
userRef: RequestBody?,
  type: RequestBody?,
    )= apiService.onlineStatusUpdate(userRef, type)

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
      ) = apiService.signUp(
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

      ) = apiService.forgotPassword(email)




      suspend fun createNewPassword(
              email: RequestBody,
              password: RequestBody

      ) = apiService.createNewPassword(email, password)

      suspend fun updateProfilePic(
              userRef: RequestBody,
              profilePic: MultipartBody.Part

      ) = apiService.updateProfilePic(userRef, profilePic!!)

      suspend fun addMoreInfo(
              userRef: RequestBody?,
              height: RequestBody?,
              hairColor: RequestBody?,
              phoneNumber: RequestBody?,
              country: RequestBody?,
              gender: RequestBody?,
              birthday: RequestBody?
      ) = apiService.addMoreInfo(userRef, height, hairColor, phoneNumber, country, gender, birthday)


      suspend fun getNotification(
              userRef: RequestBody?
      ) = apiService.getNotification(userRef)


      suspend fun getHome(
              userRef: RequestBody?,
              latitude: RequestBody?,
              longitude: RequestBody?,
              OSType: RequestBody,
              deviceToken: RequestBody
      ) = apiService.getHome(userRef, latitude, longitude, OSType, deviceToken)


      suspend fun likeProfile(
              fromRef: RequestBody?,
              toRef: RequestBody?,
              type: RequestBody?,
      ) = apiService.likeProfile(fromRef, toRef, type)


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
      ) = apiService.filterSearch(
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

              ) = apiService.editUserProfile(
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
      ) = apiService.updateUserProfile(
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


      suspend fun peopleProfile(
              value: RequestBody?,
              key: RequestBody?,
      ) = apiService.peopleProfile(
              value,
              key
      )


      suspend fun getMatchedList(
              userRef: RequestBody?,
      ) = apiService.getMatchedList(
              userRef
      )


      suspend fun sendMessageNotification(
              fromRef: RequestBody?,
              toRef: RequestBody?,
              message: RequestBody?,
      ) = apiService.sendMessageNotification(
              fromRef, toRef, message
      )


      suspend fun unMatchUser(
              fromRef: RequestBody?,
              toRef: RequestBody?,
      ) = apiService.unMatchUser(
              fromRef, toRef
      )


      suspend fun sendMatchedRequest(
              userRef: RequestBody?,
              matchedRef: RequestBody?,
      ) = apiService.sendMatchedRequest(
              userRef, matchedRef
      )


      suspend fun getFriendRequest(
              userRef: RequestBody?,

              ) = apiService.getFriendRequest(
              userRef
      )


      suspend fun acceptMatchedRequest(
              userRef: RequestBody?,
              requestedRef: RequestBody?,
              isAccept: RequestBody?,
      ) = apiService.acceptMatchedRequest(
              userRef, requestedRef, isAccept
      )


      suspend fun getVisitors(
              userRef: RequestBody?,
      ) = apiService.getVisitors(
              userRef
      )


      suspend fun toLikeUserList(
              userRef: RequestBody?,
      ) = apiService.toLikeUserList(
              userRef
      )


      suspend fun fromLikeUsers(
              userRef: RequestBody?,
      ) = apiService.fromLikeUsers(
              userRef
      )


      suspend fun addVisitor(
              userRef: RequestBody?,
              visitRef: RequestBody?,
      ) = apiService.addVisitor(
              userRef, visitRef
      )


      suspend fun dislikeUserList(
              userRef: RequestBody?,
      ) = apiService.dislikeUserList(
              userRef
      )


      suspend fun addBlock(
              userRef: RequestBody?,
              blockUserRef: RequestBody?,
              action: RequestBody?
      ) = apiService.addBlock(
              userRef, blockUserRef, action
      )


      suspend fun reportUser(
              userRef: RequestBody?,
              reportUserRef: RequestBody?,
              action: RequestBody?,
      ) = apiService.reportUser(
              userRef, reportUserRef, action
      )


      suspend fun contactUs(
              userName: RequestBody?,
              userEmail: RequestBody?,
              message: RequestBody?,
      ) = apiService.contactUs(
              userName, userEmail, message
      )

      suspend fun blockUserList(
              userRef: RequestBody?,
      ) = apiService.blockUserList(
              userRef
      )


      suspend fun manageNotifications(
              userRef: RequestBody?,
              onOff: RequestBody?,
      ) = apiService.manageNotifications(
              userRef, onOff
      )


      suspend fun deletePhoto(
              userRef: RequestBody?,
              photoId: RequestBody?,
      ) = apiService.deletePhoto(
              userRef, photoId
      )


      suspend fun changePassword(
              userRef: RequestBody?,
              oldPass: RequestBody?,
              newPass: RequestBody?,
      ) = apiService.changePassword(
              userRef, oldPass, newPass
      )


      suspend fun deleteAccount(
              userRef: RequestBody?,
      ) = apiService.deleteAccount(
              userRef
      )


      suspend fun uploadChatImage(
              chatImg: MultipartBody.Part?
      ) = apiService.uploadChatImage(
              chatImg
      )


      suspend fun getAgoraToken(
              userId: RequestBody?,
      ) = apiService.getAgoraToken(
              userId)


      suspend fun checkCallStatus(
              sendToRef: RequestBody?,
              sendFromRef: RequestBody?,
      ) = apiService.checkCallStatus(
              sendToRef, sendFromRef)

      suspend fun connectDisconnectCall(
              sendToRef: RequestBody?,
              sendFromRef: RequestBody?,
              callAction: RequestBody?,
      ) = apiService.connectDisconnect(
              sendToRef, sendFromRef, callAction)
  */

}



