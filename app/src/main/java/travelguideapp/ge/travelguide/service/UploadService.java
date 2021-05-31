package travelguideapp.ge.travelguide.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.request.UploadPostRequest;
import travelguideapp.ge.travelguide.model.response.UploadPostResponse;
import travelguideapp.ge.travelguide.network.RetrofitManager;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;

public class UploadService extends Service {

    private MediaPlayer mediaPlayer;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    private Handler handler;
    private Runnable runnable;
    private CountDownTimer countDownTimer;

    private int progress;
    private static boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        startUpload(intent.getParcelableExtra("UPLOAD_DATA"));
        return START_STICKY;
    }

    private void startUpload(UploadPostRequest UploadPostRequest) {
        sendNotification();
        String unc = "application/x-www-form-urlencoded";
        RetrofitManager.getApiService().uploadPost(unc, UploadPostRequest).enqueue(new Callback<UploadPostResponse>() {
            @Override
            public void onResponse(@NotNull Call<UploadPostResponse> call, @NotNull Response<UploadPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        notificationBuilder.setProgress(100, 95, false);
                        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                        notificationManager.notify(12, notificationBuilder.build());
                        isRunning = false;
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onFailure(@NotNull Call<UploadPostResponse> call, @NotNull Throwable t) {
            }
        });
    }

    public static boolean isRunning() {
        return isRunning;
    }

    private void startHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
            runnable = () -> {
                countDownTimer = new CountDownTimer(10000, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        progress += 5;
                        notificationBuilder.setProgress(100, progress, false);
                        notificationBuilder.setContentText("Downloading " + progress + "/" + 100);
                        notificationManager.notify(12, notificationBuilder.build());
                    }

                    @Override
                    public void onFinish() {
                        notificationBuilder.setContentTitle("Downloaded");
                        notificationBuilder.setContentText("Finished");
                        notificationBuilder.setProgress(100, progress, false);
                        notificationManager.notify(12, notificationBuilder.build());
                        progress = 0;
                        isRunning = false;
                    }
                };
                countDownTimer.start();
            };
            handler.postDelayed(runnable, 2000);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
            runnable = null;
            countDownTimer.cancel();
            countDownTimer = null;
            handler = null;
        }
        isRunning = false;
        notificationManager.cancel(12);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void sendNotification() {
        try {
            Intent intent;
            intent = new Intent(this, HomePageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            String channelId = "BABABA";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            notificationBuilder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.main_icon_sun)
                    .setContentTitle("Upload Started")
                    .setProgress(100, 0, false)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(12, notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
