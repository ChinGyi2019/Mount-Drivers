package mounts.com.driver.activity.ui.auth

import android.provider.Settings.Global.getString
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import mounts.com.driver.R
import mounts.com.driver.Util.Coroutines
import mounts.com.driver.Util.NoInternetException
import mounts.com.driver.data.db.entities.FCMToken
import mounts.com.driver.data.netwrok.MyApi.Companion.token
import mounts.com.driver.data.repositories.UserRepository

const val type = 1
class AuthViewModel(private val repository: UserRepository) :ViewModel(){

    var phone:String? = null
    var password:String? = null

    var authListener :AuthListener? = null

    fun onLoginButtonClick(view : View){
        authListener?.onStarted()
        if(phone.isNullOrEmpty() || password.isNullOrEmpty()){
            Log.e("phone & password ","${phone+" "+password}")
            authListener?.onFailure("Invalid phone number or password")
            return
        }
       Coroutines.main {
           try{

          // val authResponse = repository.userLogin(phone!!,password!!,type)
               val authResponse = repository.userLogin(phone!!,password!!,type)
                repository.getFCM_TokenFromSever()

               authResponse.let {
                   authListener?.onSuccess(it.isSuccess)
                       repository.saveUser(it)
                       token = it.sessionId.toString()
                       Log.e("User",it.toString())
                   val realtimeTokenResponse = repository.getRealtimeTokenFromServer()
                   realtimeTokenResponse.let {
                       Log.e("RealtimeToken",it.token)
                       repository.saveRealtimeToken(it)


                   }


                       return@main

               }



           authListener?.onFailure(authResponse.isSuccess.toString())


       }catch (e: Exception){
               authListener?.onFailure(e.message!!)
               Log.e("ApiException",e.message!!)
           }catch (e: NoInternetException){
               authListener?.onFailure(e.message!!)
           }
       }

    }


    fun getLoginedUser() = repository.getUser()



}