package com.example.p8_maps_location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ola.mapsdk.interfaces.OlaMapCallback
import com.ola.mapsdk.model.OlaLatLng
import com.ola.mapsdk.view.OlaMap
import com.ola.mapsdk.view.OlaMapView
import android.app.AlertDialog
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var olamapView: OlaMapView
    lateinit var fetchCurrentLocation: FloatingActionButton
    lateinit var olaMap2: OlaMap

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        olamapView = findViewById(R.id.mapView)
        fetchCurrentLocation = findViewById(R.id.floatingActionButton)

        fetchCurrentLocation.setOnClickListener {
            if (checkLocationPermission()) {
                if (isLocationEnabled()) {
                    getCurrentLocation()
                } else {
                    Toast.makeText(this, "Please enable location services", Toast.LENGTH_LONG).show()
                }
            } else {
                requestLocationPermission()
            }
        }

        olamapView.getMap(apiKey = "tv6AsJBnvg5JyzoeL8K2J8fz5OZPqRYgbWR3Yd4k",
            olaMapCallback = object : OlaMapCallback {
                override fun onMapReady(olaMap: OlaMap) {
                    olaMap2 = olaMap
                    Log.d(TAG, "Map is ready")
                }
                override fun onMapError(error: String) {
                    Log.e(TAG, "Map Error: $error")
                    Toast.makeText(this@MainActivity, "Map Error: $error", Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertDialog.Builder(this)
                .setTitle("Location Permission Needed")
                .setMessage("This app needs the Location permission to show your current location on the map.")
                .setPositiveButton("OK") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
                .create()
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun getCurrentLocation() {
        Log.d(TAG, "Attempting to get current location")
        if (checkLocationPermission()) {
            try {
                val currentLocation: OlaLatLng? = olaMap2.getCurrentLocation()
                if (currentLocation != null) {
                    handleLocation(currentLocation)
                } else {
                    Log.d(TAG, "Current location is null, waiting for 2 seconds and trying again")
                    Handler(Looper.getMainLooper()).postDelayed({
                        val delayedLocation: OlaLatLng? = olaMap2.getCurrentLocation()
                        if (delayedLocation != null) {
                            handleLocation(delayedLocation)
                        } else {
                            Log.e(TAG, "Still unable to get current location")
                            Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
                            getLastKnownLocation()
                        }
                    }, 2000) // Wait for 2 seconds before trying again
                }
            } catch (e: SecurityException) {
                Log.e(TAG, "SecurityException when getting current location: ${e.message}")
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
                requestLocationPermission()
            }
        } else {
            Log.e(TAG, "Location permission not granted")
            Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
            requestLocationPermission()
        }
    }

    private fun handleLocation(location: OlaLatLng) {
        Log.d(TAG, "Handling location: ${location.latitude}, ${location.longitude}")
        val zoomLevel = 15.0
        olaMap2.zoomToLocation(location, zoomLevel)
        olaMap2.showCurrentLocation()
        Toast.makeText(this, "Showing current location", Toast.LENGTH_SHORT).show()
    }

    private fun getLastKnownLocation() {
        Log.d(TAG, "Attempting to get last known location")
        if (checkLocationPermission()) {
            try {
                val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val providers = locationManager.getProviders(true)
                var bestLocation: android.location.Location? = null
                for (provider in providers) {
                    val location = locationManager.getLastKnownLocation(provider)
                    if (location != null) {
                        if (bestLocation == null || location.accuracy < bestLocation.accuracy) {
                            bestLocation = location
                        }
                    }
                }

                if (bestLocation != null) {
                    Log.d(TAG, "Last known location: ${bestLocation.latitude}, ${bestLocation.longitude}")
                    val lastKnownLocation = OlaLatLng(bestLocation.latitude, bestLocation.longitude)
                    handleLocation(lastKnownLocation)
                } else {
                    Log.e(TAG, "No last known location available")
                    Toast.makeText(this, "No location available", Toast.LENGTH_SHORT).show()
                }
            } catch (e: SecurityException) {
                Log.e(TAG, "SecurityException when getting last known location: ${e.message}")
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
                requestLocationPermission()
            }
        } else {
            Log.e(TAG, "Location permission not granted")
            Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
            requestLocationPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    if (isLocationEnabled()) {
                        getCurrentLocation()
                    } else {
                        Toast.makeText(this, "Please enable location services", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}