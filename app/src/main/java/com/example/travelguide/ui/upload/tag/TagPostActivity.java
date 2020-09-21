package com.example.travelguide.ui.upload.tag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.HashtagRequest;
import com.example.travelguide.model.request.SearchMusicRequest;
import com.example.travelguide.model.response.HashtagResponse;
import com.googlecode.mp4parser.h264.BTree;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;
import static com.example.travelguide.ui.upload.UploadPostActivity.TAG_HASHTAGS;
import static com.example.travelguide.ui.upload.UploadPostActivity.TAG_USERS;

public class TagPostActivity extends AppCompatActivity implements TagPostListener, View.OnClickListener {

    private EditText searchEditTxt;
    private RecyclerView recyclerView;
    private TagPostPresenter postPresenter;
    private HashtagAdapter hashtagAdapter;
    private String type;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tags);
        this.type = getIntent().getStringExtra("tag_type");
        if (type != null)
            switch (type) {
                case TAG_USERS:
                    title.setText("Tag Friends");
                    break;

                case TAG_HASHTAGS:
                    title.setText("Hashtags");
                    break;
            }
        initUI();
    }

    private void initUI() {

        postPresenter = new TagPostPresenter(this);

        ImageButton backBtn = findViewById(R.id.tag_post_back_btn);
        backBtn.setOnClickListener(this);

        TextView searchBtn = findViewById(R.id.tag_post_search_btn);
        searchBtn.setOnClickListener(this);

        TextView doneBtn = findViewById(R.id.tag_post_done_btn);
        doneBtn.setOnClickListener(this);
        doneBtn.setVisibility(View.INVISIBLE);

        searchEditTxt = findViewById(R.id.tag_post_search);

        title = findViewById(R.id.tag_post_title);

        recyclerView = findViewById(R.id.tag_post_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RxTextView.textChanges(searchEditTxt)
                .debounce(1200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                //CharSequence is converted to String
                .map(CharSequence::toString)
                .subscribe((Consumer<CharSequence>) charSequence -> {
                    if (!charSequence.toString().isEmpty()) {
                        postPresenter.getHashtags(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(this), new HashtagRequest(charSequence.toString()));
                    }
                });
    }

    @Override
    public void onGetHashtags(List<HashtagResponse.Hashtags> hashtags) {
        hashtagAdapter = new HashtagAdapter(this);
        recyclerView.setAdapter(hashtagAdapter);
        hashtagAdapter.setHashtags(hashtags);
    }

    @Override
    public void onGetHashtagsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChooseHashtag(String hashtag) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("hashtags", hashtag);
        setResult(Activity.RESULT_OK, resultIntent);
        Toast.makeText(this, "Hashtag added" + hashtag, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tag_post_back_btn:
                finish();
                break;
            case R.id.tag_post_search_btn:
                postPresenter.getHashtags(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(this), new HashtagRequest(searchEditTxt.getText().toString()));
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
