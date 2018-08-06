package me.pedroguimaraes.weatherdemo.injection

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class AppModule(context: Application) {
    private val context: Context

    init {
        this.context = context
    }

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun LocationManager(): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Provides
    @Singleton
    fun geocoder(): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }

    @Provides
    @Singleton
    fun PackageManager(): PackageManager {
        return context.packageManager
    }

}
