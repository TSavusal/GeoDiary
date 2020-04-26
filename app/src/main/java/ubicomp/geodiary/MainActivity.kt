package ubicomp.geodiary

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Use PERMISSION_ID when requesting for permission and in after the permission result,
    //PERMISSION_ID used to identify user action with permission request.
    //PERMISSION_ID = Any unique value
    val PERMISSION_ID = 59

    val lattxt = "65.0137"
    val longtxt = "25.4717"

    //declare a variable  mFusedLocationClient
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            findViewById<TextView>(R.id.latTextView).text = mLastLocation.latitude.toString()
            findViewById<TextView>(R.id.lonTextView).text = mLastLocation.longitude.toString()
        }
    }

    //Check status of location settings
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Initialize mFusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    //@SuppressLint = Ignore specified warning "MissingPermission"
    // getLastLocation() uses API to return the location information of the device
    // Also will first check if 1. permission is granted and 2. if the location setting is turned on
    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        //Print lat and long to front page
                        Toast.makeText(this, "Location fetched!", Toast.LENGTH_SHORT).show()
                        //Use intent to share lat & long with secondfragment
                        val longtxtmsg: String = location.longitude.toString()
                        val lattxtmsg: String = location.latitude.toString()
                        val intent = Intent(this, SecondFragment::class.java)
                        intent.putExtra("longkey", longtxtmsg)
                        intent.putExtra("latkey", lattxtmsg)

                        findViewById<TextView>(R.id.latTextView).text = location.latitude.toString()
                        findViewById<TextView>(R.id.lonTextView).text = location.longitude.toString()
                    }
                }
            } else {
                //Ask to turn on location
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, "Requesting permission!", Toast.LENGTH_SHORT).show()
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
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }
}
