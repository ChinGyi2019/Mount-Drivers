package mounts.com.driver.Helpers

import android.util.Log
import com.google.firebase.database.*
import java.lang.Exception
/*driverId: String*/

class FirebaseHelper constructor() {

    companion object {
        private const val ONLINE_DRIVERS = "delivery-men"
    }
//    private lateinit var database: DatabaseReference
//// ...
//    database = FirebaseDatabase.getInstance().reference

    private val onlineDriverDatabaseReference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .reference
            .child(ONLINE_DRIVERS)
           // .child(driverId)

    init {
        onlineDriverDatabaseReference
                .onDisconnect()
                .removeValue()
    }

    fun updateDriver(driver: Driver) {
        try {
            val d = HashMap<String,Any>()
            d["lat"] = driver.lat
            d["lng"] = driver.lng
            onlineDriverDatabaseReference
                    .child(driver.id)
                    .setValue(d)
            Log.e("Driver Info", " Updated")
        }catch (e: Exception){
            Log.e("Driver Info",e?.message)
        }
    }

    fun deleteDriver() {
        onlineDriverDatabaseReference
                .removeValue()
    } fun getDriver() {

        onlineDriverDatabaseReference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.e("Driver", p0.value.toString())
                 }

        })
    }

}
data class Driver(val lat: Double, val lng: Double,val id:String)