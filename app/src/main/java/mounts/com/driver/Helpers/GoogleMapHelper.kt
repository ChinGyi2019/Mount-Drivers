package mounts.com.driver.Helpers

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import mounts.com.driver.R
import java.util.*
import java.util.EnumSet.range


class GoogleMapHelper() {

    companion object {
        private const val ZOOM_LEVEL = 18
        private const val TILT_LEVEL = 25
        private lateinit var addresses: MutableList<Address>
        private lateinit var address: Address
    }


            fun getMarkerOptions(resource: Int, position: LatLng): MarkerOptions {
                return MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(resource))
                        .position(position)
            }
            fun getUserMakerOptions(position: LatLng): MarkerOptions {
                val options = getMarkerOptions(R.drawable.placeholder, position)
                options.position(position)
                options.zIndex(0.5f)
                options.flat(true);
                options.draggable(true)
                return options
            }
            fun getDriverMarkerOptions(position: LatLng): MarkerOptions {
                val options = getMarkerOptions(R.drawable.car_icon, position)
                options.position(position)
                options.zIndex(0.5f)
                options.flat(true)
                options.draggable(true)
                return options
            }




        lateinit var geocoder: Geocoder
        var featureName: String? = null
        /**
         * @param latLng in which position to Zoom the camera.
         * @return the [CameraUpdate] with Zoom and Tilt level added with the given position.
         */

        fun buildCameraUpdate(latLng: LatLng): CameraUpdate {
            val cameraPosition = CameraPosition.Builder()
                    .target(latLng)
                    .tilt(TILT_LEVEL.toFloat())
                    .zoom(ZOOM_LEVEL.toFloat())
                    .build()
            return CameraUpdateFactory.newCameraPosition(cameraPosition)
        }

        /**
         * @param position where to draw the [com.google.android.gms.maps.model.Marker]
         * @return the [MarkerOptions] with given properties added to it.
         */





        fun setLocationtoEditText(geocoder: Geocoder, view: EditText, currentLocation: LatLng) {

            if (geocoder == null) {
                view.setText("")
                addresses = geocoder.getFromLocation(currentLocation.latitude,
                        currentLocation.longitude, 1)
                if (addresses.size > 0 && null != addresses) {

                    address = addresses.get(0)
                    featureName = address.featureName

                    for (i in 0 until address.maxAddressLineIndex) {

                        if (i == 0) {
                            view.setText(address.featureName + "," +
                                    (address.locale)).let {
                                if (featureName == null)
                                    view.setText(address.countryName + "," +
                                            address.getAddressLine(i))
                            }
                            Log.e("Pin Address i == 0", "Name:" + address.featureName + "," +
                                    address.getAddressLine(i))
                        } else {
                            view.setText(address.featureName + "," +
                                    address.getAddressLine(i)).let {
                                if (featureName == null)
                                    view.setText(address.countryName + "," +
                                            address.getAddressLine(i))
                            }
                            Log.e("Pin Address i != 0", "Name:" + address.featureName + "," +
                                    address.getAddressLine(i))
                        }

                    }

                }
            } else {
                Log.e("null", "Null")
            }
        }

        private fun getAddress(position: Int): String {
            return address.featureName + "," +
                    address.getAddressLine(position).let {
                        if (featureName == null)
                            address.countryName + "," +
                                    address.getAddressLine(position)
                    }


        }

}