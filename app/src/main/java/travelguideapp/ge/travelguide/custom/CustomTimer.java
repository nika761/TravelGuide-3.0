package travelguideapp.ge.travelguide.custom;

import android.os.CountDownTimer;

import travelguideapp.ge.travelguide.model.customModel.PostViewItem;

import java.util.ArrayList;
import java.util.List;

public class CustomTimer extends CountDownTimer {

    private static final List<PostViewItem> postViewItems = new ArrayList<>();
    private static PostViewItem postViewItem;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     *
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */

    public CustomTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setCurrentPost(PostViewItem postViewItem) {
        CustomTimer.postViewItem = postViewItem;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        /** In this case we don't need use this method */
        /** We can just listen to this fucking music https://www.youtube.com/watch?v=hFAEYWQl1eM */
    }

    @Override
    public void onFinish() {
        postViewItems.add(postViewItem);
    }

    public static List<PostViewItem> getPostViewItems() {
        return postViewItems;
    }

    public void clearPostsViews() {
        postViewItems.clear();
    }

}
