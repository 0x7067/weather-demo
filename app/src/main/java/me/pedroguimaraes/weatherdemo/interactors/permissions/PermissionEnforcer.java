package me.pedroguimaraes.weatherdemo.interactors.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import me.pedroguimaraes.weatherdemo.R;

public class PermissionEnforcer {

    public static final int APP_SETTINGS_REQUEST_CODE = 101;

    private final Activity activity;
    private final AlertDialog enablePermissionsDialog;
    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public PermissionEnforcer(Activity activity) {
        this.activity = activity;
        this.enablePermissionsDialog = createEnablePermissionsDialog();
    }

    public void requestPermissions() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(activity, permissions, APP_SETTINGS_REQUEST_CODE);
        }
    }

    public void enforcePermissions() {
        if (!allPermissionsGranted()) {
            if (!enablePermissionsDialog.isShowing()) {
                enablePermissionsDialog.show();
            }
        }
    }

    public boolean allPermissionsGranted() {
        for (String p : permissions) {
            if (!PermissionChecker.satisfy(p)) {
                return false;
            }
        }
        return true;
    }

    private AlertDialog createEnablePermissionsDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setMessage(R.string.permission_location_rationale)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> goToSettings());

        return dialogBuilder.create();
    }

    private void goToSettings() {
        Intent appPermissionSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + activity.getPackageName()));
        appPermissionSettings.addCategory(Intent.CATEGORY_DEFAULT);
        activity.startActivityForResult(appPermissionSettings, APP_SETTINGS_REQUEST_CODE);
    }

}
