package travelguideapp.ge.travelguide.base;

import android.content.Context;
import android.content.ContextWrapper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context context) {
        Locale localeToSwitchTo = new Locale(GlobalPreferences.getLanguage(context));
        ContextWrapper localeUpdatedContext = SystemManager.updateLocale(context, localeToSwitchTo);
        super.attachBaseContext(localeUpdatedContext);
    }

}
