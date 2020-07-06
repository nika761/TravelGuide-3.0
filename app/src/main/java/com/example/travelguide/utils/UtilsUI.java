package com.example.travelguide.utils;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class UtilsUI {

    public static void loadFragment(Fragment currentFragment, Bundle data, int fragmentID,
                                    boolean backStack, FragmentActivity fragmentActivity) {
        currentFragment.setArguments(data);
        FragmentTransaction fragmentTransaction = fragmentActivity
                .getSupportFragmentManager()
                .beginTransaction();

        if (backStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction
                .replace(fragmentID, currentFragment)
                .commit();
    }

}
