package com.example.travelguide.helper;

import android.content.Context;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.File;

public class HelperClients {
    private static final String KEY = "AKIAVWUTGVROTZGHURQ4";
    private static final String SECRET = "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";
    private static final String AMAZONS3_END_POINT = "https://travel-guide-3.s3.eu-central-1.amazonaws.com";
    public static final String BUCKET = "travel-guide-3";

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
        credentials = new BasicAWSCredentials(KEY, SECRET);

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTPS);

        S3ClientOptions options = new S3ClientOptions();
        options.setPathStyleAccess(true);

        s3Client = new AmazonS3Client(credentials, clientConfig);
        s3Client.setEndpoint(AMAZONS3_END_POINT);
        s3Client.setS3ClientOptions(options);

        return s3Client;
    }

    public static TransferUtility transferUtility(Context context, AmazonS3Client amazonS3Client) {
        return TransferUtility.builder()
                .context(context)
                .defaultBucket(BUCKET)
                .s3Client(amazonS3Client)
                .build();
    }

    public static TransferObserver uploadObserver(Context context, File file) {
        return HelperClients.transferUtility(context, amazonS3Client(context))
                .upload(HelperClients.BUCKET, file.getName(), file, CannedAccessControlList.PublicRead);
    }
}
