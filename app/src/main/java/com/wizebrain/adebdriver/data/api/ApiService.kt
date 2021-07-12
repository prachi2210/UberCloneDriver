package com.wizebrain.adebdriver.data.api
import com.wizebrain.adebdriver.ui.auth.response.MessageResponse
import com.wizebrain.adebdriver.ui.auth.response.SignUpResponse
import com.wizebrain.adebdriver.ui.auth.response.LoginResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @Multipart
    @POST("api/v1/userRegister")
    suspend fun signUp(
        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("phoneNumber") phoneNumber: RequestBody?,
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
    @POST("api/v1/CreateNewPassword")
    suspend fun createNewPassword(
        @Part("phoneNumber") phoneNumber: RequestBody?,
        @Part("password") password: RequestBody?
    ): Response<MessageResponse>


/*
    @Multipart
    @POST("api/v1/userOut")
    suspend fun logout(
            @Part("userRef") userRef: RequestBody?
            ): Response<UpdateStatusResponse>

    @Multipart
    @POST("api/v1/userRegister")
    suspend fun signUp(
            @Part("firstName") firstName: RequestBody?,
            @Part("lastName") lastName: RequestBody?,
            @Part("userName") userName: RequestBody?,
            @Part("email") email: RequestBody?,
            @Part("sabbath") sabbath: RequestBody?,
            @Part("stateDeath") stateDeath: RequestBody?,
            @Part("bilbleCount") bibleCount: RequestBody?,
            @Part("sdaProphet") sdaProphet: RequestBody?,
            @Part("boyScouts") boyScouts: RequestBody?,
            @Part("diet") diet: RequestBody?,
            @Part("ministryChurch") ministryChurch: RequestBody?,
            @Part("churchName") churchName: RequestBody?,
            @Part("privacyPolicy") privacyPolicy: RequestBody?,
            @Part("password") password: RequestBody?,
            @Part("deviceType") deviceType: RequestBody?,
            @Part("deviceToken") deviceToken: RequestBody?,
            @Part("Latitude") latitude: RequestBody?,
            @Part("Longitude") longitude: RequestBody?
    ): Response<UserSessionResponse>



    @Multipart
    @POST("api/v1/otpVerify")
    suspend fun otpVerify(
            @Part("email") email: RequestBody?,
            @Part("otp") otp: RequestBody?
    ): Response<OtpVerificationResponse>


    @Multipart
    @POST("api/v1/CreateNewPassword")
    suspend fun createNewPassword(
            @Part("email") email: RequestBody?,
            @Part("password") otp: RequestBody?
    ): Response<UpdateStatusResponse>


    @Multipart
    @POST("api/v1/updateProfilePic")
    suspend fun updateProfilePic(
            @Part("userRef") userRef: RequestBody?,
            @Part profilePic: MultipartBody.Part?
    ): Response<UpdateStatusResponse>




    @Multipart
    @POST("api/v1/addMoreInfo")
    suspend fun addMoreInfo(
            @Part("userRef") userRef: RequestBody?,
            @Part("height") height: RequestBody?,
            @Part("hairColor") hairColor: RequestBody?,
            @Part("phoneNumber") phoneNumber: RequestBody?,
            @Part("country") country: RequestBody?,
            @Part("gender") gender: RequestBody?,
            @Part("birthday") birthday: RequestBody?
    ): Response<UserSessionResponse>

    @Multipart
    @POST("api/v1/getHome")
    suspend fun getHome(
            @Part("userRef") userRef: RequestBody?,
            @Part("Latitude") latitude: RequestBody?,
            @Part("Longitude") longitude: RequestBody?,
            @Part("deviceType") deviceType: RequestBody?,
            @Part("deviceToken") deviceToken: RequestBody?
    ): Response<PeopleProfileResponse>


    @Multipart
    @POST("api/v1/likes")
    suspend fun likeProfile(
            @Part("fromRef") fromRef: RequestBody?,
            @Part("toRef") toRef: RequestBody?,
            @Part("type") type: RequestBody?,
    ): Response<PeopleProfileResponse>


    @Multipart
    @POST("api/v1/searchFilter")
    suspend fun filterSearch(
            @Part("userRef") userRef: RequestBody?,
            @Part("gender") gender: RequestBody?,
            @Part("ageMin") ageMin: RequestBody?,
            @Part("ageMax") ageMax: RequestBody?,
            @Part("distance") distance: RequestBody?,
            @Part("Latitude") Latitude: RequestBody?,
            @Part("Longitude") Longitude: RequestBody?,
            @Part("height") height: RequestBody?,
            @Part("bodyType") bodyType: RequestBody?,
            @Part("language") language: RequestBody?,
            @Part("ethincity") ethincity: RequestBody?,
            @Part("religion") religion: RequestBody?,
            @Part("smoke") smoke: RequestBody?,
            @Part("drinks") drinks: RequestBody?,
            @Part("myIntrest") myIntrest: RequestBody?,
            @Part("education") education: RequestBody?,
            @Part("pets") pets: RequestBody?,
    ): Response<PeopleProfileResponse>


    @Multipart
    @POST("api/v1/editUser")
    suspend fun editUserProfile(
            @Part("userRef") userRef: RequestBody?,
            @Part profilePic: MultipartBody.Part?,
            @Part photo: MultipartBody.Part?,
            @Part video: MultipartBody.Part?,
            @Part("gender") gender: RequestBody?,
            @Part("Language") Language: RequestBody?,
            @Part("height") height: RequestBody?,
            @Part("hairColor") black: RequestBody?,
    ): Response<UserSessionResponse>


    @Multipart
    @POST("api/v1/editUser")
    suspend fun updateUserProfile(
            @Part("userRef") userRef: RequestBody?,
            @Part profilePic: MultipartBody.Part?,
            @Part photo: MultipartBody.Part?,
            @Part video: MultipartBody.Part?,
            @Part("gender") gender: RequestBody?,
            @Part("Language") Language: RequestBody?,
            @Part("height") height: RequestBody?,
            @Part("hairColor") black: RequestBody?,
            @Part("phoneNumber") phoneNumber: RequestBody?,
            @Part("country") country: RequestBody?,
            @Part("birthday") birthday: RequestBody?,
            @Part("aboutme") aboutme: RequestBody?,
            @Part("location") location: RequestBody?,
            @Part("smoke") smoke: RequestBody?,
            @Part("drink") drink: RequestBody?,
            @Part("education") education: RequestBody?,
            @Part("bodytype") bodytype: RequestBody?,
            @Part("ethnicity") ethnicity: RequestBody?,
            @Part("religion") religion: RequestBody?,
            @Part("myInterest") myInterest: RequestBody?,
            @Part("pet") pet: RequestBody?,
            @Part("chruch") chruch: RequestBody?,
            @Part("diet") diet: RequestBody?,
            @Part("ministry") ministry: RequestBody?,
            @Part("photosCount") photosCount: RequestBody?,
            @Part photo1: MultipartBody.Part?,
            @Part photo2: MultipartBody.Part?,
            @Part photo3: MultipartBody.Part?,
            @Part photo4: MultipartBody.Part?,
            @Part photo5: MultipartBody.Part?,
            @Part photo6: MultipartBody.Part?,
    ): Response<UserSessionResponse>

    @Multipart
    @POST("api/v1/getProfile")
    suspend fun peopleProfile(
            @Part("Value") value: RequestBody?,
            @Part("Key") key: RequestBody?,
    ): Response<SingleUserProfileResponse>

    @Multipart
    @POST("api/v1/getMatchList")
    suspend fun getMatchedList(
            @Part("userRef") userRef: RequestBody?,
    ): Response<MatchedListResponse>


    @Multipart
    @POST("api/v1/unMatched")
    suspend fun unMatchUser(
            @Part("fromRef") fromRef: RequestBody?,
            @Part("toRef") toRef: RequestBody?,
    ): Response<UpdateStatusResponse>


    @Multipart
    @POST("api/v1/sendMatchRequest")
    suspend fun sendMatchedRequest(
            @Part("userRef") userRef: RequestBody?,
            @Part("matchedRef") matchedRef: RequestBody?,
    ): Response<SendMatchedRequest>

    @Multipart
    @POST("api/v1/getMatchRequest")
    suspend fun getFriendRequest(
            @Part("userRef") userRef: RequestBody?,
    ): Response<GetMatchRequestResponse>

    @Multipart
    @POST("api/v1/acceptMatchRequest")
    suspend fun acceptMatchedRequest(
            @Part("userRef") userRef: RequestBody?,
            @Part("requestedRef") requestedRef: RequestBody?,
            @Part("isAccept") isAccept: RequestBody?,
    ): Response<AcceptMatchedRequest>

    @Multipart
    @POST("api/v1/getNotification")
    suspend fun getNotification(
            @Part("userRef") userRef: RequestBody?,
    ): Response<NotificationResponse>

    @Multipart
    @POST("api/v1/getVisitor")
    suspend fun getVisitors(
            @Part("userRef") userRef: RequestBody?,
    ): Response<VisitorListResponse>

    @Multipart
    @POST("api/v1/getLikes")
    suspend fun toLikeUserList(
            @Part("userRef") userRef: RequestBody?,
    ): Response<GetLikesUserListResponse>


    @Multipart
    @POST("api/v1/getIliked")
    suspend fun fromLikeUsers(
            @Part("userRef") userRef: RequestBody?,
    ): Response<FromLikedUserResponse>


    @Multipart
    @POST("api/v1/getDisliked")
    suspend fun dislikeUserList(
            @Part("userRef") userRef: RequestBody?,
    ): Response<GetDislikeListResponse>

    @Multipart
    @POST("api/v1/addVisitor")
    suspend fun addVisitor(
            @Part("userRef") userRef: RequestBody?,
            @Part("visitRef") visitRef: RequestBody?,
    ): Response<UpdateStatusResponse>


    @Multipart
    @POST("api/v1/addBlockList")
    suspend fun addBlock(
            @Part("userRef") userRef: RequestBody?,
            @Part("blockUserRef") blockUserRef: RequestBody?,
            @Part("action") action: RequestBody?,
    ): Response<UpdateStatusResponse>


    @Multipart
    @POST("api/v1/reportUser")
    suspend fun reportUser(
            @Part("userRef") userRef: RequestBody?,
            @Part("reportUserRef") reportUserRef: RequestBody?,
            @Part("action") action: RequestBody?,
    ): Response<UpdateStatusResponse>

    @Multipart
    @POST("api/v1/contactUs")
    suspend fun contactUs(
            @Part("userName") userName: RequestBody?,
            @Part("userEmail") userEmail: RequestBody?,
            @Part("message") message: RequestBody?,
    ): Response<CallConnectDisconnectResponse>


    @Multipart
    @POST("api/v1/getBlockedList")
    suspend fun blockUserList(
            @Part("userRef") userRef: RequestBody?,
    ): Response<BlockUserResponse>


    @Multipart
    @POST("api/v1/notificationOnOff")
    suspend fun manageNotifications(
            @Part("userRef") userRef: RequestBody?,
            @Part("onOff") onOff: RequestBody?,
    ): Response<UpdateStatusResponse>

    @Multipart
    @POST("api/v1/deletePhoto")
    suspend fun deletePhoto(
            @Part("userRef") userRef: RequestBody?,
            @Part("photoId") photoId: RequestBody?,
    ): Response<UpdateStatusResponse>

    @Multipart
    @POST("api/v1/deleteAccount")
    suspend fun deleteAccount(
            @Part("userRef") userRef: RequestBody?,
    ): Response<UpdateStatusResponse>

    @Multipart
    @POST("api/v1/changePassword")
    suspend fun changePassword(
            @Part("userRef") userRef: RequestBody?,
            @Part("oldPass") oldPass: RequestBody?,
            @Part("newPass") newPass: RequestBody?,
    ): Response<UpdateStatusResponse>

    @Multipart
    @POST("api/v1/uploadChatImage")
    suspend fun uploadChatImage(
            @Part chatImg: MultipartBody.Part?
    ): Response<ChatUploadImageResponse>


    //local firebase notification
    @POST("fcm/send")
    fun sendNotification(
            @Body
            body: ChatSender?
    ): Call<ChatDataResponse>


    @Multipart
    @POST("api/v1/chatNotification")
    suspend fun sendMessageNotification(
            @Part("fromRef") fromRef: RequestBody?,
            @Part("toRef") toRef: RequestBody?,
            @Part("message") message: RequestBody?,
    ): Response<CallConnectDisconnectResponse>


    @Multipart
    @POST("api/v1/getAgoraToken")
    suspend fun getAgoraToken(
            @Part("userId") userId: RequestBody?,
    ): Response<AgoraTokenResponse>


    @Multipart
    @POST("api/v1/sendCallAlert")
    suspend fun checkCallStatus(
            @Part("sendToRef") sendToRef: RequestBody?,
            @Part("sendFromRef") sendFromRef: RequestBody?,
    ): Response<sendCallAlertResponse>


    @Multipart
    @POST("api/v1/connectDisconnectCall")
    suspend fun connectDisconnect(
            @Part("sendToRef") sendToRef: RequestBody?,
            @Part("sendFromRef") sendFromRef: RequestBody?,
            @Part("callAction") callAction: RequestBody?,
    ): Response<CallConnectDisconnectResponse>

*/

}






