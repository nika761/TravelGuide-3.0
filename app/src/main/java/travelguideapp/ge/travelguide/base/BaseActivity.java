package travelguideapp.ge.travelguide.base;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Locale;

import travelguideapp.ge.travelguide.helper.AuthorizationManager;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.utility.SystemManager;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;


/**
 * Created by n.butskhrikidze on 01/07/2020.
 * <p>
 * დასაბამიდან იყო სიტყვა, და სიტყვა იყო ღმერთთან და ღმერთი იყო სიტყვა.
 * In the beginning was the Word, and the Word was with God, and the Word was God.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static final String READ_EXTERNAL_STORAGE = "read_external_storage";
    public static final String WRITE_EXTERNAL_STORAGE = "write_external_storage";

    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1234;
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 4321;

    private int toastCount = 0;

    @Override
    protected void attachBaseContext(Context context) {
        Locale localeToSwitchTo = new Locale(GlobalPreferences.getLanguage());
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

    protected void openKeyBoard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void closeKeyBoard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean isNullOrEmpty(String val) {
        return val == null || val.isEmpty();
    }

    protected void showToast(String message) {
        try {
            if (toastCount == 0) {
                toastCount++;
                MyToaster.showToast(this, message);
                new Handler().postDelayed(() -> toastCount = 0, 1800);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
