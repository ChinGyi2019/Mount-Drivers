package mounts.com.driver.data.repositories


import mounts.com.driver.data.db.AppDatabase
import mounts.com.driver.data.db.entities.RealtimeToken
import mounts.com.driver.data.db.entities.User
import mounts.com.driver.data.netwrok.FirebaseSource
import mounts.com.driver.data.netwrok.MyApi
import mounts.com.driver.data.netwrok.SafeApiRequest
import mounts.com.driver.data.netwrok.getFCM_Token


class UserRepository(
        private val api: MyApi,
        private val db:AppDatabase,
        private val fcmToken: getFCM_Token,
        private val firebaseSource: FirebaseSource
                     ) :SafeApiRequest(){
    //LOGIN USER
    suspend fun userLogin(phone:String,password:String,type: Int): User{
        return apiRequest{api.userLogin(phone,password,type)}
    }

    //SAVE USER
    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)
    fun getUser() = db.getUserDao().getUser()

    //FCM TOKEN
    suspend fun getFCM_TokenFromSever():String? = fcmToken.getToken()
    //suspend fun saveFCM_Token(token:FCMToken) = db.getUserDao().saveFCM_Token(token)

    //Attemt from GalleryFragment
    fun getFCMToken() = db.getUserDao().getFCMToken()

    //****___RealtimeToken___****
    //getFromeSever
    suspend fun getRealtimeTokenFromServer() = apiRequest { api.getRealtimeToken() }
    suspend fun saveRealtimeToken(token: RealtimeToken) = db.getUserDao().saveRealtimeToken(token)
    fun getRealtimeToken() = db.getUserDao().getRealtimeToken()
    suspend fun signWithCustomToken(token :String) = firebaseSource.signWithCustomToken(token)





}