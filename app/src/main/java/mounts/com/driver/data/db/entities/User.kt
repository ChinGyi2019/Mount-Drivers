package mounts.com.driver.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import com.google.gson.annotations.SerializedName

const val CURRENT_USER_ID = 0

@Entity
data class User(
        @SerializedName("isSuccess")
        var isSuccess:Boolean? = null,
        @SerializedName("sessionId")
        var sessionId:String?=null,
        @SerializedName("isCompleteProfile")
        var isCompleteProfile:Boolean?=null

){
        @PrimaryKey(autoGenerate = false)
        var uid: Int = CURRENT_USER_ID
}



