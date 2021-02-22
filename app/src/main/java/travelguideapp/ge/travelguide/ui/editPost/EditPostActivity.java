package travelguideapp.ge.travelguide.ui.editPost;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.model.customModel.ItemMedia;
import travelguideapp.ge.travelguide.model.parcelable.MediaFileData;
import travelguideapp.ge.travelguide.ui.music.ChooseMusicActivity;
import travelguideapp.ge.travelguide.ui.editPost.filterActivity.FilterActivity;
import travelguideapp.ge.travelguide.ui.editPost.sortActivity.SortStoriesActivity;

import com.gowtham.library.ui.ActVideoTrimmer;
import com.gowtham.library.utils.TrimmerConstants;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import travelguideapp.ge.travelguide.base.BaseApplication;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;


public class EditPostActivity extends BaseActivity implements EditPostCallback {

    public static final String STORIES_PATHS = "selectedPaths";

    private static final int FILTER_ACTIVITY = 1;
    private static final int SORT_ACTIVITY = 2;
    private static final int TRIM_ACTIVITY = 3;

    private List<ItemMedia> itemMedias = new ArrayList<>();
    private List<MediaFileData> mediaFiles = new ArrayList<>();

    private EditPostAdapter editPostAdapter;
    private RecyclerView editPostRecycler;

    private int adapterPosition;

    private boolean isMustTrim = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        initUI();
        sortData();
        checkVideoDuration();
    }

    private void sortData() {
        ArrayList<String> storiesPath = getIntent().getStringArrayListExtra(STORIES_PATHS);

        if (storiesPath != null) {
            try {
                for (String currentPath : storiesPath) {
                    if (currentPath.endsWith(".mp4")) {
                        mediaFiles.add(new MediaFileData(currentPath, MediaFileData.MediaType.VIDEO));
                    } else {
                        mediaFiles.add(new MediaFileData(currentPath, MediaFileData.MediaType.PHOTO));
                    }
                }
                if (mediaFiles.size() > 0)
                    initRecycler(mediaFiles);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void checkVideoDuration() {
        try {
            if (mediaFiles.size() > 0 && mediaFiles.get(0).getMediaType() == MediaFileData.MediaType.VIDEO) {
                long duration = HelperMedia.getVideoDurationLong(mediaFiles.get(0).getMediaPath());
                if (duration > GlobalPreferences.getAppSettings(this).getStory_video_duration_max()) {
                    isMustTrim = true;
                    onTrimChoose(mediaFiles.get(0).getMediaPath(), 0);
                } else {
                    Log.e("dasdasd", "naklebia");
                    Log.e("dasdasd", String.valueOf(duration));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {

        TextView btnNext = findViewById(R.id.edit_post_next_btn);
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(EditPostActivity.this, ChooseMusicActivity.class);
            intent.putParcelableArrayListExtra(MediaFileData.INTENT_KEY_MEDIA, (ArrayList<? extends Parcelable>) mediaFiles);
            startActivity(intent);
        });

        ImageView btnBack = findViewById(R.id.edit_post_back_btn);
        btnBack.setOnClickListener(v -> onBackPressed());

        editPostRecycler = findViewById(R.id.recycler_post);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(editPostRecycler);
        editPostRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        editPostRecycler.setHasFixedSize(true);
        editPostAdapter = new EditPostAdapter(this);

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

    private void initRecycler(List<MediaFileData> mediaFiles) {
        editPostAdapter.setItemMedias(mediaFiles);
        editPostRecycler.setAdapter(editPostAdapter);
    }

    @Override
    public void onCropChoose(String path, int position) {
        this.adapterPosition = position;

        if (GlobalPreferences.getAppSettings(this).getCROP_OPTION_X() != 0 && GlobalPreferences.getAppSettings(this).getCROP_OPTION_Y() != 0) {
            try {
                CropImage.activity(Uri.fromFile(new File(path)))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(GlobalPreferences.getAppSettings(this).getCROP_OPTION_X(), GlobalPreferences.getAppSettings(this).getCROP_OPTION_Y())
                        .setMultiTouchEnabled(true)
                        .start(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            CropImage.activity(Uri.fromFile(new File(path)))
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setMultiTouchEnabled(true)
                    .start(this);
        }
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
    public void onSortChoose(List<MediaFileData> stories) {
        this.mediaFiles = stories;
        Intent intent = new Intent(this, SortStoriesActivity.class);
        intent.putParcelableArrayListExtra(MediaFileData.INTENT_KEY_MEDIA, (ArrayList<? extends Parcelable>) mediaFiles);
        startActivityForResult(intent, SORT_ACTIVITY);
    }

    @Override
    public void onTrimChoose(String path, int position) {
        long minValue = GlobalPreferences.getAppSettings(this).getStory_video_duration_min();
        long maxValue = GlobalPreferences.getAppSettings(this).getStory_video_duration_max();
        this.adapterPosition = position;
        Uri videoUri = Uri.fromFile(new File(path));
        Intent intent = new Intent(this, ActVideoTrimmer.class);
        intent.putExtra(TrimmerConstants.TRIM_VIDEO_URI, String.valueOf(videoUri));
        intent.putExtra(TrimmerConstants.TRIM_TYPE, 3);
        intent.putExtra(TrimmerConstants.MIN_FROM_DURATION, 5L);
        intent.putExtra(TrimmerConstants.MAX_TO_DURATION, 30L);
        startActivityForResult(intent, TRIM_ACTIVITY);
    }

    @Override
    public void onStoryDeleted(List<MediaFileData> itemMedias) {
        if (itemMedias.size() == 0) {
            finish();
        } else {
            this.mediaFiles = itemMedias;
        }
    }

    public void onCropFinish(int resultCode, Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (result != null) {
            switch (resultCode) {
                case RESULT_OK:
                    String path = result.getUri().getPath();
                    editPostAdapter.onCropFinish(path, adapterPosition);
                    break;

                case RESULT_CANCELED:
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
                break;
            case RESULT_CANCELED:
                break;
        }
    }

    private void onSortFinish(int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                this.mediaFiles = data.getParcelableArrayListExtra(MediaFileData.INTENT_KEY_MEDIA);
                editPostAdapter.setItemMedias(mediaFiles);
                break;

            case RESULT_CANCELED:
                break;

        }
    }

    private void onTrimFinish(int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (data != null) {
                    try {
                        String trimmedVideoPath = data.getStringExtra(TrimmerConstants.TRIMMED_VIDEO_PATH);
                        long duration = HelperMedia.getVideoDurationLong(trimmedVideoPath);
                        if (duration > GlobalPreferences.getAppSettings(this).getStory_video_duration_max()) {
//                            MyToaster.getErrorToaster(this, "Video should be smaller then " + GlobalPreferences.getAppSettings(this).getStory_video_duration_max());
                            finish();
                        } else
                            editPostAdapter.onTrimFinish(trimmedVideoPath, adapterPosition);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case RESULT_CANCELED:
                if (isMustTrim) {
//                    MyToaster.getErrorToaster(this, "Video should be smaller then " + GlobalPreferences.getAppSettings(this).getStory_video_duration_max());
                    isMustTrim = false;
                    finish();
                }
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
//        uploadPostPresenter.uploadToS3(ClientManager.transferObserver(this, fileForUpload));
////        uploadObserver.setTransferListener(new TransferListener() {
////
////            @Override
////            public void onStateChanged(int id, TransferState state) {
////                if (TransferState.COMPLETED == state) {
////                    lottieAnimationView.setVisibility(View.GONE);
////                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
////                    String url = ClientManager.initAmazonS3Client(getApplicationContext()).getResourceUrl("travel-guide-3", file.getName());
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
////            uploadPostPresenter.uploadStory("Bearer" + " " + GlobalPreferences.getAccessToken(this), uploadPostRequest);
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
