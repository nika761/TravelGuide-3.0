package com.travelguide.travelguide.helper;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.services.s3.model.CannedAccessControlList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

import static com.travelguide.travelguide.helper.HelperClients.amazonS3Client;

public class S3Helper {

    public class AWSFile {
        private int id;
        private TransferState newState;
        private String filename;

        AWSFile(int id, TransferState newState, String filename) {
            this.id = id;
            this.newState = newState;
            this.filename = filename;
        }
    }

    public Observable<AWSFile> upload(List<String> paths, Context context) {
        List<Observable<AWSFile>> list = new ArrayList<>();
        for (String path : paths) {
            list.add(upload(path, context));
        }
        return Observable.concat(list);
    }

    public Observable<AWSFile> upload(String filePath, Context context) {
        if (filePath == null) {
//            Log.d(TAG, "uploadWithTransferUtility: ");
            return Observable.never();
        }
        return Observable.create(emitter -> {
            File file = new File(filePath);
            TransferObserver observer = HelperClients.transferUtility(context, amazonS3Client(context)).upload(HelperClients.S3_BUCKET, file.getName(), file, CannedAccessControlList.PublicRead);
            observer.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
//                    stateChanged(id, state);
                    emitter.onNext(new AWSFile(id, state, file.getName()));
                    Log.e("cccსსc", state.toString());
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//                    progressChanged(id, bytesCurrent, bytesTotal);
                    Log.e("cccსსc", String.valueOf(bytesCurrent));

                }

                @Override
                public void onError(int id, Exception ex) {
//                    error(id,ex);
                    emitter.onError(ex);
                    Log.e("cccსსc", ex.getCause().getMessage());
                }
            });
            emitter.setCancellable(observer::cleanTransferListener);
        });
    }
}
