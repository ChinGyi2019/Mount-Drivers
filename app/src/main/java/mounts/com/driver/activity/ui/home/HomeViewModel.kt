package mounts.com.driver.activity.ui.home

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import mounts.com.driver.Helpers.Driver
import mounts.com.driver.Helpers.GoogleMapHelper
import mounts.com.driver.Helpers.UiHelper
import mounts.com.driver.Util.Coroutines
import mounts.com.driver.data.repositories.MapRepsitory


class HomeViewModel (
        private val repository: MapRepsitory,
         application: Application
                     ):AndroidViewModel(application) {
    var context = application.applicationContext

    private var locationData = LocationLiveData(application)

   fun showMarker(latLng: LatLng, googleMap: GoogleMap) = repository.showMarker(latLng, googleMap)
    fun showUser(latLng: LatLng,googleMap: GoogleMap) = repository.showUser(latLng,googleMap)

//    fun showMarker(latLng: LatLng,googleMap: GoogleMap) {
////        try {
//////            if (currentPositionMarker == null)
//      googleMap!!.addMarker(repository.getDriverMarkerOptions(latLng))
//////            else {
//////                Log.e("LagLan", latLng.toString())
//////            }
//////        } catch (e: Exception) {
//////            Log.e("LagLan", e.message)
////        }
//
//    }
    fun getLocationData() = locationData

    fun animateCamera(latLng: LatLng, googleMap: GoogleMap) = repository.animateCamera(latLng, googleMap)

    fun updateDriver(driver: Driver) = repository.updateDriver(driver)

    //  @@@@@@@     REALTIME SIGNGED IN  @@@@@@@@   //
    fun getRealtimeToken() = repository.getRealtimeToken()
    fun signInWithCustomToken(token: String) {
        Coroutines.main{
            repository.signWithCustomToken(token)
        }
    }
    fun isSingInWithCustomToken() : Boolean = repository.isSignedInWithCustomToken()

    fun getPayloadData()= repository.getPayloadData()


   // fun isHaveLocationPermission():Boolean = repository.isHaveLocationPermission(context)
}