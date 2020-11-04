package com.travelguide.travelguide.helper.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ui.PlayerView;

public class StoryVideo extends PlayerView {

    private Context context;

    public StoryVideo(Context context) {
        super(context);
        this.context = context;
    }

    public StoryVideo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void addPlayerView() {
        removeAllViews();
        PlayerView.LayoutParams playerViewParams = new PlayerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        PlayerView playerView = new PlayerView(context);
        playerView.setLayoutParams(playerViewParams);
        addView(playerView);
    }

    private void clearPrevious(int pos) {
        for (int i = 0; i < getChildCount(); i++) {
            ProgressBar progressBar = (ProgressBar) getChildAt(i);
            if (progressBar != null) {
                if (pos <= i) {
                    progressBar.setProgress(0);
                } else {
                    progressBar.setProgress(100);
                }
            }
        }
    }

}
