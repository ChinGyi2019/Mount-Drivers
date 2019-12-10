package mounts.com.driver.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import com.google.gson.annotations.SerializedName

@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@Entity
data class FCMToken(
        @SerializedName("fcmToken")
        var fcmToken: String?= null

){
    @PrimaryKey(autoGenerate = false)
    var tokenID: Int = CURRENT_USER_ID
}

