package mounts.com.driver.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mounts.com.driver.data.db.entities.CURRENT_USER_ID
import mounts.com.driver.data.db.entities.User
import mounts.com.driver.data.db.entities.FCMToken
import mounts.com.driver.data.db.entities.RealtimeToken


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user:User) :Long

    @Query("SELECT * FROM User WHERE uid = $CURRENT_USER_ID")
    fun getUser():LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFCM_Token(token:FCMToken): Long

    @Query("SELECT * FROM FCMToken WHERE tokenID = $CURRENT_USER_ID")
    fun getFCMToken():LiveData<FCMToken>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRealtimeToken(token:RealtimeToken): Long

    @Query("SELECT * FROM RealtimeToken WHERE tokenID = $CURRENT_USER_ID")
    fun getRealtimeToken():LiveData<RealtimeToken>

}