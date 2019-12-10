//package mounts.com.driver.activity.ui.home;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.os.Looper;
//import android.provider.Settings;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Switch;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.MapStyleOptions;
//import com.google.android.gms.maps.model.Marker;
//
//import org.kodein.di.Kodein;
//import org.kodein.di.KodeinAware;
//
//import mounts.com.driver.Helpers.Driver;
//import mounts.com.driver.Helpers.FirebaseHelper;
//import mounts.com.driver.Helpers.GoogleMapHelper;
//import mounts.com.driver.Helpers.UiHelper;
//import mounts.com.driver.Interfaces.IPositiveNegativeListener;
//import mounts.com.driver.R;
//import static android.content.ContentValues.TAG;
//
//public class HomeFragment extends Fragment implements OnMapReadyCallback {
//
//    private HomeViewModel homeViewModel;
//    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 6161;
//    private FusedLocationProviderClient locationProviderClient;
//    private LocationRequest locationRequest;
//     //TextView textView;
//    private   SupportMapFragment mapFragment;
//    private Marker currentPositionMarker = null;
//
//
//
//    private static LocationCallback locationCallback;
//    public static LatLng currentLocation;
//    private boolean locationFlag = true;
//    public static GoogleMap googleMap;
//    private Switch aSwitch;
//    private boolean driverOnlineFlag = false;
//    private GoogleMapHelper googleMapHelper = new GoogleMapHelper();
//    private UiHelper uiHelper = new UiHelper();
//    private FirebaseHelper firebaseHelper = new FirebaseHelper();
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//         mapFragment = (SupportMapFragment)
//                this.getChildFragmentManager()
//                        .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//
//        createLocationCallback();
//        locationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
//        locationRequest = uiHelper.getLocationRequest();
//        if (!uiHelper.isPlayServicesAvailable(getContext())) {
//            Toast.makeText(getContext(), "Play Services did not installed!", Toast.LENGTH_SHORT).show();
//            getActivity().finish();
//        } else  requestLocationUpdate();//if(uiHelper.isHaveLocationPermission(getContext()))
//        firebaseHelper.getDriver();
//
//        return root;
//    }
//    @SuppressLint("MissingPermission")
//    private void requestLocationUpdate() {
//        if (!uiHelper.isHaveLocationPermission(getContext())) {
//
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//            return;
//        }
//        if (uiHelper.isLocationProviderEnabled(getContext())) {
//            uiHelper.showPositiveDialogWithListener(getContext(),
//                    getResources().getString(R.string.need_location),
//                    getResources().getString(R.string.location_content),
//                    new IPositiveNegativeListener() {
//                        @Override
//                        public void onNegative() {
//
//                        }
//
//                        @Override
//                        public void onPositive() {
//                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                        }
//                    }, "Turn On", false);
//        }
//        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
//    }
//    public void createLocationCallback() {
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                if (locationResult.getLastLocation() == null) return;
//                //LatLn
//               currentLocation = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
//
//                if (locationFlag) {
//                    locationFlag = false;
//                    if (currentLocation != null) {
//                        animateCamera(currentLocation);
//
//                        Log.e("Location ***", currentLocation.latitude + " , " + currentLocation.longitude);
//                         showMarker(currentLocation);
//                    } else {
//                       Log.e("currentLocation", "Null");
//
//                    }
//                }//String.format("%d",new Random().nextInt(50)))
//                firebaseHelper.updateDriver(new Driver(currentLocation.latitude, currentLocation.longitude,"lmJMczn0vZMscn2Asq5vSFpCU2l2") );
//
//            }
//        };
//    }
//    private void animateCamera(LatLng latLng) {
//        CameraUpdate cameraUpdate;
//        try {
//            cameraUpdate = googleMapHelper.buildCameraUpdate(latLng);
//            if (cameraUpdate != null) {
//                googleMap.animateCamera(cameraUpdate, 10, null);
//                Log.e("cameraUpdate", "cameraUpdate not null");
//
//            } else if (cameraUpdate == null) {
//                Log.e("AnimateCamera", "cameraupdat null null");
//                googleMap.animateCamera(cameraUpdate, 11, null);
//            }
//        } catch (Exception e) {
//            Log.e("AnimateCamera", e.getMessage());
//        }
//    }
//
//
//    @Override
//    public void onMapReady(GoogleMap gMap) {
//
////        this.googleMap = gMap;
////        MapsInitializer.initialize(getContext());
////        googleMap.setMyLocationEnabled(true);
////        if(currentLocation != null){
////        LatLng sydney = new LatLng(currentLocation.latitude, currentLocation.longitude);
////            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
////            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
////        }
////        Log.e("Map", "Error");
//        this.googleMap = gMap;
//        if(uiHelper.isHaveLocationPermission(getContext())) {
//            MapsInitializer.initialize(getContext());
//            googleMap.setMyLocationEnabled(true);
//            try {
//                // Customise the styling of the base map using a JSON object defined
//                // in a raw resource file.
//                boolean success = googleMap.setMapStyle(
//                        MapStyleOptions.loadRawResourceStyle(
//                                getContext(), R.raw.style_json_dark));
//
//                if (!success) {
//                    Log.e(TAG, "Style parsing failed.");
//                }
//            } catch (Resources.NotFoundException e) {
//                Log.e(TAG, "Can't find style. Error: ", e);
//            }
//            // Position the map's camera near Sydney, Australia.
//            if (currentLocation != null)
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.latitude, currentLocation.longitude)));
//        }
//    }
//    private void showMarker(LatLng latLng){
//        try{
//            if (currentPositionMarker == null)
//                currentPositionMarker = googleMap.addMarker(googleMapHelper.getDriverMarkerOptions(latLng));
//            else {
//                Log.e("LagLan", latLng.toString());
//            }
//        }catch (Exception e){
//            Log.e("LagLan", e.getMessage());
//        }
////    if (currentPositionMarker == null)
////        currentPositionMarker = googleMap.
////                addMarker(googleMapHelper.getUserMakerOptions(latLng));
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        createLocationCallback();
//        requestLocationUpdate();
//        SupportMapFragment mapFragment = (SupportMapFragment)
//                this.getChildFragmentManager()
//                        .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                //this.googleMap = googleMap;
//                if(uiHelper.isHaveLocationPermission(getContext())) {
//                    MapsInitializer.initialize(getContext());
//                    googleMap.setMyLocationEnabled(true);
//                    try {
//                        // Customise the styling of the base map using a JSON object defined
//                        // in a raw resource file.
//                        boolean success = googleMap.setMapStyle(
//                                MapStyleOptions.loadRawResourceStyle(
//                                        getContext(), R.raw.style_json_dark));
//
//                        if (!success) {
//                            Log.e(TAG, "Style parsing failed.");
//                        }
//                    } catch (Resources.NotFoundException e) {
//                        Log.e(TAG, "Can't find style. Error: ", e);
//                    }
//                    // Position the map's camera near Sydney, Australia.
//                    if (currentLocation != null)
//
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.latitude, currentLocation.longitude)));
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        SupportMapFragment mapFragment = (SupportMapFragment)
//                this.getChildFragmentManager()
//                        .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//    }
//}