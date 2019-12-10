package mounts.com.driver.data.netwrok

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import mounts.com.driver.Util.Coroutines
import mounts.com.driver.data.db.AppDatabase
import mounts.com.driver.data.db.entities.FCMToken


class getFCM_Token(private val db:AppDatabase){



        suspend fun getToken():String?{
            var token :String? = null
            FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) { //To do//
                            return@OnCompleteListener
                        }
                        // Get the Instance ID token//
                          token = task.result!!.token
                        Coroutines.main {
                            db.getUserDao().saveFCM_Token(FCMToken(token))
                        }

                        val msg: String = "FCM Token :" + token //getString("Token :", token)
                        Log.e("TAG", msg)


                    })
           return token


}


}