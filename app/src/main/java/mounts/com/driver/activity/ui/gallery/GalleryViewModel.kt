package mounts.com.driver.activity.ui.gallery

import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mounts.com.driver.Util.Coroutines
import mounts.com.driver.data.db.entities.FCMToken
import mounts.com.driver.data.repositories.UserRepository

class GalleryViewModel (private val repository: UserRepository): ViewModel() {
        var fcmToken :LiveData<FCMToken>? =null
        var token:LiveData<String>? = null
        fun GalleryViewModel(){
             fcmToken = repository.getFCMToken()

                if((fcmToken?.value?.fcmToken)  != null)
                    token=fcmToken?.value?.fcmToken as MutableLiveData<String>?


        }
    fun getRealtimeToken() = repository.getRealtimeToken()
    fun getFCMToken() = repository.getFCMToken()

    fun login(token:String) {
        Coroutines.main(){
            repository.signWithCustomToken(token)
        }

    }


}




