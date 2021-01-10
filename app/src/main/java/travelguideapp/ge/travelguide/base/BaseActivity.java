package travelguideapp.ge.travelguide.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.enums.GetPostsFrom;
import travelguideapp.ge.travelguide.enums.SearchPostBy;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import travelguideapp.ge.travelguide.ui.home.customerUser.CustomerProfileActivity;
import travelguideapp.ge.travelguide.ui.home.home.HomeFragment;
import travelguideapp.ge.travelguide.ui.login.signIn.SignInActivity;
import travelguideapp.ge.travelguide.ui.searchPost.SearchPostActivity;


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

    protected void onAuthenticateError(String message) {
        MyToaster.getErrorToaster(this, message);

        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHARING_INTENT_REQUEST_CODE) {
            /*Supposedly TO-DO : handle sharing request by result **/
        }
    }

    protected void getKeyboard(boolean show, View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                if (show) {
                    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                } else {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startCustomerActivity(int userId) {
        try {
            Intent intent = new Intent(this, CustomerProfileActivity.class);
            intent.putExtra("id", userId);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_activity_slide_in_right, R.anim.anim_activity_slide_out_left);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startSearchPostActivity(String hashtag, SearchPostBy searchPostBy) {
        try {
            Intent postHashtagIntent = new Intent(this, SearchPostActivity.class);
            postHashtagIntent.putExtra("search_type", searchPostBy);
            postHashtagIntent.putExtra("search_hashtag", hashtag);
            startActivity(postHashtagIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
