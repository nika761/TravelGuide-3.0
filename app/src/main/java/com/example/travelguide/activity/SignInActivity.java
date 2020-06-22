package com.example.travelguide.activity;

import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelguide.R;
import com.example.travelguide.fragments.ForgotPswFragment;
import com.example.travelguide.fragments.RegisterFragment;
import com.example.travelguide.fragments.SignInFragment;
import com.example.travelguide.fragments.TermsOfServiceFragment;
import com.example.travelguide.interfaces.FragmentClickActions;

public class SignInActivity extends AppCompatActivity {

    private SignInFragment signInFragment;
    private Button fragmentHead;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        loadFragment(new SignInFragment(), null, R.id.fragment_container, false);
    }
//
//    public void loadSignInFragment() {
////        fragmentHead.setText(R.string.welcome);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.fragment_container, signInFragment, "signIn")
//                .commit();
//    }

//    public void loadTermsFragment() {
////        fragmentHead.setText(R.string.welcome);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.register_frg_container, new TermsOfServiceFragment(), "terms")
//                .addToBackStack(null)
//                .commit();
//    }

//    public void loadSignUpFragment() {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.register_frg_container, registerFragment, "signUp")
//                .addToBackStack(null)
//                .commit();
//    }
//
//    public void loadForgotPswFragment() {
//        forgotPswFragment = new ForgotPswFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.register_frg_container, forgotPswFragment, "forgotPswFragment")
//                .addToBackStack(null)
//                .commit();
//    }

    public void loadFragment(Fragment currentFragment, Bundle data, int fragmentID, boolean backStack) {
        currentFragment.setArguments(data);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();

        if (backStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(fragmentID, currentFragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
