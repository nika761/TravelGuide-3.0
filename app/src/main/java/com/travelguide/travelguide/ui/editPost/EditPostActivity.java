package com.travelguide.travelguide.ui.editPost;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.travelguide.travelguide.R;
import com.travelguide.travelguide.model.ItemMedia;
import com.travelguide.travelguide.ui.music.ChooseMusicActivity;
import com.travelguide.travelguide.ui.editPost.filterActivity.FilterActivity;
import com.travelguide.travelguide.ui.editPost.sortActivity.SortStoriesActivity;
import com.travelguide.travelguide.helper.HelperClients;
import com.travelguide.travelguide.model.request.UploadPostRequest;
import com.travelguide.travelguide.model.response.UploadPostResponse;
import com.travelguide.travelguide.ui.upload.UploadPostPresenter;
import com.gowtham.library.ui.ActVideoTrimmer;
import com.gowtham.library.utils.TrimmerConstants;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EditPostActivity extends AppCompatActivity implements EditPostListener, View.OnClickListener {

    private static final int FILTER_ACTIVITY = 1;
    private static final int SORT_ACTIVITY = 2;
    private static final int TRIM_ACTIVITY = 3;
    public static final String STORIES_PATHS = "selectedPaths";
    private ArrayList<String> storiesPath = new ArrayList<>();
    private List<ItemMedia> itemMedias = new ArrayList<>();
    private EditPostAdapter editPostAdapter;
    private int adapterPosition;
    private UploadPostPresenter uploadPostPresenter;
    private LottieAnimationView lottieAnimationView;
    private UploadPostRequest uploadPostRequest;
    private File fileForUpload;
    private RecyclerView recyclerEditStories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        initUI();
        this.storiesPath = getIntent().getStringArrayListExtra(STORIES_PATHS);

        if (storiesPath != null) {
            for (String currentPath : storiesPath) {
                if (currentPath.endsWith(".mp4")) {
                    itemMedias.add(new ItemMedia(1, currentPath));
                } else {
                    itemMedias.add(new ItemMedia(0, currentPath));
                }
            }
            initStoriesEditRecycler(itemMedias);
        }
    }

    private void initUI() {

        TextView btnNext = findViewById(R.id.edit_post_next_btn);
        btnNext.setOnClickListener(this);

        ImageView btnBack = findViewById(R.id.edit_post_back_btn);
        btnBack.setOnClickListener(this);

//        uploadPostPresenter = new UploadPostPresenter(this);

        recyclerEditStories = findViewById(R.id.recycler_post);
        lottieAnimationView = findViewById(R.id.animation_view_upload);
    }

    private void setItemsForUpload() {
        if (storiesPath != null) {
            for (String current : storiesPath) {
                if (current.endsWith(".mp4")) {
                    uploadFile(current);
//                    String videoBinary = HelperMedia.encodeVideo(current);
//                    videos.add(videoBinary);
                } else {
                    uploadFile(current);
//                    final Uri itemUri = Uri.parse(current);
//                    final InputStream imageStream;
//                    try {
//                        imageStream = getContentResolver().openInputStream(itemUri);
//                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                        String encodedImage = HelperMedia.encodeImage(selectedImage);
//                        photos.add(encodedImage);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }

            }
//            uploadPostRequest = new UploadPostRequest(17, photos, videos);
//            uploadPostPresenter.uploadStory("Bearer" + " " + HelperPref.getAccessToken(this), uploadPostRequest);

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                onCropFinish(resultCode, data);
                break;
            case FILTER_ACTIVITY:
                onFilterFinish(resultCode, data);
                break;

            case SORT_ACTIVITY:
                onSortFinish(resultCode, data);
                break;

            case TRIM_ACTIVITY:
                onTrimFinish(resultCode, data);
                break;
        }
    }

    private void initStoriesEditRecycler(List<ItemMedia> itemMedia) {
        editPostAdapter = new EditPostAdapter(this, this);
        recyclerEditStories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerEditStories.setHasFixedSize(true);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerEditStories);
        recyclerEditStories.setAdapter(editPostAdapter);
        editPostAdapter.setItemMedias(itemMedia);
    }

    @Override
    public void onCropChoose(String path, int position) {
        this.adapterPosition = position;
        CropImage.activity(Uri.fromFile(new File(path)))
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(9, 16)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @Override
    public void onFilterChoose(String path, int position) {
        this.adapterPosition = position;
        Intent i = new Intent(this, FilterActivity.class);
        i.putExtra("image_for_filter", path);
        i.putExtra("position_for_image", position);
        startActivityForResult(i, FILTER_ACTIVITY);
    }

    @Override
    public void onSortChoose(List<ItemMedia> stories) {
        this.itemMedias = stories;
        Intent intent = new Intent(this, SortStoriesActivity.class);
        intent.putExtra(STORIES_PATHS, (Serializable) itemMedias);
//        intent.putStringArrayListExtra(STORIES_PATHS, itemMedias);
        startActivityForResult(intent, SORT_ACTIVITY);
    }

    @Override
    public void onTrimChoose(String path, int position) {
        this.adapterPosition = position;
        Uri videoUri = Uri.fromFile(new File(path));
        Intent intent = new Intent(this, ActVideoTrimmer.class);
        intent.putExtra(TrimmerConstants.TRIM_VIDEO_URI, String.valueOf(videoUri));
        intent.putExtra(TrimmerConstants.TRIM_TYPE, 3);
        intent.putExtra(TrimmerConstants.MIN_FROM_DURATION, 5L);
        intent.putExtra(TrimmerConstants.MAX_TO_DURATION, 60L);
        startActivityForResult(intent, TRIM_ACTIVITY);
    }

    @Override
    public void onStoryDeleted(List<ItemMedia> itemMedias) {
        this.itemMedias = itemMedias;
    }

    public void onCropFinish(int resultCode, Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (result != null) {
            switch (resultCode) {
                case RESULT_OK:
                    String path = result.getUri().getPath();
                    editPostAdapter.onCropFinish(path, adapterPosition);
                    Toast.makeText(this, "Image Crop Successful", Toast.LENGTH_LONG).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "Image Crop Error", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    private void onFilterFinish(int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                String path = data.getStringExtra("filtered_path");
                int position = data.getIntExtra("filtered_position", 0);
                editPostAdapter.onFilterFinish(path, position);
                Toast.makeText(this, "Image Filter Successful", Toast.LENGTH_LONG).show();
                break;
            case RESULT_CANCELED:
                Toast.makeText(this, "Image Filter Error", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void onSortFinish(int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
//                this.storiesPath = data.getStringArrayListExtra(STORIES_PATHS);
                this.itemMedias = (List<ItemMedia>) data.getSerializableExtra(STORIES_PATHS);
                editPostAdapter.setItemMedias((itemMedias));
                Toast.makeText(this, "Item Sort Successful", Toast.LENGTH_LONG).show();
                break;

            case RESULT_CANCELED:
                Toast.makeText(this, "Item Sort Error", Toast.LENGTH_LONG).show();
                break;

        }
    }

    private void onTrimFinish(int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (data != null) {
                    String trimmedVideoPath = data.getStringExtra(TrimmerConstants.TRIMMED_VIDEO_PATH);
                    editPostAdapter.onTrimFinish(trimmedVideoPath, adapterPosition);
                    Toast.makeText(this, "Video Trimmed Successful", Toast.LENGTH_LONG).show();
                }
                break;

            case RESULT_CANCELED:
                Toast.makeText(this, "Video Trim Error", Toast.LENGTH_LONG).show();
                break;

        }
    }

    @Override
    public void onStoryUploaded(UploadPostResponse uploadPostResponse) {
        lottieAnimationView.setVisibility(View.GONE);
        if (uploadPostResponse.getStatus() == 0) {
            Toast.makeText(this, "uploaded", Toast.LENGTH_SHORT).show();
        } else if (uploadPostResponse.getStatus() == 1) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Some error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFileUploaded() {
        lottieAnimationView.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
        finish();
        String url = HelperClients.amazonS3Client(this).getResourceUrl(HelperClients.S3_BUCKET, fileForUpload.getName());
//        String url = HelperClients.initAmazonS3Client(getApplicationContext()).getResourceUrl("travel-guide-3", file.getName());
    }

    @Override
    public void onFileUploadError() {
        Toast.makeText(getApplicationContext(), "error while", Toast.LENGTH_SHORT).show();
    }

    private void uploadFile(String fileName) {
        fileForUpload = new File(fileName);
//        final Uri itemUri = Uri.parse(fileName);

//        createFile(getApplicationContext(), itemUri, file);
//
//        PutObjectRequest putObjectRequest = new PutObjectRequest("travel-guide-3", file.getName(), file)
//                .withCannedAcl(CannedAccessControlList.PublicRead);

//        transferUtility.upload("travel-guide-3", file.getName(), file, CannedAccessControlList.PublicRead);

//        s3Client.putObject(putObjectRequest);

        uploadPostPresenter.uploadToS3(HelperClients.transferObserver(this, fileForUpload));
//        uploadObserver.setTransferListener(new TransferListener() {
//
//            @Override
//            public void onStateChanged(int id, TransferState state) {
//                if (TransferState.COMPLETED == state) {
//                    lottieAnimationView.setVisibility(View.GONE);
//                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
//                    String url = HelperClients.initAmazonS3Client(getApplicationContext()).getResourceUrl("travel-guide-3", file.getName());
//                    Log.e("link", url);
////                    file.delete();
//                } else if (TransferState.FAILED == state) {
////                    file.delete();
//                }
//            }
//
//            @Override
//            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
//                int percentDone = (int) percentDonef;
//
////                tvFileName.setText("ID:" + id + "|bytesCurrent: " + bytesCurrent + "|bytesTotal: " + bytesTotal + "|" + percentDone + "%");
//            }
//
//            @Override
//            public void onError(int id, Exception er) {
//                Toast.makeText(getApplicationContext(), "error while", Toast.LENGTH_SHORT).show();
//                Log.e("fatalerror  ", "" + er);
//            }
//
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_post_back_btn:
                onBackPressed();

                break;
            case R.id.edit_post_next_btn:
                Intent intent = new Intent(this, ChooseMusicActivity.class);
                intent.putExtra(STORIES_PATHS, (Serializable) itemMedias);
                startActivity(intent);
                break;
        }
    }

//    private void createFile(Context context, Uri srcUri, File dstFile) {
//        try {
//            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
//            if (inputStream == null) return;
//            OutputStream outputStream = new FileOutputStream(dstFile);
//            IOUtils.copy(inputStream, outputStream);
//            inputStream.close();
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String getFileExtension(Uri uri) {
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
//    }

}
