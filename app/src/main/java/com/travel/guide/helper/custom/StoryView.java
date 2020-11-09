package com.travel.guide.helper.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.travel.guide.R;

public class StoryView extends LinearLayout {

    private Context context;
    private StoryListener storyListener;
    boolean stop;
    public int currentPosition = -1;
    public int size;


    public StoryView(Context context) {
        super(context);
        this.context = context;
    }

    public StoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public StoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

    }

    public StoryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }


    public void setStorySize(int size) {
        this.size = size;
        removeAllViews();
        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5);
            lp.weight = 1;
            lp.leftMargin = 3;
            lp.rightMargin = 3;

            ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
            progressBar.setLayoutParams(lp);
            Drawable stateListDrawable = progressBar.getContext().getResources().getDrawable(R.drawable.selector_progress_state_list, null);
            progressBar.setProgressDrawable(stateListDrawable);
            addView(progressBar);

        }
    }


    public void setListener(StoryListener storyListener) {
        this.storyListener = storyListener;
    }

    public void stop(boolean stop) {
        this.stop = stop;
    }

    public void start(final int position, int duration) {
        clearPrevious(position);
        ProgressBar progressBar = (ProgressBar) getChildAt(position);
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(duration);
        currentPosition = position;
        valueAnimator.addUpdateListener(animation -> {
            if (position == currentPosition && progressBar != null) {
                if (!stop) {
                    progressBar.setProgress((int) animation.getAnimatedValue());
                    if (progressBar.getProgress() == 100) {
                        storyListener.storyFinished(position);
                    }
                }
            } else {
                valueAnimator.cancel();
            }

        });
        valueAnimator.start();
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


    public interface StoryListener {
        void storyFinished(int finishedPosition);
    }


}
