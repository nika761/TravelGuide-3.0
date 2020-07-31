package com.example.travelguide.ui.upload.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.ItemMedia;
import com.example.travelguide.ui.upload.adapter.recycler.EditPostAdapter;
import com.example.travelguide.helper.HelperClients;
import com.example.travelguide.ui.upload.interfaces.IEditPost;
import com.example.travelguide.model.request.UploadPostRequestModel;
import com.example.travelguide.model.response.UploadPostResponseModel;
import com.example.travelguide.ui.upload.presenter.UploadPostPresenter;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EditPostActivity extends AppCompatActivity implements IEditPost, View.OnClickListener {
    private static final int FILTER_ACTIVITY = 1;
    private static final int SORT_ACTIVITY = 2;
    public static final String STORIES_PATHS = "selectedPaths";
    private ArrayList<String> storiesPath = new ArrayList<>();
    private List<ItemMedia> itemMedias = new ArrayList<>();
    private EditPostAdapter editPostAdapter;
    private int adapterPosition;
    private UploadPostPresenter uploadPostPresenter;
    private LottieAnimationView lottieAnimationView;
    private UploadPostRequestModel uploadPostRequestModel;
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

        uploadPostPresenter = new UploadPostPresenter(this);

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
//            uploadPostRequestModel = new UploadPostRequestModel(17, photos, videos);
//            uploadPostPresenter.uploadStory("Bearer" + " " + HelperPref.getCurrentAccessToken(this), uploadPostRequestModel);

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
    public void onTrimChoose(String path) {
        Uri uri = Uri.fromFile(new File(path));

        Intent trimVideoIntent = new Intent("com.android.camera.action.TRIM");

// The key for the extra has been discovered from com.android.gallery3d.app.PhotoPage.KEY_MEDIA_ITEM_PATH
        trimVideoIntent.putExtra("media-item-path", HelperMedia.getFilePathFromVideoURI(this, uri));
        trimVideoIntent.setData(uri);
// Check if the device can handle the Intent
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(trimVideoIntent, 0);
        if (null != list && list.size() > 0) {
            startActivity(trimVideoIntent); // Fires TrimVideo activity into being active
        }
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

    @Override
    public void onStoryUploaded(UploadPostResponseModel uploadPostResponseModel) {
        lottieAnimationView.setVisibility(View.GONE);
        if (uploadPostResponseModel.getStatus() == 0) {
            Toast.makeText(this, "uploaded", Toast.LENGTH_SHORT).show();
        } else if (uploadPostResponseModel.getStatus() == 1) {
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
        String url = HelperClients.amazonS3Client(this).getResourceUrl(HelperClients.BUCKET, fileForUpload.getName());
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

////        s3Client.putObject(putObjectRequest);
//        TransferManager tm = new TransferManager(credentials);
//        MultipleFileUpload upload = tm.uploadFileList(myBucket, myKeyPrefix, rootDirectory, fileList);
        uploadPostPresenter.uploadToS3(HelperClients.uploadObserver(this, fileForUpload));
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
