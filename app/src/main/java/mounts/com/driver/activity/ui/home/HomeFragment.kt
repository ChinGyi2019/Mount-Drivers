package mounts.com.driver.activity.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.bottom_sheet_fragment.*
import mounts.com.driver.Helpers.Driver
import mounts.com.driver.R
import mounts.com.driver.Util.GpsUtils
import mounts.com.driver.Util.OnSwipeTouchListener
import mounts.com.driver.databinding.FragmentHomeBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class HomeFragment :Fragment(),OnMapReadyCallback,KodeinAware{
    override val kodein by kodein()
    private val factory : HomeViewModelFactory by instance()
    private var isGPSEnabled = false
    lateinit var viewModel:HomeViewModel
     lateinit var binding :FragmentHomeBinding

    companion object{
        @JvmStatic
        lateinit var googleMap:GoogleMap
        @JvmStatic
        private var showBottomSheet:Int? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        showBottomSheet = activity?.intent?.getIntExtra("showBottomSheet",0)
        Log.e("showBottomSheet",showBottomSheet.toString())

        val mapFragment: SupportMapFragment = this.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.lifecycleOwner = this

        GpsUtils(context).turnGPSOn(object : GpsUtils.OnGpsListener {

            override fun gpsStatus(isGPSEnable: Boolean) {
                this@HomeFragment.isGPSEnabled = isGPSEnable
            }
        })
        viewModel.getLocationData().observe(this@HomeFragment, Observer {
          //  viewModel.showMarker(LatLng(it.latitude, it.longitude), googleMap)
        })

   if(viewModel.isSingInWithCustomToken()) {
        viewModel.getRealtimeToken().observe(this, Observer { token ->
            if (token != null) {
                viewModel.signInWithCustomToken(token.token.toString())
                Log.e("RealTime SignIn", "Success")
            } else {
                Log.e("RealTime SignIn", "Fail")
            }
        })
    }

        viewModel.getPayloadData().observe(this@HomeFragment, Observer {
            if(it!=null){
                if(showBottomSheet == 1){
                    showBottomSheet = 0
                    countDown()
                }
                if (it.receiver_lat != null && it.receiver_lng != null){
                    viewModel.showUser(LatLng(it.receiver_lat!!.toDouble(),it.receiver_lng!!.toDouble()),googleMap)
                }

                Log.e("Payload","${it}")

            }
        })

        return binding.root

    }


    override fun onStart() {
        super.onStart()
        invokeLocationAction()
        val mapFragment: SupportMapFragment = this.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
//        viewModel.getLocationData().observe(viewLifecycleOwner, Observer {
//            viewModel.showMarker(LatLng(it.latitude, it.longitude), googleMap)
//        })


    }



    override fun onResume() {
        super.onResume()
        invokeLocationAction()
        val mapFragment: SupportMapFragment = this.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel.getPayloadData().observe(viewLifecycleOwner, Observer {
            if(it!=null){
               // viewModel.showMarker(LatLng(it.receiver_lat!!.toDouble(),it.receiver_lng!!.toDouble()),googleMap)
                Log.e("Payload","${it}")
            }
        })


//        viewModel.getLocationData().observe(this@HomeFragment, Observer {
//        viewModel.showMarker(LatLng(it.latitude, it.longitude), googleMap)
//        })
    }

    fun countDown(){
        val dialogView: View = layoutInflater.inflate(R.layout.bottom_sheet_fragment, null)
        val dialog = context?.let {
            it1 -> BottomSheetDialog(it1,R.style.BottomSheetDialog)
        }

        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setContentView(dialogView)
        dialog?.show()

        var a =0
        val timer = object : CountDownTimer(15000,1000){
            override fun onTick(millisUntilFinished: Long) {
                a= (millisUntilFinished/1000).toInt()
                dialog?.countDownText?.setText("${a}"+" s")
            }
            override fun onFinish() {
                dialog?.countDownText?.setText(a.toString()+" s")
                //TODO:TO UNCOMMENT THIS LINE
               // dialog?.cancel()
            }
        }.start()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }
    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> Log.e("GPS","NEEDED")// buildAlertMessageNoGps("Please Turn on your GPS Connection")

            isPermissionsGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() ->Log.e("Permession","Needed")//buildAlertMessageNoGps(getString(R.string.permission_request))
            else -> ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_REQUEST
            )
        }
    }

    private fun isPermissionsGranted() =
            ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

    private fun startLocationUpdate() {
        viewModel.getLocationData().observe(viewLifecycleOwner, Observer {
            ///latLong.text =  getString(R.string.latLong, it.longitude, it.latitude)
        //    viewModel.showMarker(LatLng(it.latitude,it.longitude),googleMap)
            Log.e("Current Loaction","${it.latitude } ,${it.longitude}")


        })
    }



    private fun shouldShowRequestPermissionRationale() =
            ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
            ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
            )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }

    fun buildAlertMessageNoGps(title:String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(title)
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                .setNegativeButton("No") { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    override fun onMapReady(p0: GoogleMap?) {

        googleMap = p0!!
        if(isPermissionsGranted()) {
            MapsInitializer.initialize(context)
            googleMap.setMyLocationEnabled(true)

            try {
                // Customise the styling of the base map using a JSON object defined
                // in a raw resource file.
                val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                getContext(), R.raw.style_json_dark));

                if (!success) {
                    Log.e("TAG", "Style parsing failed.");
                }
            } catch (e: Exception) {
                Log.e("TAG", "Can't find style. Error: ", e)
            }
            viewModel.getLocationData().observe(viewLifecycleOwner, Observer {
                viewModel.updateDriver(Driver(it.latitude, it.longitude,FirebaseAuth.getInstance().uid.toString()))
             //   viewModel.showMarker(LatLng(it.latitude, it.longitude), googleMap)
                if (it != null)
                    viewModel.animateCamera(LatLng(it.latitude, it.longitude), googleMap)
            })
        }
    }


}
const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101