package me.pedroguimaraes.weatherdemo.interactors.location

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import me.pedroguimaraes.weatherdemo.WeatherApplication
import me.pedroguimaraes.weatherdemo.injection.DependencyInjection

open class LocationGetter : LocationListener {

    private val dependencyInjection = DependencyInjection(WeatherApplication.getContext())

    private val locationManager: LocationManager = dependencyInjection.locationManager()
    private val packageManager: PackageManager = dependencyInjection.packageManager()

    private var location: Location? = null

    @SuppressLint("MissingPermission")
    fun getLocation(): Location? {
        if (hasNetworkAndIsEnabled()) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATES,
                    MIN_DISTANCE_TO_REQUEST_LOCATION, this)
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } else if (hasGpsAndIsEnabled()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATES,
                    MIN_DISTANCE_TO_REQUEST_LOCATION, this)
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }

        return location
    }

    fun getCityName(lat: Double, lon: Double): String {
        val geocoder = dependencyInjection.geocoder()
        val addresses = geocoder.getFromLocation(lat, lon, 1)
        return addresses[0].locality
    }

    private fun hasNetworkAndIsEnabled(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_NETWORK)
                && locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
    }

    private fun hasGpsAndIsEnabled(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS) &&
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
