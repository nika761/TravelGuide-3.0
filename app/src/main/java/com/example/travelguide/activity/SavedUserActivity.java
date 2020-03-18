package com.example.travelguide.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.adapter.UserListAdapter;
import com.example.travelguide.fragments.ForgotPswFragment;
import com.example.travelguide.fragments.SavedUsersFragment;
import com.example.travelguide.utils.Utils;

public class SavedUserActivity extends AppCompatActivity {

    private SavedUsersFragment savedUsersFragment;
    private ForgotPswFragment forgotPswFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_user);
        loadSaveUserFragment();
    }

    public void loadSaveUserFragment() {
        if (savedUsersFragment == null) {
            savedUsersFragment = new SavedUsersFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.animation_fragment_slide_down, R.anim.animation_fragment_slide_down)
                    .add(R.id.saved_user_fragment, savedUsersFragment, "savedUserFragment")
                    .commit();
        }
    }

    public void loadForgotPswFragment() {
        forgotPswFragment = new ForgotPswFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.saved_user_fragment, forgotPswFragment, "forgotPswFragment")
                .addToBackStack(null)
                .commit();
    }
}
