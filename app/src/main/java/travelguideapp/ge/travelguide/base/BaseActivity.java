package travelguideapp.ge.travelguide.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Locale;

import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.ui.gallery.GalleryActivity;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

import static travelguideapp.ge.travelguide.helper.SystemManager.READ_EXTERNAL_STORAGE;

/**
 * Created by n.butskhrikidze on 01/07/2020.
 * <p>
 * დასაბამიდან იყო სიტყვა, და სიტყვა იყო ღმერთთან და ღმერთი იყო სიტყვა.
 * In the beginning was the Word, and the Word was with God, and the Word was God.
 * <p>
 */

public class BaseActivity extends AppCompatActivity {

    public static final String READ_EXTERNAL_STORAGE = "read_external_storage";
    public static final String WRITE_EXTERNAL_STORAGE = "write_external_storage";

    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1234;
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 4321;

    @Override
    protected void attachBaseContext(Context context) {
        Locale localeToSwitchTo = new Locale(GlobalPreferences.getLanguage(context));
        ContextWrapper localeUpdatedContext = SystemManager.updateLocale(context, localeToSwitchTo);
        super.attachBaseContext(localeUpdatedContext);
    }

    public boolean isPermissionGranted(String permission) {
        int result;
        boolean permissionGranted = false;
        switch (permission) {
            case READ_EXTERNAL_STORAGE:
                result = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                permissionGranted = result == PackageManager.PERMISSION_GRANTED;
                break;
            case WRITE_EXTERNAL_STORAGE:
                result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                permissionGranted = result == PackageManager.PERMISSION_GRANTED;
                break;
        }
        return permissionGranted;
    }

    public void requestPermission(String permission) {
        switch (permission) {
            case READ_EXTERNAL_STORAGE:
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
                break;
            case WRITE_EXTERNAL_STORAGE:
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                break;
        }
    }

    public void onPermissionResult(boolean permissionGranted) {
        //Child method
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE_REQUEST_CODE:
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                onPermissionResult(grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
        }
    }
}
