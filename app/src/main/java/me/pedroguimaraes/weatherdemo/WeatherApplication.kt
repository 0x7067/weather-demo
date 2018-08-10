package me.pedroguimaraes.weatherdemo

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary

class WeatherApplication : Application() {

    override fun onCreate() {
        instance = this
        super.onCreate()
        startLeakCanary()
    }

    private fun startLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    companion object {

        private lateinit var instance: WeatherApplication

        val context: Context
            get() = instance.applicationContext
    }
}
