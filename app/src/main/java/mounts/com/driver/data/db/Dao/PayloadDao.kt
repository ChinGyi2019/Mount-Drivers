package mounts.com.driver.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mounts.com.driver.data.db.entities.CURRENT_USER_ID
import mounts.com.driver.data.db.entities.Payload
import mounts.com.driver.data.db.entities.User

@Dao
interface PayloadDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayload(payload: Payload) :Long

    @Query("SELECT * FROM Payload WHERE payloadID = $CURRENT_USER_ID")
    fun getPayload(): LiveData<Payload>
}