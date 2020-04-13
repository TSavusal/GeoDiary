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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.google.android.gms.location.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

//Todo: Implement database where text + address info is saved and presented in recycler viewer

//Todo: Send latitude and longitude data from main activity to second fragment (Intent/interface?) alternatively import function to second frag and modify

class MainActivity : AppCompatActivity() {

    val db = AppDatabase(this)
    private fun getData() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "entry-list.db"
        ).build()
        GlobalScope.launch {
            db.entryDao().getAllEntries(EntryEntity(id = 1337,address = "address",date = "date",entry_title = "entry_title",entry_text ="entry_text"))
            val data = db.entryDao().getAll()

            data.forEach {
                println(it)
            }
        }
    }

    //Use PERMISSION_ID when requesting for permission and in after the permission result,
    //PERMISSION_ID used to identify user action with permission request.
    //PERMISSION_ID = Any unique value
    val PERMISSION_ID = 59

    //declare a variable  mFusedLocationClient
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
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
        //setSupportActionBar(toolbar)

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
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        Toast.makeText(this, "Location fetched!", Toast.LENGTH_SHORT).show()
                        //print lat and long to front page
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
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private fun refreshList() {
        doAsync {
            val db: AppDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "entry_table").build()
            db.entryDao().insert(entry)
            db.close()

            uiThread {

                if (entry_table.isNotEmpty()) {
                    val adapter = EntryAdapter(applicationContext, entry_table)
                    list.adapter = adapter

                } else (
                        toast("No reminders yet")
                        )
            }
        }
    }

}
