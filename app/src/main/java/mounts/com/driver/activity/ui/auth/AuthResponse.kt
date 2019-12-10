package mounts.com.driver.activity.ui.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse(
        @SerializedName("isSuccess")
        var isSuccess:Boolean? = null,
        @SerializedName("sessionId")
        var sessionId:String?=null,
        @SerializedName("isCompleteProfile")
        var isCompleteProfile:Int?=null
)
