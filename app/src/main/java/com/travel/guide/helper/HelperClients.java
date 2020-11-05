package com.travel.guide.helper;

import android.content.Context;
import android.util.Log;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transfermanager.MultipleFileUpload;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.travel.guide.R;
import com.travel.guide.model.ItemMedia;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HelperClients {
    private static final String S3_KEY = "AKIAVWUTGVROTZGHURQ4";
    private static final String S3_SECRET = "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";
    private static final String AMAZONS3_END_POINT = "https://travel-guide-3.s3.eu-central-1.amazonaws.com";
    public static final String S3_BUCKET = "travel-guide-3/temp";

    public static GoogleSignInClient googleSignInClient(Context context) {

        GoogleSignInClient mGoogleSignInClient;

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

        return mGoogleSignInClient;
    }

    public static AmazonS3Client amazonS3Client(Context context) {

        AmazonS3Client s3Client;
        BasicAWSCredentials credentials;

        AWSMobileClient.getInstance().initialize(context).execute();
        credentials = new BasicAWSCredentials(S3_KEY, S3_SECRET);

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTPS);

        S3ClientOptions options = new S3ClientOptions();
        options.setPathStyleAccess(true);

        s3Client = new AmazonS3Client(credentials, clientConfig);
        s3Client.setEndpoint(AMAZONS3_END_POINT);
        s3Client.setS3ClientOptions(options);

        return s3Client;
    }

    static TransferUtility transferUtility(Context context, AmazonS3Client amazonS3Client) {
        return TransferUtility.builder()
                .context(context)
                .defaultBucket(S3_BUCKET)
                .s3Client(amazonS3Client)
                .build();
    }

    public static TransferObserver transferObserver(Context context, File file) {
        return HelperClients.transferUtility(context, amazonS3Client(context))
                .upload(HelperClients.S3_BUCKET, file.getName(), file, CannedAccessControlList.PublicRead);
    }

    public static void uploadMultipleS3(AmazonS3Client s3Client, List<ItemMedia> paths) {
        ArrayList<File> files = new ArrayList<>();
        for (ItemMedia list : paths) {
            if (list.getType() == 0) {
                files.add(new File(list.getPath()));
            }
        }

//        TransferManager tm = new TransferManager(s3Client);
//
//        ObjectMetadataProvider metadataProvider = (file, metadata) -> metadata.addUserMetadata("original-image-date", file.getAbsolutePath());
//
//        MultipleFileUpload upload = tm.uploadFileList(S3_BUCKET, S3_KEY, new File("."), files, metadataProvider);
//        upload.addProgressListener((ProgressListener) progressEvent ->
//                Log.e("taggssg", String.valueOf(progressEvent.getEventCode())));
//
//        tm.shutdownNow();
//
        TransferManager transferManager = new TransferManager(s3Client);
        try {
            MultipleFileUpload xfer = transferManager.uploadFileList(S3_BUCKET, S3_KEY, new File("."), files);
            try {
                xfer.waitForCompletion();
            } catch (InterruptedException e) {
                Log.e("ssss", e.getMessage());
                e.printStackTrace();
            }
            xfer.addProgressListener((ProgressListener) progressEvent ->
                    Log.e("ssss", String.valueOf(progressEvent.getEventCode())));
            if (xfer.isDone()) {
                Log.e("ssss", String.valueOf(xfer.getProgress().getPercentTransferred()));
            } else {
                Log.e("ssss", String.valueOf(xfer.getProgress().getPercentTransferred()));
            }
        } catch (AmazonS3Exception s3Exception) {
            Log.e("ssss", Objects.requireNonNull(s3Exception.getMessage()));
        }
        transferManager.shutdownNow();
    }


}