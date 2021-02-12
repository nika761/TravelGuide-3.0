package travelguideapp.ge.travelguide.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import travelguideapp.ge.travelguide.helper.HelperExoPlayer;

public class CustomFrameLayout extends FrameLayout {
    private Context context;
    private PlayerView playerView;

    private boolean playing;
    private static ExoPlayer exoPlayer;

    public CustomFrameLayout(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    public void addPlayerView() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        playerView = new PlayerView(context);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        playerView.setUseController(false);
        playerView.requestFocus();
        playerView.setLayoutParams(lp);
        addView(playerView);
    }

    public void setExoPlayer() {
        if (exoPlayer == null)
            exoPlayer = HelperExoPlayer.getExoPlayer(context);
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public void releaseExoPlayer() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    public void playRequest(String url) {
        if (exoPlayer != null) {
            if (exoPlayer.isPlaying())
                releaseExoPlayer();
        } else {
            playVideo(url);
        }
    }


    public void playVideo(String url) {
        setExoPlayer();
        MediaSource mediaSource = HelperExoPlayer.getMediaLink(url);
        this.playerView.setPlayer(exoPlayer);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_ENDED:
                        exoPlayer.seekTo(0);
                        break;
                    case Player.STATE_READY:
                        playing = true;
//                            homeFragmentListener.onExoPlayerReady();
                        break;
                    case Player.STATE_BUFFERING:
                        break;
                    case Player.STATE_IDLE:
                        break;
                }
            }
        });
    }
}
