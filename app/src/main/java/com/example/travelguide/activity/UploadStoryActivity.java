package com.example.travelguide.activity;

import android.content.Intent;
import android.database.Cursor;
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
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.adapter.recycler.UploadStoryAdapter;
import com.example.travelguide.interfaces.IUploadStory;
import com.example.travelguide.model.request.UploadStoryRequestModel;
import com.example.travelguide.model.response.UploadStoryResponseModel;
import com.example.travelguide.presenters.UploadStoryPresenter;
import com.example.travelguide.utils.UtilsClients;
import com.example.travelguide.utils.UtilsPermissions;
import com.example.travelguide.utils.UtilsPref;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class UploadStoryActivity extends AppCompatActivity implements IUploadStory {
    private static final int FILTER_ACTIVITY = 1;
    private TextView btnNext;
    private ImageView btnBack;
    private ArrayList<Uri> uriArrayList = new ArrayList<>();
    private UploadStoryAdapter adapter;
    private int adapterPosition;
    private List<String> photos = new ArrayList<>();
    private List<String> videos = new ArrayList<>();
    private UploadStoryPresenter uploadStoryPresenter;
    private LottieAnimationView lottieAnimationView;
    private UploadStoryRequestModel uploadStoryRequestModel;
    private File fileForUpload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_story);
        initUI();
        setClickListeners();
        initContentRecyclerAdapter(getIntent().getStringArrayListExtra("selectedPaths"));
    }

    private void initUI() {
        uploadStoryPresenter = new UploadStoryPresenter(this);
        btnNext = findViewById(R.id.btn_next_upload_story);
        btnBack = findViewById(R.id.btn_back_upload_story);
        lottieAnimationView = findViewById(R.id.animation_view_upload);
    }

    private void setClickListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());
        btnNext.setOnClickListener(v -> {
            lottieAnimationView.setVisibility(View.VISIBLE);
            setItemsForUpload();
        });
    }

    private void setItemsForUpload() {
        ArrayList<String> paths = getIntent().getStringArrayListExtra("selectedPaths");
        if (paths != null) {

            //            UtilsMedia.reduceVideoQuality(paths, new UtilsMedia.VideoQualityCallBack() {
//                @Override
//                public void onQualityReduced(String destPath) {
//                    Toast.makeText(UploadStoryActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(UploadStoryActivity.this, MediaDetailActivity.class);
//                    intent.putExtra("path", destPath);
//                    startActivity(intent);
//                }
//
//                @Override
//                public void onStart() {
//                    Toast.makeText(UploadStoryActivity.this, "Start", Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onFail() {
//                    Toast.makeText(UploadStoryActivity.this, "Fail", Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onProgress() {
//                    Toast.makeText(UploadStoryActivity.this, "Progress", Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onCancel() {
//                    Toast.makeText(UploadStoryActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
//
//                }
//            }, this);
            for (String current : paths) {
                if (current.endsWith(".mp4")) {
                    uploadFile(current);
//                    String videoBinary = UtilsMedia.encodeVideo(current);
//                    videos.add(videoBinary);
                } else {
                    uploadFile(current);
//                    final Uri itemUri = Uri.parse(current);
//                    final InputStream imageStream;
//                    try {
//                        imageStream = getContentResolver().openInputStream(itemUri);
//                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                        String encodedImage = UtilsMedia.encodeImage(selectedImage);
//                        photos.add(encodedImage);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }

            }


//            uploadStoryRequestModel = new UploadStoryRequestModel(17, photos, videos);
//            startUpload();

        }

    }

    private void startUpload() {
        uploadStoryPresenter.uploadStory("Bearer" + " " + UtilsPref.getCurrentAccessToken(this), uploadStoryRequestModel);
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
        }
    }

//    private void pickImages(Intent data) {
//        if (data.getClipData() != null) {
//            int count = data.getClipData().getItemCount();
//            for (int i = 0; i < count; i++) {
//                Uri itemUri = data.getClipData().getItemAt(i).getUri();
//                uriArrayList.add(itemUri);
//            }
//            if (uriArrayList.size() <= 10) {
//                initContentRecyclerAdapter(uriArrayList);
//                initFiltersAdapter();
//            } else {
//                Toast.makeText(this, "Max photos is 10", Toast.LENGTH_LONG).show();
//                openGallery();
//            }
//        } else if (data.getData() != null) {
//            Uri uri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                Drawable d = new BitmapDrawable(getResources(), bitmap);
//                photoEditorView.getSource().setImageBitmap(bitmap);
////                photoEditorView.getSource().setImageBitmap(bitmap);
//                initFiltersAdapter();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else if (data.getExtras() != null) {
//            String[] pathsList = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
//            if (pathsList != null) {
//                List<String> stringList = new ArrayList<>(Arrays.asList(pathsList));
//                for (String s : stringList) {
//                    File imgFile = new File(s);
//                    if (imgFile.exists()) {
//                        Uri uri = Uri.fromFile(imgFile);
//                        uriArrayList.add(uri);
//                    }
//                    if (uriArrayList.size() <= 10) {
//                        initContentRecyclerAdapter(uriArrayList);
//                        initFiltersAdapter();
//                        new Thread(this::setPhotosForUpload).start();
//                    }
//                }
//            }
//        }
//    }

    private void initContentRecyclerAdapter(ArrayList<String> photos) {
        RecyclerView recyclerView = findViewById(R.id.recycler_post);
        adapter = new UploadStoryAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setUriArrayList(photos);
    }

    public void checkPermission() {
        if (UtilsPermissions.isReadStoragePermission(this)) {
//            new GligarPicker().requestCode(PICKER_REQUEST_CODE).withActivity(this).show();
//            openGallery();
            List<String> stringList = fetchMedia(3);
            for (String s : stringList) {
                File imgFile = new File(s);
                if (imgFile.exists()) {
                    Uri uri = Uri.fromFile(imgFile);
                    uriArrayList.add(uri);
                }

//                initContentRecyclerAdapter(uriArrayList);
////                new Thread(this::setPhotosForUpload).start();

            }
        } else {
            UtilsPermissions.requestReadStoragePermission(this);
        }
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

    public void onCropFinish(int resultCode, Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (result != null) {
            switch (resultCode) {
                case RESULT_OK:
                    String path = result.getUri().getPath();
                    adapter.onCropFinish(path, adapterPosition);
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
                adapter.onFilterFinish(path, position);
                Toast.makeText(this, "Image Filter Successful", Toast.LENGTH_LONG).show();
                break;
            case RESULT_CANCELED:
                Toast.makeText(this, "Image Filter Error", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onStoryUploaded(UploadStoryResponseModel uploadStoryResponseModel) {
        lottieAnimationView.setVisibility(View.GONE);
        if (uploadStoryResponseModel.getStatus() == 0) {
            Toast.makeText(this, "uploaded", Toast.LENGTH_SHORT).show();
        } else if (uploadStoryResponseModel.getStatus() == 1) {
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
        String url = UtilsClients.amazonS3Client(this).getResourceUrl(UtilsClients.BUCKET, fileForUpload.getName());
//        String url = UtilsClients.initAmazonS3Client(getApplicationContext()).getResourceUrl("travel-guide-3", file.getName());
//        Log.e("link", url);
    }

    @Override
    public void onFileUploadError() {
        Toast.makeText(getApplicationContext(), "error while", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<String> fetchMedia(int type) {
        ArrayList<String> listOfAllImages = new ArrayList<>();

        if (type == 1) {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String absolutePathOfImage = null;
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                listOfAllImages.add(absolutePathOfImage);
            }
            return listOfAllImages;
        }

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String absolutePathOfImage = null;

        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            listOfAllImages.add(absolutePathOfImage);
        }

        return listOfAllImages;
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
        uploadStoryPresenter.uploadToS3(UtilsClients.uploadObserver(this, fileForUpload));
//        uploadObserver.setTransferListener(new TransferListener() {
//
//            @Override
//            public void onStateChanged(int id, TransferState state) {
//                if (TransferState.COMPLETED == state) {
//                    lottieAnimationView.setVisibility(View.GONE);
//                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
//                    String url = UtilsClients.initAmazonS3Client(getApplicationContext()).getResourceUrl("travel-guide-3", file.getName());
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
