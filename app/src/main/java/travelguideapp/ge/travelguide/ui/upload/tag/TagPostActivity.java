package travelguideapp.ge.travelguide.ui.upload.tag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.SearchFollowersRequest;
import travelguideapp.ge.travelguide.model.request.SearchHashtagRequest;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;

import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

import static travelguideapp.ge.travelguide.ui.upload.UploadPostActivity.TAG_TYPE_HASHTAGS;
import static travelguideapp.ge.travelguide.ui.upload.UploadPostActivity.TAG_TYPE_USERS;

public class TagPostActivity extends AppCompatActivity implements TagPostListener, View.OnClickListener {

    private EditText searchEditTxt;
    private TextView searchBtn;
    private RecyclerView recyclerView;
    private TagPostPresenter postPresenter;
    private LottieAnimationView loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tags);
        initUI();
    }

    private void initUI() {

        postPresenter = new TagPostPresenter(this);

        loader = findViewById(R.id.loader_tags);

        ImageButton backBtn = findViewById(R.id.tag_post_back_btn);
        backBtn.setOnClickListener(this);

        searchBtn = findViewById(R.id.tag_post_search_btn);
        searchBtn.setOnClickListener(this);

        TextView doneBtn = findViewById(R.id.tag_post_done_btn);
        doneBtn.setOnClickListener(this);
        doneBtn.setVisibility(View.INVISIBLE);

        searchEditTxt = findViewById(R.id.tag_post_search);
        TextView title = findViewById(R.id.tag_post_title);

        recyclerView = findViewById(R.id.tag_post_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String type = getIntent().getStringExtra("tag_type");
        if (type != null)
            switch (type) {
                case TAG_TYPE_USERS:
                    title.setText("Tag Friends");
                    RxTextView.textChanges(searchEditTxt)
                            .debounce(1200, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(CharSequence::toString)
                            .subscribe((Consumer<CharSequence>) charSequence -> {
                                if (!charSequence.toString().isEmpty()) {
                                    loader.setVisibility(View.VISIBLE);
                                    postPresenter.searchFollowers(GlobalPreferences.getAccessToken(this), new SearchFollowersRequest(charSequence.toString()));
                                }
                            });
                    break;

                case TAG_TYPE_HASHTAGS:
                    title.setText("Hashtags");
                    RxTextView.textChanges(searchEditTxt)
                            .debounce(1200, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(CharSequence::toString)
                            .subscribe((Consumer<CharSequence>) charSequence -> {
                                if (!charSequence.toString().isEmpty()) {
                                    loader.setVisibility(View.VISIBLE);
                                    postPresenter.getHashtags(GlobalPreferences.getAccessToken(this), new SearchHashtagRequest(charSequence.toString()));
                                }
                            });
                    break;
            }

    }

    @Override
    public void onGetHashtags(List<HashtagResponse.Hashtags> hashtags) {
        try {
            loader.setVisibility(View.GONE);
            if (hashtags == null || hashtags.size() == 0) {
                searchBtn.setText("Add Hashtag");
            } else {
                searchBtn.setText(getString(R.string.search));
                AddTagAdapter addTagAdapter = new AddTagAdapter(this);
                addTagAdapter.setHashtags(hashtags);
                recyclerView.setAdapter(addTagAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void onGetError(String message) {
        MyToaster.getToast(this, message);
        loader.setVisibility(View.GONE);
    }

    @Override
    public void onChooseHashtag(String hashtag) {
        Intent m = new Intent();
        m.putExtra("hashtags", hashtag);
        setResult(Activity.RESULT_OK, m);
        finish();
    }

    @Override
    public void onGetFollowers(List<FollowerResponse.Followers> followers) {
        try {
            FriendsAdapter friendsAdapter = new FriendsAdapter(this);
            recyclerView.setAdapter(friendsAdapter);
            friendsAdapter.setFollowers(followers);
            loader.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void onChooseFollower(int followerId, String name) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("friend_id", followerId);
        resultIntent.putExtra("friend_name", name);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tag_post_back_btn:
                finish();
                break;

//            case R.id.tag_post_search_btn:
//                postPresenter.getHashtags(GlobalPreferences.getAccessToken(this), new SearchHashtagRequest(searchEditTxt.getText().toString()));
//                break;

            case R.id.tag_post_search_btn:
                try {
                    Intent m = new Intent();
                    String created = "#" + searchEditTxt.getText().toString();
                    m.putExtra("hashtags", created);
                    setResult(Activity.RESULT_OK, m);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
                break;

//            case R.id.tag_post_done_btn:
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("hashtags", (Serializable) hashtags);
//                setResult(Activity.RESULT_OK, resultIntent);
//                finish();
//                break;
        }
    }
}
