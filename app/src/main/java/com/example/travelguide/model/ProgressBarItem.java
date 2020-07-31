package com.example.travelguide.model;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.travelguide.R;

import java.util.concurrent.TimeUnit;

public class ProgressBarItem {

    ProgressBar progressBar;
    ObjectAnimator objectAnimator;

    public ProgressBarItem(ProgressBar progressBar, ObjectAnimator objectAnimator) {
        this.progressBar = progressBar;

        Drawable stateListDrawable = progressBar.getContext()
                .getResources().getDrawable(R.drawable.selector_progress_state_list, null);

        progressBar.setProgressDrawable(stateListDrawable);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5,
                1.0f);
        params.leftMargin = 3;
        params.rightMargin = 3;
        progressBar.setLayoutParams(params);

        this.objectAnimator = objectAnimator;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public ObjectAnimator getObjectAnimator() {
        return objectAnimator;
    }

    public void setObjectAnimator(ObjectAnimator objectAnimator) {
        this.objectAnimator = objectAnimator;
    }
}
