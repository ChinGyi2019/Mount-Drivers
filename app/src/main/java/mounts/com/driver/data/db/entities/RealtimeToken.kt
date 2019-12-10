package mounts.com.driver.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import com.google.gson.annotations.SerializedName

@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@Entity
data class RealtimeToken (
        @SerializedName("token")
        var token:String? = null,
        @SerializedName("user_id")
        var user_id:Int? =null

){
    @PrimaryKey(autoGenerate = false)
    var tokenID: Int = CURRENT_USER_ID
}