package travelguideapp.ge.travelguide.ui.upload.tag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.SearchFollowersRequest;
import travelguideapp.ge.travelguide.model.request.SearchHashtagRequest;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;

import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

import static travelguideapp.ge.travelguide.ui.upload.UploadPostActivity.TAG_TYPE_REQUEST;
import static travelguideapp.ge.travelguide.ui.upload.UploadPostActivity.TAG_TYPE_HASHTAGS;
import static travelguideapp.ge.travelguide.ui.upload.UploadPostActivity.TAG_TYPE_USERS;

public class TagPostActivity extends BaseActivity implements TagPostListener {

    private EditText searchEditTxt;
    private TextView searchBtn, title;
    private RecyclerView tagRecycler;
    private LinearLayout nothingFound;
    private TagPostPresenter postPresenter;
    private LottieAnimationView loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tags);
        postPresenter = new TagPostPresenter(this);
        initUI();
    }

    private void initUI() {
        ImageButton backBtn = findViewById(R.id.tag_post_back_btn);
        backBtn.setOnClickListener(v -> finish());

        tagRecycler = findViewById(R.id.tag_post_recycler);
        tagRecycler.setLayoutManager(new LinearLayoutManager(this));

        loader = findViewById(R.id.loader_tags);
        title = findViewById(R.id.tag_post_title);
        nothingFound = findViewById(R.id.nothing_found_tag_friends);

        searchBtn = findViewById(R.id.tag_post_search_btn);
        searchBtn.setOnClickListener(v -> searchBtnClickAction());

        searchEditTxt = findViewById(R.id.tag_post_search);
        setSearchFieldOptions();

        getTagType();
    }

    private void setSearchFieldOptions() {
        try {
            InputFilter[] inputFilters = new InputFilter[1];
            inputFilters[0] = new InputFilter.LengthFilter(GlobalPreferences.getAppSettings(this).getHashtag_lenght());
            searchEditTxt.setFilters(inputFilters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchEditTxt.setLongClickable(false);
        searchEditTxt.setTextIsSelectable(false);
    }

    public void setNothingFound() {
        tagRecycler.setVisibility(View.GONE);
        nothingFound.setVisibility(View.VISIBLE);
    }

    private void getTagType() {

        String type = getIntent().getStringExtra(TAG_TYPE_REQUEST);

        if (type != null)
            switch (type) {
                case TAG_TYPE_USERS:
                    title.setText(getString(R.string.tag_friends));
                    RxTextView.textChanges(searchEditTxt)
                            .debounce(600, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(CharSequence::toString)
                            .subscribe((Consumer<CharSequence>) charSequence -> {
                                if (!charSequence.toString().isEmpty()) {
                                    loader.setVisibility(View.VISIBLE);
                                    postPresenter.searchFollowers( new SearchFollowersRequest(charSequence.toString()));
                                }
                            });
                    break;

                case TAG_TYPE_HASHTAGS:
                    title.setText(getString(R.string.hashtags));
                    RxTextView.textChanges(searchEditTxt)
                            .debounce(600, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(CharSequence::toString)
                            .subscribe((Consumer<CharSequence>) charSequence -> {
                                if (!charSequence.toString().isEmpty()) {
                                    loader.setVisibility(View.VISIBLE);
                                    postPresenter.getHashtags( new SearchHashtagRequest(charSequence.toString()));
                                }
                            });
                    break;
            }

    }

    private void searchBtnClickAction() {
        try {

            if (searchEditTxt.getText().toString() == null || searchEditTxt.getText().toString().isEmpty()) {
                return;
            }

            if (searchEditTxt.getText().toString().length() > GlobalPreferences.getAppSettings(this).getHashtag_lenght()) {
                searchEditTxt.getText().clear();
                return;
            }

            String created;
            if (searchEditTxt.getText().toString().startsWith("#")) {
                created = searchEditTxt.getText().toString();
            } else {
                created = "#" + searchEditTxt.getText().toString();
            }

            if (checkHashtag(created)) {
                Intent mIntent = new Intent();
                mIntent.putExtra("hashtags", created);
                setResult(Activity.RESULT_OK, mIntent);
            } else {
                String current = created.replace("#", "");
                if (current.length() > 0) {
                    String ready = "#" + current;
                    Intent mIntent = new Intent();
                    mIntent.putExtra("hashtags", ready);
                    setResult(Activity.RESULT_OK, mIntent);
                } else {
                    setResult(Activity.RESULT_CANCELED);
                }
            }
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void onGetHashtags(List<HashtagResponse.Hashtags> hashtags) {
        try {
            loader.setVisibility(View.GONE);
            if (hashtags == null || hashtags.size() == 0) {
                searchBtn.setText(getString(R.string.add_hashtag));
            } else {
                searchBtn.setText(getString(R.string.search));
                AddTagAdapter addTagAdapter = new AddTagAdapter(this);
                addTagAdapter.setHashtags(hashtags);
                tagRecycler.setAdapter(addTagAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void onGetError(String message) {
        loader.setVisibility(View.GONE);
        MyToaster.getToast(this, message);
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
            loader.setVisibility(View.GONE);

            if (followers.size() == 0) {
                setNothingFound();
                return;
            }

            FriendsAdapter friendsAdapter = new FriendsAdapter(this);
            nothingFound.setVisibility(View.GONE);
            tagRecycler.setVisibility(View.VISIBLE);
            tagRecycler.setAdapter(friendsAdapter);
            friendsAdapter.setFollowers(followers);
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

    public static void isHashtag(String hashtag) {
        String input = "My name is #George and I like #Java.";
        Pattern patt = Pattern.compile("(#\\w+)\\b");
        Matcher match = patt.matcher(input);
        List<String> matStr = new ArrayList<>();
        while (match.find()) {
            matStr.add(match.group(1));
        }
    }

    private boolean checkHashtag(String hashtag) {
        Pattern patt = Pattern.compile("(#\\w+)\\b");
        Matcher match = patt.matcher(hashtag);
        return match.matches();
    }

}
