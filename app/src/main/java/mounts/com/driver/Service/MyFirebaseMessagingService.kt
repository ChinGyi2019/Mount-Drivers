package mounts.com.driver.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import mounts.com.driver.R
import mounts.com.driver.Util.Coroutines
import mounts.com.driver.activity.MainActivity
import mounts.com.driver.data.db.AppDatabase
import mounts.com.driver.data.db.entities.Payload
import mounts.com.driver.data.repositories.MapRepsitory

class MyFirebaseMessagingService: FirebaseMessagingService() {

   // override val kodein by kodein()

   private val db:AppDatabase = AppDatabase(this);

//    // Create an Intent for the activity you want to start
//    val resultIntent = Intent(this, MainActivity::class.java)
//    // Create the TaskStackBuilder
//    val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
//        // Add the intent, which inflates the back stack
//        addNextIntentWithParentStack(resultIntent)
//        // Get the PendingIntent containing the entire back stack
//        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
//    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notifyIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(this, "channel_ID").apply {
                setContentTitle(remoteMessage.notification!!.title)
                setContentText(remoteMessage.notification!!.body)
                setPriority(NotificationCompat.PRIORITY_DEFAULT)
                setStyle(NotificationCompat.BigTextStyle())
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                setSmallIcon(R.mipmap.ic_launcher)
                setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
                setLights(1, 1, 5000)
                setAutoCancel(true)
            setContentIntent(notifyPendingIntent)
        }


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("channel_ID",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT).apply {
                enableLights(true)
                lightColor = Color.BLUE
                lockscreenVisibility = 1000
                vibrationPattern = longArrayOf(0,1000,500,1000)

            }
//            channel.enableLights(true)
//            channel.lightColor = Color.BLUE
//            channel.lockscreenVisibility = 1000
//            channel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
//            channel.enableLights(true)
            notificationManager.createNotificationChannel(channel)
        }

        if (remoteMessage.data.size > 0) {

            Log.e("Notification Data",
                    "Message data payload: " +
                            remoteMessage.data["name"])
            Log.e("Notification Data",
                    "Message data payload: " +
                            remoteMessage.data["receiver_name"])
            Log.e("Notification Data",
                    "Message data payload: " +
                            remoteMessage.data["user_lat"])
            Log.e("Notification Data",
                    "Message data payload: " +
                            remoteMessage.data["user_lng"])
            Log.e("Notification Data",
                    "Message data payload: " +
                            remoteMessage.data["receiver_name"])
            Log.e("Notification Data",
                    "Message data payload: " +
                            remoteMessage.data["receiver_phone_number"])
            Log.e("Notification Data",
                    "Message data payload: " +
                            remoteMessage.data["receiver_address"])
            Log.e("Notification Data",
                    "Message data payload: " +
                            remoteMessage.data["receiver_lat"])
            Log.e("Notification Data",
                    "Message data payload: " +
                            remoteMessage.data["receiver_lng"])

            var name = remoteMessage.data.get("name")
            var receiver_name = remoteMessage.data.get("receiver_name")
            var receiver_phone_number = remoteMessage.data.get("receiver_phone_number")
            var receiver_address =  remoteMessage.data.get("receiver_address")
            var user_lat = remoteMessage.data.get("user_lat")?.toDouble()
            var user_lng = remoteMessage.data.get("user_lng")?.toDouble()
            var receiver_lat  =remoteMessage.data.get("receiver_lat")?.toDouble()
            var receiver_lng = remoteMessage.data.get("receiver_lng")?.toDouble()
            Coroutines.main {
                try{
                    db.getPayloadDao().insertPayload(Payload(name,receiver_name,receiver_phone_number,
                            receiver_address,user_lat,user_lng,receiver_lat,receiver_lng) )

                }catch (e:Exception){
                    Log.e("insert Payload","${e.message}")
                }


        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
}