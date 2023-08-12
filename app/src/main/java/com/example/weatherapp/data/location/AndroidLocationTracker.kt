package com.example.weatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.models.MapLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit

class AndroidLocationTracker(
    private val client: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {

    override fun getCurrentLocation() =
        callbackFlow {
            val hasFineLocationPerm = ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val hasCoarseLocationPerm = ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            if (hasFineLocationPerm && hasCoarseLocationPerm && isGpsEnabled) {

                val locationRequest: LocationRequest = LocationRequest.create()
                    .apply {
                        interval = TimeUnit.SECONDS.toMillis(UPDATE_INTERVAL_SECS)
                        fastestInterval = TimeUnit.SECONDS.toMillis(FASTEST_UPDATE_INTERVAL_SECS)
                        priority = Priority.PRIORITY_HIGH_ACCURACY
                    }


                val callBack = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        val location = locationResult.lastLocation

                        location?.let {
                            val userLocation = MapLocation(
                                latitude = it.latitude,
                                longitude = it.longitude,
                                bearing = it.bearing
                            )
                            trySendBlocking(userLocation)
                        }
                    }
                }

                client.requestLocationUpdates(locationRequest, callBack, Looper.getMainLooper())
                awaitClose { client.removeLocationUpdates(callBack) }
            } else {
                close(Exception("Could not access location or GPS"))
            }
        }

    companion object {
        private const val UPDATE_INTERVAL_SECS = 10L
        private const val FASTEST_UPDATE_INTERVAL_SECS = 5L
    }

}