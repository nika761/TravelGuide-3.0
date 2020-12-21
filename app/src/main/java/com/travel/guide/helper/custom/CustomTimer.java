package com.travel.guide.helper.custom;

import android.os.CountDownTimer;
import android.util.Log;

import com.travel.guide.model.PostView;
import com.travel.guide.ui.home.home.HomeFragmentListener;

import java.util.ArrayList;
import java.util.List;

public class CustomTimer extends CountDownTimer {

    private static List<PostView> postViews = new ArrayList<>();
    private PostView postView;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */

    public CustomTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setPostView(PostView postView) {
        this.postView = postView;
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        postViews.add(postView);
    }

    public static List<PostView> getPostViews() {
        return postViews;
    }

}
