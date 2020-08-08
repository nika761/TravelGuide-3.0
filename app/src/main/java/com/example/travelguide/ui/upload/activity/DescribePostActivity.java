package com.example.travelguide.ui.upload.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.s3.transfermanager.MultipleFileUpload;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.UploadObjectObserver;
import com.bumptech.glide.Glide;
import com.example.travelguide.R;
import com.example.travelguide.helper.HelperClients;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.ItemMedia;
import com.example.travelguide.ui.upload.interfaces.IUploadPostListener;
import com.example.travelguide.ui.upload.presenter.UploadPostPresenter;
import com.google.android.gms.common.api.Status;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.travelguide.ui.upload.activity.EditPostActivity.STORIES_PATHS;

public class DescribePostActivity extends AppCompatActivity implements View.OnClickListener, IUploadPostListener {
    private ImageView imageView;
    private List<ItemMedia> itemMedia = new ArrayList<>();
    private UploadPostPresenter uploadPostPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_post);
        this.itemMedia = (List<ItemMedia>) getIntent().getSerializableExtra(STORIES_PATHS);
        initUI();
    }

    private void initUI() {
        ImageButton backBtn = findViewById(R.id.describe_post_back_btn);
        backBtn.setOnClickListener(this);

        Button post = findViewById(R.id.describe_post_post_btn);
        post.setOnClickListener(this);

        View selectCover = findViewById(R.id.select_cover_btn);
        selectCover.setOnClickListener(this);

        TextView location = findViewById(R.id.location);
        location.setOnClickListener(this);

        imageView = findViewById(R.id.describe_story_photo);
        HelperMedia.loadPhoto(this, itemMedia.get(0).getPath(), imageView);
    }

//    private void initStoriesDescribeRecycler(ArrayList<String> storiesPath) {
//        RecyclerView recyclerDescribeStories = findViewById(R.id.describe_post_recycler);
//        DescribePostAdapter adapter = new DescribePostAdapter(storiesPath);
//        recyclerDescribeStories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        recyclerDescribeStories.setHasFixedSize(true);
//        recyclerDescribeStories.setAdapter(adapter);
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.describe_post_back_btn:
                finish();
                break;

            case R.id.select_cover_btn:

                break;

            case R.id.location:

//                AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                        getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//
//                // Specify the types of place data to return.
//                assert autocompleteFragment != null;
//                autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//
//                // Set up a PlaceSelectionListener to handle the response.
//                autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//                    @Override
//                    public void onPlaceSelected(@NotNull Place place) {
//                        // TODO: Get info about the selected place.
//                        Log.i("zz", "Place: " + place.getName() + ", " + place.getId());
//                    }
//
//
//                    @Override
//                    public void onError(@NotNull Status status) {
//                        // TODO: Handle the error.
//                        Log.i("cc", "An error occurred: " + status);
//                    }
//                });
                break;

            case R.id.describe_post_post_btn:
                List<ItemMedia> convertedImages = convertImagesToPng(itemMedia);
                uploadPostPresenter = new UploadPostPresenter(this);
                AmazonS3Client s3Client = HelperClients.amazonS3Client(this);
                HelperClients.uploadMultipleS3(s3Client, convertedImages);
                break;
        }
    }

    public List<ItemMedia> convertImagesToPng(List<ItemMedia> itemMedia) {

        List<ItemMedia> convertedImages = new ArrayList<>();

        for (int i = 0; i < itemMedia.size(); i++) {
            if (itemMedia.get(i).getType() == 0) {
                try {
                    Bitmap bmp = BitmapFactory.decodeFile(itemMedia.get(i).getPath());

                    File imageFileJGP = new File(itemMedia.get(i).getPath());

                    File imageFilePNG = new File(imageFileJGP.getParent() + "/" + System.currentTimeMillis() + ".png");

                    FileOutputStream out = new FileOutputStream(imageFilePNG);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, out); //100-best quality
                    out.flush();
                    out.close();
                    convertedImages.add(new ItemMedia(0, imageFilePNG.getAbsolutePath()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return convertedImages;
    }

    @Override
    public void onPostUploaded() {
        Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostUploadError() {
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        if (uploadPostPresenter != null) {
            uploadPostPresenter = null;
        }
        super.onDestroy();
    }
}

