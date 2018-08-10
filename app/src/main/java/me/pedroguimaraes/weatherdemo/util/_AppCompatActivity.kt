package me.pedroguimaraes.weatherdemo.util

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.goToAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}