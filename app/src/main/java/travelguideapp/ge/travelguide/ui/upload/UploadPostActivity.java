package travelguideapp.ge.travelguide.ui.upload;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.ClientManager;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.model.ItemMedia;
import travelguideapp.ge.travelguide.model.request.UploadPostRequest;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import travelguideapp.ge.travelguide.ui.upload.tag.HashtagAdapter;
import travelguideapp.ge.travelguide.ui.upload.tag.TagPostActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import travelguideapp.ge.travelguide.network.ApiEndPoint;
import travelguideapp.ge.travelguide.ui.editPost.EditPostActivity;

import static travelguideapp.ge.travelguide.ui.music.ChooseMusicActivity.MUSIC_ID;

public class UploadPostActivity extends AppCompatActivity implements UploadPostListener {

    private static final int TAG_LOCATION_REQUEST_CODE = 444;
    private static final int TAG_REQUEST_CODE_HASHTAGS = 48;
    private static final int TAG_REQUEST_CODE_FRIENDS = 49;

    public static final String TAG_TYPE_USERS = "tag_users";
    public static final String TAG_TYPE_HASHTAGS = "tag_hashtags";

    private File fileForUpload;
    private List<ItemMedia> itemMedia = new ArrayList<>();
    private List<Integer> users = new ArrayList<>();
    private List<String> hashtags = new ArrayList<>();
    private List<String> hashs = new ArrayList<>();

    private LottieAnimationView loader;
    private FrameLayout loaderContainer;
    private UploadPostPresenter uploadPostPresenter;
    private RecyclerView hashtagRecycler;
    private HashtagAdapter hashtagAdapter;
    private EditText postDescription;
    private Button uploadBtn;
    private ImageButton removeLocation;
    private TextView choosedLocation;

    private String address, addressName, latLng, description, videoHeight, videoWidht;
    private int musicId;

    private boolean hasPermisPsion = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_post);
        getExtras();
        initUI();
        initHashtagRecycler();
    }

    private void getExtras() {
        try {
            this.musicId = getIntent().getIntExtra(MUSIC_ID, 0);
            this.itemMedia = (List<ItemMedia>) getIntent().getSerializableExtra(EditPostActivity.STORIES_PATHS);

        } catch (Exception e) {
            onBackPressed();
            e.printStackTrace();
        }
    }

    private void initUI() {

        uploadPostPresenter = new UploadPostPresenter(this);

        Places.initialize(getApplicationContext(), ApiEndPoint.PLACES_API_KEY);
//        PlacesClient placesClient = Places.createClient(this);

        ImageButton backBtn = findViewById(R.id.describe_post_back_btn);
        backBtn.setOnClickListener(v -> finish());

        removeLocation = findViewById(R.id.remove_choosed_location);
        removeLocation.setOnClickListener(v -> clearLocation());

        uploadBtn = findViewById(R.id.describe_post_post_btn);
        uploadBtn.setOnClickListener(v -> getReadyForUpload());

        postDescription = findViewById(R.id.description_post);
        loaderContainer = findViewById(R.id.loader_upload);
        loader = findViewById(R.id.loader_upload_post);

        choosedLocation = findViewById(R.id.choosed_location);

        ImageView imageView = findViewById(R.id.describe_story_photo);
        HelperMedia.loadPhoto(this, itemMedia.get(0).getPath(), imageView);

        TextView location = findViewById(R.id.location);
        location.setOnClickListener(v -> onStartAddLocation());

        TextView friends = findViewById(R.id.describe_friends);
        friends.setOnClickListener(v -> onStartAddTags(TAG_TYPE_USERS, TAG_REQUEST_CODE_FRIENDS));

        TextView hashtag = findViewById(R.id.describe_hashtags);
        hashtag.setOnClickListener(v -> onStartAddTags(TAG_TYPE_HASHTAGS, TAG_REQUEST_CODE_HASHTAGS));

    }

    private void clearLocation() {
        YoYo.with(Techniques.FadeOut).duration(150).onEnd(animator -> {
            choosedLocation.setVisibility(View.GONE);
            removeLocation.setVisibility(View.GONE);
        }).playOn(choosedLocation);
        latLng = null;
        choosedLocation.setText("");
    }

    private void initHashtagRecycler() {
        hashtagRecycler = findViewById(R.id.hashtag_upload_recycler);
        hashtagRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        hashtagAdapter = new HashtagAdapter(1);
        hashtagRecycler.setAdapter(hashtagAdapter);
    }

    private void onStartAddTags(String tagType, int requestCode) {
        Intent intent = new Intent(this, TagPostActivity.class);
        intent.putExtra("tag_type", tagType);
        startActivityForResult(intent, requestCode);
    }

    private void onStartAddLocation() {
        List<Place.Field> locations = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, locations).build(UploadPostActivity.this);
        startActivityForResult(intent, TAG_LOCATION_REQUEST_CODE);
    }

    private void getLoader(boolean show) {
        if (show) {
            loader.setVisibility(View.VISIBLE);
            loaderContainer.setVisibility(View.VISIBLE);
            uploadBtn.setClickable(false);
        } else {
            loader.setVisibility(View.GONE);
            loaderContainer.setVisibility(View.GONE);
            uploadBtn.setClickable(true);
        }
    }

    private void getReadyForUpload() {
        getLoader(true);
        try {
            if (itemMedia.get(0).getType() == 0 && !SystemManager.isWriteStoragePermission(this)) {
                getLoader(false);
                SystemManager.requestWriteStoragePermission(this);
            } else {
                startUpload();
            }
        } catch (Exception e) {
            getLoader(false);
            e.printStackTrace();
        }
    }

//    @Override
//    public void onClick(View v) {
////                ArrayList<String> files = new ArrayList<>();
////                for (ItemMedia list : convertedImages) {
////                    if (list.getType() == 0) {
////                        files.add(list.getPath());
////                        fileForUpload = new File(list.getPath());
////                    }
////                }
////                uploadPostPresenter = new UploadPostPresenter(this);
////                AmazonS3Client s3Client = ClientManager.amazonS3Client(this);
////                ClientManager.uploadMultipleS3(s3Client, convertedImages);
////                S3Helper s3Helper = new S3Helper();
////                s3Helper.upload(files, this);
//    }

    private void setTagRecycler(boolean isShow) {
        if (isShow) {
            hashtagRecycler.setVisibility(View.VISIBLE);
        } else {
            hashtagRecycler.setVisibility(View.GONE);
        }
    }

    public void startUpload() {
        try {
            if (itemMedia.get(0).getType() == 0) {
                List<ItemMedia> convertedImages = HelperMedia.convertImagesToPng(itemMedia);
                fileForUpload = new File(convertedImages.get(0).getPath());
            } else {
                fileForUpload = new File(itemMedia.get(0).getPath());
//            try {
//                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//                retriever.setDataSource(itemMedia.get(0).getPath());
//
//                this.videoWidht = String.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
//                this.videoHeight = String.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
//
//            } catch (RuntimeException e) {
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                e.getMessage();
//            } catch (Exception e) {
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
            }
            uploadPostPresenter.uploadToS3(ClientManager.transferObserver(this, fileForUpload));
        } catch (Exception e) {
            e.printStackTrace();
            getLoader(false);
            try {
                MyToaster.getErrorToaster(this, e.getMessage());
                MyToaster.getErrorToaster(this, e.getLocalizedMessage());
            } catch (Exception b) {
                b.printStackTrace();
            }
            finish();
        }

    }

    private void onGetAddLocationResult(Intent data, int resultCode) {
        if (data != null) {
            switch (resultCode) {
                case RESULT_OK:
                    try {
                        Place place = Autocomplete.getPlaceFromIntent(data);
                        address = place.getAddress();
                        addressName = place.getName();
                        YoYo.with(Techniques.FadeIn).duration(600).onEnd(animator -> {
                            choosedLocation.setVisibility(View.VISIBLE);
                            removeLocation.setVisibility(View.VISIBLE);
                            choosedLocation.setText(addressName);
                        }).playOn(choosedLocation);

                        if (place.getLatLng() != null) {
                            String lat = String.valueOf(place.getLatLng().latitude);
                            String lon = String.valueOf(place.getLatLng().longitude);
                            latLng = lat + "," + lon;
//                            Toast.makeText(this, addressName + " " + "added to post", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case RESULT_CANCELED:
                case AutocompleteActivity.RESULT_ERROR:
                    Status status = Autocomplete.getStatusFromIntent(data);
                    MyToaster.getErrorToaster(this, status.getStatusMessage());
                    break;
            }
        }
    }

    private void onGetAddHashtagResult(Intent data, int resultCode) {
        if (data != null) {
            switch (resultCode) {
                case RESULT_OK:
                    try {
                        String hashtag = data.getStringExtra("hashtags");
                        this.hashtags.add(hashtag);

                        this.hashs.add(hashtag);
                        if (hashs.size() > 0) {
                            setTagRecycler(true);
                            hashtagAdapter.setHashs(hashs);
                        } else {
                            setTagRecycler(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case RESULT_CANCELED:
                    break;
            }
        }
    }

    private void onGetAddFriendsResult(Intent data, int resultCode) {
        if (data != null) {
            switch (resultCode) {
                case RESULT_OK:
                    try {
                        int friendsId = data.getIntExtra("friend_id", 0);
                        String friendName = data.getStringExtra("friend_name");

                        this.users.add(friendsId);
                        this.hashs.add(friendName);

                        if (hashs.size() > 0) {
                            setTagRecycler(true);
                            hashtagAdapter.setHashs(hashs);
                        } else {
                            setTagRecycler(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case RESULT_CANCELED:
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAG_LOCATION_REQUEST_CODE:
                onGetAddLocationResult(data, resultCode);
                break;

            case TAG_REQUEST_CODE_HASHTAGS:
                onGetAddHashtagResult(data, resultCode);
                break;

            case TAG_REQUEST_CODE_FRIENDS:
                onGetAddFriendsResult(data, resultCode);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SystemManager.WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLoader(true);
                startUpload();
            } else {
                MyToaster.getErrorToaster(this, "Without Permission You Can't Upload Photo");
            }
        }
    }

    @Override
    public void onPostUploadedToS3() {

        try {

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

            uploadPostPresenter.uploadStory(GlobalPreferences.getAccessToken(this), uploadPostRequestModel);

        } catch (Exception e) {
            e.printStackTrace();
            getLoader(false);
            try {
                MyToaster.getErrorToaster(this, e.getMessage());
                MyToaster.getErrorToaster(this, e.getLocalizedMessage());
            } catch (Exception b) {
                b.printStackTrace();
            }
            finish();
        }

    }

    @Override
    public void onPostUploaded() {
        try {
            getLoader(false);
            Intent intent = new Intent(UploadPostActivity.this, HomePageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("option", "uploaded");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPostUploadError(String message) {
        getLoader(false);
        MyToaster.getErrorToaster(this, message);
    }

    @Override
    protected void onDestroy() {
        if (uploadPostPresenter != null) {
            uploadPostPresenter = null;
        }
        super.onDestroy();
    }
}

