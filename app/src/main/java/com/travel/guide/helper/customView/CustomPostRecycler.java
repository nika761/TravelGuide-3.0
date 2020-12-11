package com.travel.guide.helper.customView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.travel.guide.R;
import com.travel.guide.enums.SearchPostBy;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.ui.home.home.HashtagAdapter;
import com.travel.guide.ui.home.home.HomeFragmentListener;
import com.travel.guide.ui.searchPost.SearchPostActivity;
import com.travel.guide.utility.GlobalPreferences;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomPostRecycler extends RecyclerView {

    private static final String TAG = "VideoPlayerRecyclerView";

    private enum VolumeState {ON, OFF}

    private enum EmotionState {DEFAULT, CUSTOM}

    private enum EmotionType {LIKE, FAVORITE, FOLLOW}

    // ui
    private ImageView thumbnail;
    private ProgressBar progressBar;
    private View viewHolderParent;
    private FrameLayout frameLayout, postCoverContainer;
    private PlayerView videoSurfaceView;
    private SimpleExoPlayer videoPlayer;
    private Bitmap thumb;
    private Drawable drawable;

    // vars
    private List<PostResponse.Posts> posts = new ArrayList<>();
    private int videoSurfaceDefaultHeight = 0;
    private int screenDefaultHeight = 0;
    private Context context;
    private int playPosition = -1;
    private int playingPosition;
    private boolean isVideoViewAdded;
    private int currentPlayingPosition;

    // controlling playback state
    private boolean soundOn = true;
    private boolean videoPlaying = true;

    private HomeFragmentListener homeFragmentListener;
    private CustomProgressBar customProgressBar;

    /// holder ui
    public ImageView storyCover, music, videoPlayIcon;
    public View parent;
    public RequestManager requestManager;
//    public HomeFragmentListener listener;

    public TextView nickName, description, musicName, location, storyLikes, storyComments, storyShares, storyFavorites;
    public CircleImageView profileImage;
    public ImageButton like, follow, share, favorite, comment, menu;
    public RecyclerView hashtagRecycler;

    public FrameLayout media_container;
//    public PostResponse.Posts post;

    public static int ownerUserId;
    public int position;
    public int countLikeUp = -1;
    public int countLikeDown = Integer.MAX_VALUE;

    public int countFavoriteUp = -1;
    public int countFavoriteDown = Integer.MAX_VALUE;


    public CustomPostRecycler(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomPostRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        this.context = context.getApplicationContext();
        ownerUserId = GlobalPreferences.getUserId(context);
        try {
            Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            videoSurfaceDefaultHeight = point.x;
            screenDefaultHeight = point.y;
        } catch (Exception e) {
            e.printStackTrace();
        }

        videoSurfaceView = new PlayerView(this.context);
//        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
//        videoSurfaceView.setKeepScreenOn(true);

//        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

//        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        // 2. Create the player
        videoPlayer = ExoPlayerFactory.newSimpleInstance(context, getTrackSelector());
        // Bind the player to the view.
        videoSurfaceView.setUseController(false);
        videoSurfaceView.setKeepContentOnPlayerReset(true);
        videoSurfaceView.setPlayer(videoPlayer);
        setVolume(VolumeState.ON);

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {

                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:

                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                        customProgressBar.stop(true);
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                        customProgressBar.stop(false);
//                        Log.d(TAG, "onScrollStateChanged: called.");
//                        if (thumbnail != null) {
//                        thumbnail.setVisibility(VISIBLE);
//                        }
//                        customProgressBar.removeAllViews();
//                        customProgressBar.setStorySize(1);
                        if (!recyclerView.canScrollVertically(1)) {
                            playVideo(true);
                        } else {
                            playVideo(false);
                        }
                        break;
                }
            }

            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        addOnChildAttachStateChangeListener(new OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NotNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NotNull View view) {
                if (viewHolderParent != null && viewHolderParent.equals(view)) {
                    resetVideoView();
                }
            }
        });


        videoPlayer.addListener(new Player.EventListener() {

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                homeFragmentListener.stopLoader();
//                if (error.type == ExoPlaybackException.TYPE_RENDERER) {
//
//                }
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_BUFFERING:
                        Log.d(TAG, "onPlayerStateChanged: Video ended.");
//                        if (progressBar != null) {
//                            progressBar.setVisibility(VISIBLE);
//                        }
                        break;

                    case Player.STATE_ENDED:
                        Log.d(TAG, "onPlayerStateChanged: Video ended.");
                        videoPlayer.seekTo(0);
//                        customProgressBar.start(0, posts.get(playingPosition).getPost_stories().get(0).getSecond());
                        break;

                    case Player.STATE_IDLE:
                        Log.d(TAG, "onPlayerStateChanged: Idle here");
                        break;

                    case Player.STATE_READY:
                        homeFragmentListener.stopLoader();
//                        if (playWhenReady) {
//                            customProgressBar.start(0, posts.get(playingPosition).getPost_stories().get(0).getSecond());
//                        } else {
//                            customProgressBar.setPause(false);
//                        }
                        Log.d(TAG, "onPlayerStateChanged: Ready to play.");
                        if (progressBar != null) {
                            progressBar.setVisibility(GONE);
                        }
                        break;

                    default:
                        break;
                }
            }

        });

    }

    public void playVideo(boolean isEndOfList) {

        int targetPosition;

        if (!isEndOfList) {
            int startPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            int endPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();

            // if there is more than 2 list-items on the screen, set the difference to be 1
            if (endPosition - startPosition > 1) {
                endPosition = startPosition + 1;
            }

            // something is wrong. return.
            if (startPosition < 0 || endPosition < 0) {
                return;
            }

            // if there is more than 1 list-item on the screen
            if (startPosition != endPosition) {
                int startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition);
                int endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition);

                targetPosition = startPositionVideoHeight > endPositionVideoHeight ? startPosition : endPosition;
            } else {
                targetPosition = startPosition;
            }

        } else {
            targetPosition = posts.size() - 1;
        }

        // video is already playing so return
        if (targetPosition == playPosition) {
            return;
        }

        // set the position of the list-item that is to be played
        playPosition = targetPosition;
        if (videoSurfaceView == null) {
            return;
        }

        // remove any old surface views from previously playing videos
        videoSurfaceView.setVisibility(INVISIBLE);
        removeVideoView(videoSurfaceView);

        int currentPosition = targetPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();

        View child = getChildAt(currentPosition);
        if (child == null) {
            return;
        }

        CustomPostHolder holder = (CustomPostHolder) child.getTag();
        if (holder == null) {
            playPosition = -1;
            return;
        }

        viewHolderParent = holder.itemView;
        frameLayout = holder.itemView.findViewById(R.id.pl_container);
        videoPlayIcon = holder.videoPlayIcon;

        videoSurfaceView.setPlayer(videoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "TravelGuide"));

        try {
            thumb = ThumbnailUtils.createVideoThumbnail(posts.get(targetPosition).getPost_stories().get(0).getUrl(), MediaStore.Images.Thumbnails.MINI_KIND);
            drawable = new BitmapDrawable(getResources(), thumb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!isVideoViewAdded) {
            addVideoView();
        }

        String mediaUrl = posts.get(targetPosition).getPost_stories().get(0).getUrl();
        bindItem(holder, posts.get(targetPosition));

        playingPosition = targetPosition;
        if (mediaUrl != null) {
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mediaUrl));
            videoPlayer.prepare(videoSource);
            videoPlayer.setPlayWhenReady(true);
        }
    }


    private int getVisibleVideoSurfaceHeight(int playPosition) {
        int at = playPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();

        View child = getChildAt(at);
        if (child == null) {
            return 0;
        }

        int[] location = new int[2];
        child.getLocationInWindow(location);

        if (location[1] < 0) {
            return location[1] + videoSurfaceDefaultHeight;
        } else {
            return screenDefaultHeight - location[1];
        }
    }

    // Remove the old player
    private void removeVideoView(PlayerView videoView) {
        try {
            if (viewHolderParent != null)
                viewHolderParent.setOnClickListener(null);

            ViewGroup parent = (ViewGroup) videoView.getParent();

            if (parent == null) {
                return;
            }

            int index = parent.indexOfChild(videoView);
            if (index >= 0) {
                parent.removeViewAt(index);
                isVideoViewAdded = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindItem(CustomPostHolder holder, PostResponse.Posts post) {

        PostResponse.Post_stories story = post.getPost_stories().get(0);

        try {
            HelperMedia.loadCirclePhotoProfile(context, post.getProfile_pic(), holder.profileImage);

            if (post.getUser_id() == ownerUserId) {
                holder.follow.setVisibility(GONE);
            } else {
                if (post.getI_follow_post_owner()) {
                    holder.follow.setVisibility(View.GONE);
                } else {
                    holder.follow.setVisibility(View.VISIBLE);
                }
            }

            if (story.getStory_like_by_me())
                holder.like.setBackground(context.getResources().getDrawable(R.drawable.emoji_heart_red, null));
            else
                holder.like.setBackground(context.getResources().getDrawable(R.drawable.emoji_heart, null));

            holder.storyLikes.setText(String.valueOf(story.getStory_likes()));
            holder.storyComments.setText(String.valueOf(story.getStory_comments()));
            holder.storyShares.setText(String.valueOf(post.getPost_shares()));

            holder.storyFavorites.setText(String.valueOf(post.getPost_favorites()));
            if (post.getI_favor_post())
                holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.emoji_link_yellow, null));
            else
                holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.emoji_link, null));


            holder.nickName.setText(post.getNickname());

            if (post.getDescription() == null) {
                holder.description.setVisibility(View.GONE);
            } else {
                if (post.getDescription().isEmpty() || post.getDescription().equals("")) {
                    holder.description.setVisibility(View.GONE);
                } else {
                    holder.description.setVisibility(View.VISIBLE);
                    holder.description.setText(post.getDescription());
                }
            }

            if (post.getHashtags() == null) {
                holder.hashtagRecycler.setVisibility(View.GONE);
            } else {
                if (post.getHashtags().size() > 0) {
                    holder.hashtagRecycler.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                    holder.hashtagRecycler.setAdapter(new HashtagAdapter(post.getHashtags()));
                    holder.hashtagRecycler.setVisibility(View.VISIBLE);
                } else {
                    holder.hashtagRecycler.setVisibility(View.GONE);
                }
            }

            if (post.getPost_locations() == null) {
                holder.location.setVisibility(GONE);
            } else {
                if (post.getPost_locations().size() != 0) {
                    holder.location.setVisibility(VISIBLE);
                    holder.location.setText(post.getPost_locations().get(0).getAddress());
                } else {
                    holder.location.setVisibility(GONE);
                }
            }

            holder.musicName.setText(post.getMusic_text());
            holder.musicName.setSelected(true);

            if (ownerUserId == post.getUser_id()) {
                holder.menu.setVisibility(View.VISIBLE);
            } else {
                holder.menu.setVisibility(View.GONE);
            }

            ////Listeners
            holder.menu.setOnClickListener(v -> holder.showMenu(holder.menu));

            holder.profileImage.setOnClickListener(v -> homeFragmentListener.onUserChoose(post.getUser_id()));

            holder.comment.setOnClickListener(v -> homeFragmentListener.onCommentChoose(story.getStory_id(), post.getPost_id()));

            holder.follow.setOnClickListener(v -> {
                homeFragmentListener.onFollowChoose(post.getUser_id());
                setStoryEmotion(EmotionType.FOLLOW, post, holder);
            });

            holder.like.setOnClickListener(v -> {
                homeFragmentListener.onStoryLikeChoose(post.getPost_id(), story.getStory_id(), position);
                setStoryEmotion(EmotionType.LIKE, post, holder);
            });

            holder.share.setOnClickListener(v -> homeFragmentListener.onShareChoose(post.getPost_share_url(), post.getPost_id()));
            holder.favorite.setOnClickListener(v -> {
                homeFragmentListener.onFavoriteChoose(post.getPost_id(), position);
                setStoryEmotion(EmotionType.FAVORITE, post, holder);
            });

            holder.music.setOnClickListener(v -> {
                if (soundOn) {
                    holder.music.setBackground(context.getResources().getDrawable(R.drawable.icon_music_off, null));
                    setVolume(VolumeState.OFF);
                } else {
                    holder.music.setBackground(context.getResources().getDrawable(R.drawable.icon_music, null));
                    setVolume(VolumeState.ON);
                }
            });

            holder.location.setOnClickListener(v -> {
                Intent postHashtagIntent = new Intent(context, SearchPostActivity.class);
                postHashtagIntent.putExtra("search_type", SearchPostBy.LOCATION);
                postHashtagIntent.putExtra("search_post_id", post.getPost_id());
                context.startActivity(postHashtagIntent);
            });


        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
        }
    }

    private void setStoryEmotion(EmotionType emotionType, PostResponse.Posts post, CustomPostHolder holder) {

        PostResponse.Post_stories story = post.getPost_stories().get(0);

        switch (emotionType) {
            case LIKE:
                if (story.getStory_like_by_me()) {
                    if (countLikeUp > story.getStory_likes()) {
                        animateBtn(holder.like, EmotionType.LIKE, EmotionState.DEFAULT);
                        holder.storyLikes.setText(String.valueOf(story.getStory_likes()));
                        story.setStory_like_by_me(false);

                    } else {
                        animateBtn(holder.like, EmotionType.LIKE, EmotionState.DEFAULT);
                        holder.storyLikes.setText(String.valueOf(story.getStory_likes() - 1));
                        countLikeDown = story.getStory_likes() - 1;
                        story.setStory_like_by_me(false);
                    }

                } else {
                    if (countLikeDown < story.getStory_likes()) {
                        animateBtn(holder.like, EmotionType.LIKE, EmotionState.CUSTOM);
                        holder.storyLikes.setText(String.valueOf(story.getStory_likes()));
                        story.setStory_like_by_me(true);
                    } else {
                        animateBtn(holder.like, EmotionType.LIKE, EmotionState.CUSTOM);
                        holder.storyLikes.setText(String.valueOf(story.getStory_likes() + 1));
                        countLikeUp = story.getStory_likes() + 1;
                        story.setStory_like_by_me(true);
                    }
                }

                break;

            case FAVORITE:
                if (post.getI_favor_post()) {
                    if (countFavoriteUp > post.getPost_favorites()) {
                        animateBtn(holder.favorite, EmotionType.FAVORITE, EmotionState.DEFAULT);
                        holder.storyFavorites.setText(String.valueOf(post.getPost_favorites()));
                        post.setI_favor_post(false);
                    } else {
                        animateBtn(holder.favorite, EmotionType.FAVORITE, EmotionState.DEFAULT);
                        holder.storyFavorites.setText(String.valueOf(post.getPost_favorites() - 1));
                        countFavoriteDown = post.getPost_favorites() - 1;
                        post.setI_favor_post(false);
                    }

                } else {
                    if (countFavoriteDown < post.getPost_favorites()) {
                        animateBtn(holder.favorite, EmotionType.FAVORITE, EmotionState.CUSTOM);
                        holder.storyFavorites.setText(String.valueOf(post.getPost_favorites()));
                        post.setI_favor_post(true);
                    } else {
                        animateBtn(holder.favorite, EmotionType.FAVORITE, EmotionState.CUSTOM);
                        holder.storyFavorites.setText(String.valueOf(post.getPost_favorites() + 1));
                        countFavoriteUp = post.getPost_favorites() + 1;
                        post.setI_favor_post(true);
                    }

                }
                break;

            case FOLLOW:
                if (!post.getI_follow_post_owner()) {
                    animateBtn(holder.follow, EmotionType.FOLLOW, EmotionState.DEFAULT);
                    post.setI_follow_post_owner(true);
                }
                break;
        }

    }


    private void animateBtn(View view, EmotionType emotionType, EmotionState emotionState) {
        switch (emotionType) {
            case LIKE:
                switch (emotionState) {
                    case DEFAULT:
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> view.setBackground(context.getResources().getDrawable(R.drawable.emoji_heart, null)))
                                .duration(250)
                                .playOn(view);
                        break;
                    case CUSTOM:
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> view.setBackground(context.getResources().getDrawable(R.drawable.emoji_heart_red, null)))
                                .duration(250)
                                .playOn(view);
                        break;
                }
                break;

            case FAVORITE:
                switch (emotionState) {
                    case CUSTOM:
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> view.setBackground(context.getResources().getDrawable(R.drawable.emoji_link_yellow, null)))
                                .duration(250)
                                .playOn(view);
                        break;

                    case DEFAULT:
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> view.setBackground(context.getResources().getDrawable(R.drawable.emoji_link, null)))
                                .duration(250)
                                .playOn(view);
                        break;
                }

                break;

            case FOLLOW:
                YoYo.with(Techniques.FadeOut)
                        .onEnd(animator -> view.setVisibility(GONE))
                        .duration(250)
                        .playOn(view);
                break;
        }

    }

    private void addVideoView() {
        frameLayout.addView(videoSurfaceView);
        isVideoViewAdded = true;
        try {
            videoSurfaceView.setBackground(drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        videoSurfaceView.requestFocus();
        videoSurfaceView.setVisibility(VISIBLE);
        videoSurfaceView.setAlpha(1);

        try {
            viewHolderParent.setOnClickListener(v -> {
                if (videoPlaying) {
                    pausePlayer();
                } else {
                    startPlayer();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TrackSelector getTrackSelector() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        return new DefaultTrackSelector(videoTrackSelectionFactory);
    }

    private void resetVideoView() {
        if (isVideoViewAdded) {
            removeVideoView(videoSurfaceView);
            playPosition = -1;
            videoSurfaceView.setVisibility(INVISIBLE);
        }
    }

    private void pausePlayer() {
        videoPlaying = false;
        animateVideoPlyIcon(false);
        videoPlayer.setPlayWhenReady(false);
        videoPlayer.getPlaybackState();
    }

    private void startPlayer() {
        videoPlaying = true;
        animateVideoPlyIcon(true);
        videoPlayer.setPlayWhenReady(true);
        videoPlayer.getPlaybackState();
    }

    public void releasePlayer() {
        if (videoPlayer != null) {
            videoPlayer.release();
            videoPlayer = null;
        }
        viewHolderParent = null;
    }


    private void setVolume(VolumeState state) {
        if (videoPlayer != null) {
            switch (state) {
                case OFF:
                    videoPlayer.setVolume(0f);
                    soundOn = false;
                    break;
                case ON:
                    videoPlayer.setVolume(1f);
                    soundOn = true;
                    break;
            }
        }
    }


    private void animateVideoPlyIcon(boolean videoPlaying) {
        if (videoPlayIcon != null) {

            videoPlayIcon.bringToFront();

            if (videoPlaying)
                videoPlayIcon.setBackground(videoPlayIcon.getContext().getResources().getDrawable(R.drawable.icon_pause, null));
            else
                videoPlayIcon.setBackground(videoPlayIcon.getContext().getResources().getDrawable(R.drawable.icon_play, null));

            videoPlayIcon.animate().cancel();

            videoPlayIcon.setAlpha(1f);

            videoPlayIcon.animate().alpha(0f).setDuration(600).setStartDelay(1000);
        }
    }

    public void setPosts(List<PostResponse.Posts> posts) {
        this.posts = posts;
    }

    public void setHomeFragmentListener(HomeFragmentListener homeFragmentListener) {
        this.homeFragmentListener = homeFragmentListener;
    }

    public void setCustomProgressBar(CustomProgressBar customProgressBar) {
        this.customProgressBar = customProgressBar;
    }

}
