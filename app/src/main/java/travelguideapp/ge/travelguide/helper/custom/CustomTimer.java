package travelguideapp.ge.travelguide.helper.custom;

import android.os.CountDownTimer;

import travelguideapp.ge.travelguide.model.PostView;

import java.util.ArrayList;
import java.util.List;

public class CustomTimer extends CountDownTimer {

    private static List<PostView> postViews = new ArrayList<>();
    private static PostView postView;

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
        CustomTimer.postView = postView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        /** In this case we don't need use this method */
        /** We can just listen to this fucking music https://www.youtube.com/watch?v=hFAEYWQl1eM */
    }

    @Override
    public void onFinish() {
        postViews.add(postView);
    }

    public static List<PostView> getPostViews() {
        return postViews;
    }

    public void clearPostsViews() {
        postViews.clear();
    }

}
