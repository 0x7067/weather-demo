package me.pedroguimaraes.weatherdemo.interactors.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import me.pedroguimaraes.weatherdemo.WeatherApplication;

public class PermissionChecker {

    public static boolean satisfy(String permission) {
        Context context = WeatherApplication.getContext();
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
