package com.travel.guide.ui.editPost;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.model.ItemMedia;
import com.travel.guide.ui.gallery.GalleryActivity;
import com.travel.guide.ui.music.ChooseMusicActivity;
import com.travel.guide.ui.editPost.filterActivity.FilterActivity;
import com.travel.guide.ui.editPost.sortActivity.SortStoriesActivity;
import com.gowtham.library.ui.ActVideoTrimmer;
import com.gowtham.library.utils.TrimmerConstants;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EditPostActivity extends AppCompatActivity implements EditPostCallback {

    public static final String STORIES_PATHS = "selectedPaths";

    private static final int FILTER_ACTIVITY = 1;
    private static final int SORT_ACTIVITY = 2;
    private static final int TRIM_ACTIVITY = 3;


    private List<ItemMedia> itemMedias = new ArrayList<>();

    private EditPostAdapter editPostAdapter;
    private RecyclerView editPostRecycler;

    private int adapterPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        initUI();
        sortData();
    }

    private void sortData() {
        ArrayList<String> storiesPath = getIntent().getStringArrayListExtra(STORIES_PATHS);

        if (storiesPath != null) {
            for (String currentPath : storiesPath) {
                if (currentPath.endsWith(".mp4")) {
                    itemMedias.add(new ItemMedia(1, currentPath));
                } else {
                    itemMedias.add(new ItemMedia(0, currentPath));
                }
            }
            if (itemMedias.size() > 0)
                initRecycler(itemMedias);
        }
    }

    private void initUI() {

        TextView btnNext = findViewById(R.id.edit_post_next_btn);
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(EditPostActivity.this, ChooseMusicActivity.class);
            intent.putExtra(STORIES_PATHS, (Serializable) itemMedias);
            startActivity(intent);
        });

        ImageView btnBack = findViewById(R.id.edit_post_back_btn);
        btnBack.setOnClickListener(v -> onBackPressed());

        editPostRecycler = findViewById(R.id.recycler_post);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(editPostRecycler);
        editPostRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        editPostRecycler.setHasFixedSize(true);
        editPostAdapter = new EditPostAdapter(this, this);

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

    private void initRecycler(List<ItemMedia> itemMedia) {
        editPostAdapter.setItemMedias(itemMedia);
        editPostRecycler.setAdapter(editPostAdapter);
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
        if (itemMedias.size() == 0) {
            finish();
        } else {
            this.itemMedias = itemMedias;
        }
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

//    private void uploadFile(String fileName) {
//        fileForUpload = new File(fileName);
////        final Uri itemUri = Uri.parse(fileName);
//
////        createFile(getApplicationContext(), itemUri, file);
////
////        PutObjectRequest putObjectRequest = new PutObjectRequest("travel-guide-3", file.getName(), file)
////                .withCannedAcl(CannedAccessControlList.PublicRead);
//
////        transferUtility.upload("travel-guide-3", file.getName(), file, CannedAccessControlList.PublicRead);
//
////        s3Client.putObject(putObjectRequest);
//
//        uploadPostPresenter.uploadToS3(HelperClients.transferObserver(this, fileForUpload));
////        uploadObserver.setTransferListener(new TransferListener() {
////
////            @Override
////            public void onStateChanged(int id, TransferState state) {
////                if (TransferState.COMPLETED == state) {
////                    lottieAnimationView.setVisibility(View.GONE);
////                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
////                    String url = HelperClients.initAmazonS3Client(getApplicationContext()).getResourceUrl("travel-guide-3", file.getName());
////                    Log.e("link", url);
//////                    file.delete();
////                } else if (TransferState.FAILED == state) {
//////                    file.delete();
////                }
////            }
////
////            @Override
////            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
////                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
////                int percentDone = (int) percentDonef;
////
//////                tvFileName.setText("ID:" + id + "|bytesCurrent: " + bytesCurrent + "|bytesTotal: " + bytesTotal + "|" + percentDone + "%");
////            }
////
////            @Override
////            public void onError(int id, Exception er) {
////                Toast.makeText(getApplicationContext(), "error while", Toast.LENGTH_SHORT).show();
////                Log.e("fatalerror  ", "" + er);
////            }
////
////        });
//    }
//
//    private void setItemsForUpload() {
//        if (storiesPath != null) {
//            for (String current : storiesPath) {
//                if (current.endsWith(".mp4")) {
//                    uploadFile(current);
////                    String videoBinary = HelperMedia.encodeVideo(current);
////                    videos.add(videoBinary);
//                } else {
//                    uploadFile(current);
////                    final Uri itemUri = Uri.parse(current);
////                    final InputStream imageStream;
////                    try {
////                        imageStream = getContentResolver().openInputStream(itemUri);
////                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
////                        String encodedImage = HelperMedia.encodeImage(selectedImage);
////                        photos.add(encodedImage);
////                    } catch (FileNotFoundException e) {
////                        e.printStackTrace();
////                    }
//                }
//
//            }
////            uploadPostRequest = new UploadPostRequest(17, photos, videos);
////            uploadPostPresenter.uploadStory("Bearer" + " " + HelperPref.getAccessToken(this), uploadPostRequest);
//
//        }
//    }
//
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
