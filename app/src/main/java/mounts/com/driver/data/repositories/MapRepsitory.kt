package mounts.com.driver.data.repositories

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.mounts.lenovo.delivery3interfaces.LatLngInterpolator.Spherical
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mounts.com.driver.Helpers.Driver
import mounts.com.driver.Helpers.FirebaseHelper
import mounts.com.driver.Helpers.MarkerAnimationHelper
import mounts.com.driver.R
import mounts.com.driver.data.db.AppDatabase
import mounts.com.driver.data.db.entities.Payload
import mounts.com.driver.data.netwrok.FirebaseSource

class MapRepsitory(private val firebaseHelper: FirebaseHelper,
                   private val firebaseSource: FirebaseSource,
                   private val markerAnimation:MarkerAnimationHelper,
                   private val db:AppDatabase) {
    companion object {
        private const val ZOOM_LEVEL = 18
        private const val TILT_LEVEL = 25


    }
    private var currentPositionMarker: Marker? = null

    private var googleMap: GoogleMap? = null
    private var payload =MutableLiveData<Payload>()




    fun showMarker(latLng: LatLng,googleMap: GoogleMap) {
        try {
            if (currentPositionMarker == null)
                currentPositionMarker = googleMap!!.addMarker(getDriverMarkerOptions(latLng))
            else {
                showOrAnimateMarker(latLng)
                Log.e("LagLan", latLng.toString())
            }
        } catch (e: Exception) {
            Log.e("LagLan", e.message)
        }

    }

    fun showUser(latLng: LatLng,googleMap: GoogleMap) {
        try {
            googleMap!!.addMarker(getDriverMarkerOptions(latLng))
        } catch (e: Exception) {
            Log.e("showUser", e.message)
        }

    }


    private fun showOrAnimateMarker(latLng: LatLng) {
//        if (currentPositionMarker == null)
//            currentPositionMarker = googleMap!!.addMarker(getDriverMarkerOptions(latLng))
//        else { //Log.e("LagLan", latLng.toString());
            markerAnimation.animateMarkerToGB(currentPositionMarker!!, latLng,Spherical())
//        }
    }

    fun getMarkerOptions(resource: Int, position: LatLng): MarkerOptions {
        return MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(resource))
                .position(position)
    }

    fun  getDriverMarkerOptions(position: LatLng): MarkerOptions {
        val options = getMarkerOptions(R.drawable.car_icon, position)
        options.position(position)
        options.zIndex(0.5f)
        options.flat(true)
        options.draggable(true)
        return options
    }

    fun animateCamera(latLng: LatLng,googleMap: GoogleMap) {
        val cameraUpdate: CameraUpdate
        try {
            cameraUpdate =buildCameraUpdate(latLng)
            if (cameraUpdate != null) {
                googleMap.animateCamera(cameraUpdate, 10, null)
              //  Log.e("cameraUpdate", "cameraUpdate not null")
            } else if (cameraUpdate == null) {
               // Log.e("AnimateCamera", "cameraupdat null null")
                googleMap.animateCamera(cameraUpdate, 11, null)
            }
        } catch (e: java.lang.Exception) {
            Log.e("AnimateCamera", e.message)
        }
    }

    fun buildCameraUpdate(latLng: LatLng): CameraUpdate {
        val cameraPosition = CameraPosition.Builder()
                .target(latLng)
                .tilt(TILT_LEVEL.toFloat())
                .zoom(ZOOM_LEVEL.toFloat())
                .build()
        return CameraUpdateFactory.newCameraPosition(cameraPosition)
    }

    fun updateDriver(driver: Driver) =firebaseHelper.updateDriver(driver)

    fun isHaveLocationPermission(context: Context): Boolean{ return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
    }
    // ****REALTIME TOKEN**** //
    fun getRealtimeToken() = db.getUserDao().getRealtimeToken()
    suspend fun signWithCustomToken(token :String) = firebaseSource.signWithCustomToken(token)
    fun isSignedInWithCustomToken():Boolean {
        return firebaseSource.firebaseAuth.currentUser?.uid == null
    }

    fun getPayloadData():LiveData<Payload> = db.getPayloadDao().getPayload()
    suspend fun savePayloadData(payload: Payload) = db.getPayloadDao().insertPayload(payload)
    suspend fun getPayloadData1(): LiveData<Payload>{
        return withContext(Dispatchers.IO){
           // fetchQuotes()
            db.getPayloadDao().getPayload()
        }
    }
}




