package ubicomp.geodiary

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
//import androidx.room.Room
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*

//Todo: Implement database where text + address info is saved and presented in recyclerviewer

//Todo: Send latitude and longitude data from mainactivity to second fragment (Intent/interface?) altertnatively import function to secondfrag and modify

class MainActivity : AppCompatActivity() {

    //Use PERMISSION_ID when requesting for permission and in after the permission result,
    //PERMISSION_ID used to identify user action with permission request.
    //PERMISSION_ID = Any unique value
    val PERMISSION_ID = 59
    //declare a variable  mFusedLocationClient
    lateinit var mFusedLocationClient: FusedLocationProviderClient
/*
    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "entry-list.db").build()

 */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Initialize mFusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    //Ignore specified warning "MissingPermission"
    @SuppressLint("MissingPermission")
    //getLastLocation() uses API to return the location information of the device
    //Also will first check if 1. permission is granted and 2. if the location setting is turned on
    fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        //print lat and long to front page
                        findViewById<TextView>(R.id.latTextView).text = location.latitude.toString()
                        findViewById<TextView>(R.id.lonTextView).text = location.longitude.toString()
                        val SharedLatitude = location.latitude
                        val SharedLongitude = location.longitude
                    }
                }
            } else {
                //Ask to turn on location
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            //Ask for permission
            requestPermissions()
        }
    }
    //Ignore specified warning "MissingPermission"
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        /*
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )*/
    }
/*
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            findViewById<TextView>(R.id.latTextView).text = mLastLocation.latitude.toString()
            findViewById<TextView>(R.id.lonTextView).text = mLastLocation.longitude.toString()
        }
    }

 */
    //Check status of location settings
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    //Check permission for ACCESS_COARSE_LOCATION and ACCESS_FINE_LOCATION
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    //Request necessary permissions if not granted
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }
    //Called when requested permissions are allowed or denied, getLastLocation() if allowed.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted, get location information
                getLastLocation()
            }
        }
    }
}
