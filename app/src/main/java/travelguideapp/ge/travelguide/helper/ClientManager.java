package travelguideapp.ge.travelguide.helper;

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

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.customModel.ItemMedia;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClientManager {

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

        AWSMobileClient.getInstance().initialize(context).execute();
        BasicAWSCredentials credentials = new BasicAWSCredentials(GlobalPreferences.getAppSettings(context).getS3_1(), GlobalPreferences.getAppSettings(context).getS3_2());

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTPS);

        S3ClientOptions options = new S3ClientOptions();
        options.setPathStyleAccess(true);

        s3Client = new AmazonS3Client(credentials, clientConfig);
        s3Client.setEndpoint(GlobalPreferences.getAppSettings(context).getS3_END_POINT());
        s3Client.setS3ClientOptions(options);

        return s3Client;
    }

    static TransferUtility transferUtility(Context context, AmazonS3Client amazonS3Client) {
        return TransferUtility.builder()
                .context(context)
                .defaultBucket(GlobalPreferences.getAppSettings(context).getS3_BUCKET_NAME())
                .s3Client(amazonS3Client)
                .build();
    }

    public static TransferObserver transferObserver(Context context, File file) {
        return ClientManager.transferUtility(context, amazonS3Client(context)).upload(GlobalPreferences.getAppSettings(context).getS3_BUCKET_NAME(), file.getName(), file, CannedAccessControlList.PublicRead);
    }

//    public static void uploadMultipleS3(AmazonS3Client s3Client, List<ItemMedia> paths) {
//        ArrayList<File> files = new ArrayList<>();
//        for (ItemMedia list : paths) {
//            if (list.getType() == 0) {
//                files.add(new File(list.getPath()));
//            }
//        }
//
////        TransferManager tm = new TransferManager(s3Client);
////
////        ObjectMetadataProvider metadataProvider = (file, metadata) -> metadata.addUserMetadata("original-image-date", file.getAbsolutePath());
////
////        MultipleFileUpload upload = tm.uploadFileList(S3_BUCKET, S3_KEY, new File("."), files, metadataProvider);
////        upload.addProgressListener((ProgressListener) progressEvent ->
////                Log.e("taggssg", String.valueOf(progressEvent.getEventCode())));
////
////        tm.shutdownNow();
////
//        TransferManager transferManager = new TransferManager(s3Client);
//        try {
//            MultipleFileUpload transferCallback = transferManager.uploadFileList(S3_BUCKET, S3_KEY, new File("."), files);
//            try {
//                transferCallback.waitForCompletion();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            transferCallback.addProgressListener((ProgressListener) progressEvent -> Log.e("ssss", String.valueOf(progressEvent.getEventCode())));
//
//            if (transferCallback.isDone()) {
//                Log.e("ssss", String.valueOf(transferCallback.getProgress().getPercentTransferred()));
//            } else {
//                Log.e("ssss", String.valueOf(transferCallback.getProgress().getPercentTransferred()));
//            }
//
//        } catch (AmazonS3Exception s3Exception) {
//            s3Exception.printStackTrace();
//        }
//        transferManager.shutdownNow();
//    }


}