package mounts.com.driver.data.netwrok

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class FirebaseSource(context : Context) {

        val context = context.applicationContext


     val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    suspend fun signWithCustomToken(token: String) {
        firebaseAuth.signInWithCustomToken(token).addOnCompleteListener(OnCompleteListener {
            task ->
            if(task.isSuccessful){
                Log.e("TAG", "signInWithCustomToken:success")
                val user = firebaseAuth.currentUser
                Log.e("Current User", user.toString())

            }else {
                // If sign in fails, display a message to the user.
                Log.e("TAG", "signInWithCustomToken:failure", task.exception)
                Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                //updateUI(null)
            }

        })



    }
}
