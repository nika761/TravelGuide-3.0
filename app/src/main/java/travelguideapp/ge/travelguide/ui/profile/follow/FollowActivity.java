package travelguideapp.ge.travelguide.ui.profile.follow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.ui.home.customerUser.CustomerProfileActivity;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

import com.google.android.material.tabs.TabLayout;

public class FollowActivity extends AppCompatActivity implements FollowFragment.FollowFragmentCallbacks {

    private int customerUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        if (getIntent().getIntExtra("customer_user_id", 0) != 0) {
            this.customerUserId = getIntent().getIntExtra("customer_user_id", 0);
        }
        initUI();
        initViewPager();
    }

    private void initUI() {
        TextView userName = findViewById(R.id.follower_page_user_name);

        String name = getIntent().getStringExtra("user_name");
        if (name != null) {
            userName.setText(name);
        }

        ImageButton backBtn = findViewById(R.id.follower_page_back_btn);
        backBtn.setOnClickListener(v -> onBackPressed());

    }

    private void initViewPager() {
        TabLayout tabLayout = findViewById(R.id.follower_page_tab_layout);
        ViewPager viewPager = findViewById(R.id.follower_page_view_pager);

        FollowPagerAdapter followPagerAdapter = new FollowPagerAdapter(getSupportFragmentManager());
        followPagerAdapter.setTitles(getString(R.string.following), getString(R.string.followers));
        followPagerAdapter.setCustomerUserId(customerUserId);
        followPagerAdapter.setFollowFragmentCallback(this);
        viewPager.setAdapter(followPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_activity_slide_in_left, R.anim.anim_activity_slide_out_rigth);
    }

    @Override
    public void onChooseUser(int userId) {
        try {
            if (GlobalPreferences.getUserId(this) != userId) {
                Intent intent = new Intent(this, CustomerProfileActivity.class);
                intent.putExtra("id", userId);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_activity_slide_in_right, R.anim.anim_activity_slide_out_left);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
