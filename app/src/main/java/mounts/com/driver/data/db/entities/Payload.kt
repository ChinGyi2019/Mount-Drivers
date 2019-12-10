package mounts.com.driver.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings

@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@Entity
data class Payload(
        var name:String?=null,
        var receiver_name:String?= null,
        var receiver_phone_number:String? =null,
        var receiver_address:String? = null,
        var user_lat:Double? = null,
        var user_lng:Double? = null,
        var receiver_lat:Double? = null,
        var receiver_lng:Double? = null

){
@PrimaryKey(autoGenerate = false)
var payloadID: Int = CURRENT_USER_ID
}