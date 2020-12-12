package com.travel.guide.ui.upload;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.ClientManager;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.utility.GlobalPreferences;
import com.travel.guide.helper.SystemManager;
import com.travel.guide.model.ItemMedia;
import com.travel.guide.model.request.UploadPostRequest;
import com.travel.guide.ui.home.HomePageActivity;
import com.travel.guide.ui.upload.tag.HashtagAdapter;
import com.travel.guide.ui.upload.tag.TagPostActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.travel.guide.helper.HelperMedia.convertImagesToPng;
import static com.travel.guide.helper.SystemManager.WRITE_EXTERNAL_STORAGE;
import static com.travel.guide.network.ApiEndPoint.PLACES_API_KEY;
import static com.travel.guide.ui.editPost.EditPostActivity.STORIES_PATHS;
import static com.travel.guide.ui.music.ChooseMusicActivity.MUSIC_ID;

public class UploadPostActivity extends AppCompatActivity implements View.OnClickListener, UploadPostListener {

    private static final int LOCATIONS_REQUEST_CODE = 444;
    private static final int TAG_ACTIVITY_HASHTAGS = 48;
    private static final int TAG_ACTIVITY_FRIENDS = 49;

    public static final String TAG_USERS = "tag_users";
    public static final String TAG_HASHTAGS = "tag_hashtags";

    private File fileForUpload;
    private List<ItemMedia> itemMedia = new ArrayList<>();
    private List<Integer> users = new ArrayList<>();
    private List<String> hashtags = new ArrayList<>();
    private List<String> hashs = new ArrayList<>();

    private ConstraintLayout loaderContainer;
    private UploadPostPresenter uploadPostPresenter;
    private RecyclerView hashtagRecycler;
    private HashtagAdapter hashtagAdapter;
    private EditText postDescription;
    private Button uploadBtn;

    private String address, addressName, latLng, description, oldDesc, videoHeight, videoWidht;
    private int musicId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_post);
        this.musicId = getIntent().getIntExtra(MUSIC_ID, 0);
        this.itemMedia = (List<ItemMedia>) getIntent().getSerializableExtra(STORIES_PATHS);
        initUI();
    }

    private void initUI() {

        uploadPostPresenter = new UploadPostPresenter(this);

        Places.initialize(getApplicationContext(), PLACES_API_KEY);
        PlacesClient placesClient = Places.createClient(this);

        ImageButton backBtn = findViewById(R.id.describe_post_back_btn);
        backBtn.setOnClickListener(this);

        uploadBtn = findViewById(R.id.describe_post_post_btn);
        uploadBtn.setOnClickListener(this);

//        View selectCover = findViewById(R.id.select_cover_btn);
//        selectCover.setOnClickListener(this);

        TextView location = findViewById(R.id.location);
        location.setOnClickListener(this);

        ImageView imageView = findViewById(R.id.describe_story_photo);
        HelperMedia.loadPhoto(this, itemMedia.get(0).getPath(), imageView);

        postDescription = findViewById(R.id.description_post);

        hashtagRecycler = findViewById(R.id.hashtag_upload_recycler);
        hashtagRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        hashtagAdapter = new HashtagAdapter(1);
        hashtagRecycler.setAdapter(hashtagAdapter);

        loaderContainer = findViewById(R.id.loader_upload);

        TextView friends = findViewById(R.id.describe_friends);
        friends.setOnClickListener(this);

        TextView hashtag = findViewById(R.id.describe_hashtags);
        hashtag.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.describe_post_back_btn:
                finish();
                break;

            case R.id.describe_hashtags:
                Intent i = new Intent(this, TagPostActivity.class);
                i.putExtra("tag_type", TAG_HASHTAGS);
                startActivityForResult(i, TAG_ACTIVITY_HASHTAGS);
                break;

            case R.id.describe_friends:
                Intent j = new Intent(this, TagPostActivity.class);
                j.putExtra("tag_type", TAG_USERS);
                startActivityForResult(j, TAG_ACTIVITY_FRIENDS);
                break;

            case R.id.location:
                List<Place.Field> locations = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, locations).build(UploadPostActivity.this);
                startActivityForResult(intent, LOCATIONS_REQUEST_CODE);
                break;

            case R.id.describe_post_post_btn:
                loaderContainer.setVisibility(View.VISIBLE);


                if (itemMedia.get(0).getType() == 0) {
                    if (SystemManager.isWriteStoragePermission(this)) {
                        startUpload();
                    } else {
                        SystemManager.requestWriteStoragePermission(this);
                    }
                } else {
                    startUpload();
                }
//                ArrayList<String> files = new ArrayList<>();
//                for (ItemMedia list : convertedImages) {
//                    if (list.getType() == 0) {
//                        files.add(list.getPath());
//                        fileForUpload = new File(list.getPath());
//                    }
//                }
//                uploadPostPresenter = new UploadPostPresenter(this);


//                AmazonS3Client s3Client = ClientManager.amazonS3Client(this);
//                ClientManager.uploadMultipleS3(s3Client, convertedImages);
//                S3Helper s3Helper = new S3Helper();
//                s3Helper.upload(files, this);
                break;
        }
    }


    private void hashtagRecyclerVisibility(boolean isShow) {
        if (isShow) {
            hashtagRecycler.setVisibility(View.VISIBLE);
        } else {
            hashtagRecycler.setVisibility(View.GONE);
        }
    }

    public void startUpload() {
        uploadBtn.setClickable(false);
        if (itemMedia.get(0).getType() == 0) {
            List<ItemMedia> convertedImages = convertImagesToPng(itemMedia);
            fileForUpload = new File(convertedImages.get(0).getPath());
            uploadPostPresenter.uploadToS3(ClientManager.transferObserver(this, fileForUpload));
        } else {
            fileForUpload = new File(itemMedia.get(0).getPath());

            try {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(itemMedia.get(0).getPath());

                this.videoWidht = String.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                this.videoHeight = String.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));

            } catch (RuntimeException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.getMessage();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            uploadPostPresenter.uploadToS3(ClientManager.transferObserver(this, fileForUpload));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOCATIONS_REQUEST_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        if (data != null) {
                            Place place = Autocomplete.getPlaceFromIntent(data);
                            address = place.getAddress();
                            addressName = place.getName();
                            if (place.getLatLng() != null) {
                                String lat = String.valueOf(place.getLatLng().latitude);
                                String lon = String.valueOf(place.getLatLng().longitude);
                                latLng = lat + "," + lon;
                            }
                            Toast.makeText(this, addressName + " " + "added to post", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case RESULT_CANCELED:
                    case AutocompleteActivity.RESULT_ERROR:
                        if (data != null) {
                            Status status = Autocomplete.getStatusFromIntent(data);
                            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;

            case TAG_ACTIVITY_HASHTAGS:
                switch (resultCode) {
                    case RESULT_OK:
                        if (data != null) {
                            String hashtag = data.getStringExtra("hashtags");
                            this.hashtags.add(hashtag);

                            this.hashs.add(hashtag);
                            if (hashs.size() > 0) {
                                hashtagRecyclerVisibility(true);
                                hashtagAdapter.setHashs(hashs);
                            } else {
                                hashtagRecyclerVisibility(false);
                            }
                        }
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;

            case TAG_ACTIVITY_FRIENDS:
                switch (resultCode) {
                    case RESULT_OK:
                        if (data != null) {
                            int friendsId = data.getIntExtra("friend_id", 0);
                            String friendName = data.getStringExtra("friend_name");
                            this.users.add(friendsId);

                            this.hashs.add(friendName);
                            if (hashs.size() > 0) {
                                hashtagRecyclerVisibility(true);
                                hashtagAdapter.setHashs(hashs);
                            } else {
                                hashtagRecyclerVisibility(false);
                            }
                        }
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startUpload();
                } else {
                    Toast.makeText(this, "Please Permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onPostUploadedToS3() {

        List<UploadPostRequest.Post_stories> stories = new ArrayList<>();

        //Get uploaded content url from S3 server
        String url = ClientManager.amazonS3Client(this).getResourceUrl(ClientManager.S3_BUCKET, fileForUpload.getName());

        if (url.endsWith(".mp4")) {
            String videoDuration = String.valueOf(HelperMedia.getVideoDurationInt(itemMedia.get(0).getPath()));
            stories.add(new UploadPostRequest.Post_stories(url, videoDuration, 1, 1));
        } else {
            stories.add(new UploadPostRequest.Post_stories(url, "10", 1, 0));
        }

        if (postDescription.getText() != null && !postDescription.toString().isEmpty())
            description = postDescription.getText().toString();

        UploadPostRequest uploadPostRequestModel = new UploadPostRequest(stories, users, hashtags, musicId, latLng, address, addressName, description, "sometitle");

        uploadPostPresenter.uploadStory( GlobalPreferences.getAccessToken(this), uploadPostRequestModel);
//
//        UploadPostService uploadPostService = new UploadPostService();
//        uploadPostService.uploadPost(ACCESS_TOKEN_BEARER + GlobalPreferences.getAccessToken(this), uploadPostRequestModel);
//
    }

    @Override
    public void onPostUploadErrorS3(String message) {
        uploadBtn.setClickable(true);
        loaderContainer.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostUploaded() {
        loaderContainer.setVisibility(View.GONE);
        Intent intent = new Intent(UploadPostActivity.this, HomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("option", "uploaded");
        startActivity(intent);
    }

    @Override
    public void onPostUploadError(String message) {
        uploadBtn.setClickable(true);
        loaderContainer.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        if (uploadPostPresenter != null) {
            uploadPostPresenter = null;
        }
        super.onDestroy();
    }
}

