package mounts.com.driver.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mounts.com.driver.data.db.Dao.PayloadDao
import mounts.com.driver.data.db.Dao.UserDao
import mounts.com.driver.data.db.entities.FCMToken
import mounts.com.driver.data.db.entities.Payload
import mounts.com.driver.data.db.entities.RealtimeToken
import mounts.com.driver.data.db.entities.User


@Database(
        entities =[User::class,FCMToken::class,RealtimeToken::class,Payload::class],
        version = 1,
        exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getPayloadDao():PayloadDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "MyDatabase.db"
                ).build()
    }
}