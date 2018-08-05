package me.pedroguimaraes.weatherdemo.interactors.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import me.pedroguimaraes.weatherdemo.WeatherApplication
import java.util.*


class LocationManager : LocationListener {

    private val locationManager: LocationManager
    private val context: Context = WeatherApplication.getContext()
    private var location: Location? = null

    init {
        this.locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @SuppressLint("MissingPermission")
    fun getLocation(): Location? {
        if (hasNetworkAndIsEnabled()) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATES, MIN_DISTANCE_TO_REQUEST_LOCATION, this)
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } else if (hasGpsAndIsEnabled()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATES, MIN_DISTANCE_TO_REQUEST_LOCATION, this)
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }

        return location
    }

    fun getCityName(lat: Double, lon: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(lat, lon, 1)
        return addresses[0].locality
    }

    private fun hasNetworkAndIsEnabled(): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_NETWORK)
                && locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
    }

    private fun hasGpsAndIsEnabled(): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS) &&
                locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
    }

    override fun onLocationChanged(location: Location) {
        this.location = location
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {

    }

    companion object {

        private const val MIN_DISTANCE_TO_REQUEST_LOCATION: Float = 1.0F
        private const val MIN_TIME_FOR_UPDATES: Long = 1000
    }

}
