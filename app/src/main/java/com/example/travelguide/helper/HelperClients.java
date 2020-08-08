package com.example.travelguide.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transfermanager.MultipleFileUpload;
import com.amazonaws.mobileconnectors.s3.transfermanager.ObjectMetadataProvider;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.mobileconnectors.s3.transfermanager.internal.MultipleFileTransfer;
import com.amazonaws.mobileconnectors.s3.transfermanager.internal.MultipleFileTransferMonitor;
import com.amazonaws.mobileconnectors.s3.transfermanager.internal.MultipleFileUploadImpl;
import com.amazonaws.mobileconnectors.s3.transfermanager.internal.TransferManagerUtils;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.example.travelguide.model.ItemMedia;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HelperClients {
    private static final String S3_KEY = "AKIAVWUTGVROTZGHURQ4";
    private static final String S3_SECRET = "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";
    private static final String AMAZONS3_END_POINT = "https://travel-guide-3.s3.eu-central-1.amazonaws.com";
    public static final String S3_BUCKET = "travel-guide-3";

    public static GoogleSignInClient googleSignInClient(Context context) {

        GoogleSignInClient mGoogleSignInClient;

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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

    private static TransferUtility transferUtility(Context context, AmazonS3Client amazonS3Client) {
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


        TransferManager tm = new TransferManager(s3Client);

        ObjectMetadataProvider metadataProvider = (file, metadata) -> metadata.addUserMetadata("original-image-date", file.getAbsolutePath());

        MultipleFileUpload upload = tm.uploadFileList(S3_BUCKET, S3_KEY, new File("."), files, metadataProvider);
        upload.addProgressListener((ProgressListener) progressEvent ->
                Log.e("taggssg", String.valueOf(progressEvent.getEventCode())));

        tm.shutdownNow();


//        TransferManager transferManager = new TransferManager(s3Client);
//        try {
//            MultipleFileUpload xfer = transferManager.uploadFileList(S3_BUCKET, S3_KEY, new File("."), files);
//            xfer.addProgressListener((ProgressListener) progressEvent ->
//                    Log.e("taggssg", String.valueOf(progressEvent.getEventCode())));
//            // loop with Transfer.isDone()
////            XferMgrProgress.showTransferProgress(xfer);
//            // or block with Transfer.waitForCompletion()
////            XferMgrProgress.waitForCompletion(xfer);
////            if (xfer.isDone()) {
////                Toast.makeText(context, String.valueOf(xfer.getProgress().getPercentTransferred()), Toast.LENGTH_LONG).show();
////                Log.e("ssss", String.valueOf(xfer.getProgress().getPercentTransferred()));
////
////            } else {
////                Log.e("ssss", String.valueOf(xfer.getProgress().getPercentTransferred()));
////                Toast.makeText(context, xfer.getDescription(), Toast.LENGTH_LONG).show();
//
//
////            }
//
//        } catch (AmazonServiceException a) {
//            Log.e("ssss", a.getErrorMessage());
//        }
//        transferManager.shutdownNow();
    }
}