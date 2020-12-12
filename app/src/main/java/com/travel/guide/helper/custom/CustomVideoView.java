package com.travel.guide.helper.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class CustomVideoView extends VideoView {

    private int mVideoWidth;
    private int mVideoHeight;
    private DisplayMode displayMode = DisplayMode.ORIGINAL;

    public enum DisplayMode {
        ORIGINAL,       // original aspect ratio
        FULL_SCREEN,    // fit to screen
        ZOOM            // zoom in
    }

    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mVideoWidth = 0;
        mVideoHeight = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);

        switch (displayMode) {
            case ORIGINAL:
                if (mVideoWidth > 0 && mVideoHeight > 0) {
                    if (mVideoWidth * height > width * mVideoHeight) {
                        // video height exceeds screen, shrink it
                        height = width * mVideoHeight / mVideoWidth;
                    } else if (mVideoWidth * height < width * mVideoHeight) {
                        // video width exceeds screen, shrink it
                        width = height * mVideoWidth / mVideoHeight;
                    }
                }

                break;
            case FULL_SCREEN:

                break;

            case ZOOM:

                if (mVideoWidth > 0 && mVideoHeight > 0 && mVideoWidth < width) {
                    height = mVideoHeight * width / mVideoWidth;
                }

                break;
        }
        setMeasuredDimension(width, height);
    }

    public void changeVideoSize(int width, int height) {
        mVideoWidth = width;
        mVideoHeight = height;
        // not sure whether it is useful or not but safe to do so
        getHolder().setFixedSize(width, height);
        requestLayout();
        invalidate();     // very important, so that onMeasure will be triggered
    }

    public void setDisplayMode(DisplayMode mode) {
        displayMode = mode;
    }

}