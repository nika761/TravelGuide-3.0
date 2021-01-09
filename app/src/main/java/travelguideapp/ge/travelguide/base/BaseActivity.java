package travelguideapp.ge.travelguide.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.enums.GetPostsFrom;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import travelguideapp.ge.travelguide.ui.home.home.HomeFragment;


public class BaseActivity extends AppCompatActivity {

    public static final int SHARING_INTENT_REQUEST_CODE = 500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentManager getCurrentChildFragmentManager() {
        try {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment_container);
            if (fragment instanceof HomeFragment) {
                return fragment.getChildFragmentManager();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        FragmentManager currentChildFragmentManager = getCurrentChildFragmentManager();
        if (currentChildFragmentManager != null) {
            if (currentChildFragmentManager.getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                currentChildFragmentManager.popBackStackImmediate();
            }
        } else {
            super.onBackPressed();
        }
    }

    public void shareContent(String content) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        startActivityForResult(sharingIntent, SHARING_INTENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHARING_INTENT_REQUEST_CODE) {
            /*Supposedly TO-DO : handle sharing request by result **/
        }
    }
}
