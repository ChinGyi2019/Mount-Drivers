package mounts.com.driver.activity.ui.auth

interface AuthListener{
    fun onStarted()
    fun onSuccess(isSuccessful:Boolean?)
    fun onFailure(message: String?)
}