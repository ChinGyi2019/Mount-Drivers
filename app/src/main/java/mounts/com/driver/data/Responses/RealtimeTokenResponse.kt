package mounts.com.driver.data.Responses

import com.google.gson.annotations.SerializedName


data class RealtimeTokenResponse(
           @SerializedName("user_id")
           val user_id:Boolean,
           @SerializedName("token")
           val token:String
)