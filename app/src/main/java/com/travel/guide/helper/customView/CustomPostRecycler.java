package com.travel.guide.helper.customView;

import android.animation.Animator;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.travel.guide.R;
import com.travel.guide.enums.SearchPostType;
import com.travel.guide.enums.StoryEmotionType;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.ui.home.home.HashtagAdapter;
import com.travel.guide.ui.home.home.HomeFragmentListener;
import com.travel.guide.ui.searchPost.SearchPostActivity;
import com.travel.guide.utility.GlobalPreferences;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomPostRecycler extends RecyclerView {

    private static final String TAG = "VideoPlayerRecyclerView";

    private enum VolumeState {ON, OFF}

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
    private VolumeState volumeState;

    private HomeFragmentListener homeFragmentListener;
    private CustomProgressBar customProgressBar;

    /// holder ui
    public ImageView storyCover, volumeControl;
    public View parent;
    public RequestManager requestManager;
//    public HomeFragmentListener listener;

    public TextView nickName, description, musicName, location, storyLikes, storyComments, storyShares, storyFavorites;
    public CircleImageView profileImage;
    public ImageButton like, follow, share, favorite, comment, menu;
    public RecyclerView hashtagRecycler;

    public FrameLayout media_container;
//    public PostResponse.Posts post;

    public int ownerUserId;
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
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        videoSurfaceDefaultHeight = point.x;
        screenDefaultHeight = point.y;

        videoSurfaceView = new PlayerView(this.context);
//        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
//        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
//        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        videoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        // Bind the player to the view.
        videoSurfaceView.setUseController(false);
        videoSurfaceView.setPlayer(videoPlayer);
        setVolumeControl(VolumeState.ON);

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {

                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:

                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        customProgressBar.stop(true);
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        customProgressBar.stop(false);

//                        Log.d(TAG, "onScrollStateChanged: called.");
                        if (thumbnail != null) {
//                        thumbnail.setVisibility(VISIBLE);
                        }
                        customProgressBar.removeAllViews();
                        customProgressBar.setStorySize(1);
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
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_BUFFERING:
//                        Log.e(TAG, "onPlayerStateChanged: Buffering video.");
                        if (progressBar != null) {
                            progressBar.setVisibility(VISIBLE);
                        }
                        break;

                    case Player.STATE_ENDED:
//                        Log.d(TAG, "onPlayerStateChanged: Video ended.");
                        videoPlayer.seekTo(0);
                        customProgressBar.start(0, posts.get(playingPosition).getPost_stories().get(0).getSecond());
                        break;

                    case Player.STATE_IDLE:
//                        Log.d(TAG, "onPlayerStateChanged: Idle here");
                        break;

                    case Player.STATE_READY:
//                        Log.e(TAG, "onPlayerStateChanged: Ready to play.");
                        if (progressBar != null) {
                            progressBar.setVisibility(GONE);
                        }
                        customProgressBar.start(0, posts.get(playingPosition).getPost_stories().get(0).getSecond());
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

        thumbnail = holder.storyCover;
//        progressBar = holder.progressBar;
        volumeControl = holder.volumeControl;
        viewHolderParent = holder.itemView;
        frameLayout = holder.itemView.findViewById(R.id.pl_container);

        videoSurfaceView.setPlayer(videoPlayer);

//            viewHolderParent.setOnClickListener(v -> toggleVolume());

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
        homeFragmentListener.stopLoader();
        if (mediaUrl != null) {
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mediaUrl));
            videoPlayer.prepare(videoSource);
            videoPlayer.setPlayWhenReady(true);
        }
    }


    private int getVisibleVideoSurfaceHeight(int playPosition) {
        int at = playPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        Log.d(TAG, "getVisibleVideoSurfaceHeight: at: " + at);

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
            ViewGroup parent = (ViewGroup) videoView.getParent();
            if (parent == null) {
                return;
            }

            int index = parent.indexOfChild(videoView);
            if (index >= 0) {
                parent.removeViewAt(index);
                isVideoViewAdded = false;
                viewHolderParent.setOnClickListener(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindItem(CustomPostHolder holder, PostResponse.Posts post) {
        try {
            storyCover = holder.storyCover;
            nickName = holder.nickName;
            description = holder.description;
            musicName = holder.musicName;
            storyLikes = holder.storyLikes;
            storyComments = holder.storyComments;
            storyShares = holder.storyShares;
            storyFavorites = holder.storyFavorites;
            hashtagRecycler = holder.hashtagRecycler;

            favorite = holder.favorite;
            favorite.setOnClickListener(v -> {
                homeFragmentListener.onFavoriteChoose(post.getPost_id(), position);
                setStoryEmotion(StoryEmotionType.FAVORITE, post);

            });

            menu = holder.menu;
            menu.setOnClickListener(v -> holder.showMenu(menu));

            share = holder.share;
            share.setOnClickListener(v -> homeFragmentListener.onShareChoose(post.getPost_share_url(), post.getPost_id()));

            like = holder.like;
            like.setOnClickListener(v -> {
                homeFragmentListener.onStoryLikeChoose(post.getPost_id(), post.getPost_stories().get(0).getStory_id(), position);
                setStoryEmotion(StoryEmotionType.LIKE, post);
            });

            follow = holder.follow;
            follow.setOnClickListener(v -> {
                homeFragmentListener.onFollowChoose(post.getUser_id());
                setStoryEmotion(StoryEmotionType.FOLLOW, post);
            });

            comment = holder.comment;
            comment.setOnClickListener(v -> homeFragmentListener.onCommentChoose(post.getPost_stories().get(0).getStory_id(), post.getPost_id()));

            location = holder.location;
            location.setOnClickListener(v -> {
                Intent postHashtagIntent = new Intent(context, SearchPostActivity.class);
                postHashtagIntent.putExtra("search_type", SearchPostType.LOCATION);
                postHashtagIntent.putExtra("search_post_id", post.getPost_id());
                like.getContext().startActivity(postHashtagIntent);
            });

            profileImage = holder.profileImage;
            profileImage.setOnClickListener(v -> homeFragmentListener.onUserChoose(post.getUser_id()));

            ownerUserId = GlobalPreferences.getUserId(like.getContext());

            ///Bind view
            if (post.getPost_stories().get(0).getStory_like_by_me())
                like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
            else
                like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));


            if (post.getI_follow_post_owner())
                follow.setVisibility(View.GONE);
            else
                follow.setVisibility(View.VISIBLE);

            if (post.getUser_id() == ownerUserId)
                follow.setVisibility(View.GONE);
            else
                follow.setVisibility(VISIBLE);


            if (post.getI_favor_post())
                favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
            else
                favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link, null));


            if (post.getDescription().isEmpty()) {
                description.setVisibility(View.GONE);
            } else {
                description.setVisibility(View.VISIBLE);
                description.setText(post.getDescription());
            }

            if (post.getHashtags().size() > 0) {
                hashtagRecycler.setLayoutManager(new LinearLayoutManager(share.getContext(), RecyclerView.HORIZONTAL, false));
                hashtagRecycler.setAdapter(new HashtagAdapter(post.getHashtags()));
                hashtagRecycler.setVisibility(View.VISIBLE);
            } else {
                hashtagRecycler.setVisibility(View.GONE);
            }

            if (post.getPost_locations().size() != 0) {
                location.setVisibility(VISIBLE);
                location.setText(post.getPost_locations().get(0).getAddress());
            } else {
                location.setText("");
                location.setVisibility(GONE);
            }


            if (GlobalPreferences.getUserId(like.getContext()) == post.getUser_id())
                menu.setVisibility(View.VISIBLE);
            else
                menu.setVisibility(View.GONE);

            nickName.setText(post.getNickname());
            storyShares.setText(String.valueOf(post.getPost_shares()));
            storyFavorites.setText(String.valueOf(post.getPost_favorites()));
            storyLikes.setText(String.valueOf(post.getPost_stories().get(0).getStory_likes()));
            storyComments.setText(String.valueOf(post.getPost_stories().get(0).getStory_comments()));

            musicName.setText(post.getMusic_text());
            musicName.setSelected(true);

            HelperMedia.loadCirclePhoto(profileImage.getContext(), post.getProfile_pic(), profileImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStoryEmotion(StoryEmotionType storyEmotionType, PostResponse.Posts post) {
        switch (storyEmotionType) {
            case LIKE:
                if (post.getPost_stories().get(0).getStory_like_by_me()) {
                    if (countLikeUp > post.getPost_stories().get(0).getStory_likes()) {
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null)))
                                .duration(250)
                                .playOn(like);
                        storyLikes.setText(String.valueOf(post.getPost_stories().get(0).getStory_likes()));
                        post.getPost_stories().get(0).setStory_like_by_me(false);
                    } else {
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null)))
                                .duration(250)
                                .playOn(like);
                        storyLikes.setText(String.valueOf(post.getPost_stories().get(0).getStory_likes() - 1));
                        countLikeDown = post.getPost_stories().get(0).getStory_likes() - 1;
                        post.getPost_stories().get(0).setStory_like_by_me(false);
                    }

                } else {

                    if (countLikeDown < post.getPost_stories().get(0).getStory_likes()) {
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null)))
                                .duration(250)
                                .playOn(like);
                        storyLikes.setText(String.valueOf(post.getPost_stories().get(0).getStory_likes()));
                        post.getPost_stories().get(0).setStory_like_by_me(true);
                    } else {
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null)))
                                .duration(250)
                                .playOn(like);
                        storyLikes.setText(String.valueOf(post.getPost_stories().get(0).getStory_likes() + 1));
                        countLikeUp = post.getPost_stories().get(0).getStory_likes() + 1;
                        post.getPost_stories().get(0).setStory_like_by_me(true);
                    }
                }

                break;

            case FAVORITE:
                if (post.getI_favor_post()) {
                    if (countFavoriteUp > post.getPost_favorites()) {
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link, null)))
                                .duration(250)
                                .playOn(favorite);
                        storyFavorites.setText(String.valueOf(post.getPost_favorites()));
                        post.setI_favor_post(false);
                    } else {
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link, null)))
                                .duration(250)
                                .playOn(favorite);
                        storyFavorites.setText(String.valueOf(post.getPost_favorites() - 1));
                        countFavoriteDown = post.getPost_favorites() - 1;
                        post.setI_favor_post(false);
                    }

                } else {
                    if (countFavoriteDown < post.getPost_favorites()) {
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null)))
                                .duration(250)
                                .playOn(favorite);
                        storyFavorites.setText(String.valueOf(post.getPost_favorites()));
                        post.setI_favor_post(true);
                    } else {
                        YoYo.with(Techniques.RubberBand)
                                .onEnd(animator -> favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null)))
                                .duration(250)
                                .playOn(favorite);
                        storyFavorites.setText(String.valueOf(post.getPost_favorites() + 1));
                        countFavoriteUp = post.getPost_favorites() + 1;
                        post.setI_favor_post(true);
                    }

                }
                break;

            case FOLLOW:
                if (!post.getI_follow_post_owner()) {
                    YoYo.with(Techniques.FadeOut)
                            .onEnd(animator -> follow.setVisibility(GONE))
                            .duration(250)
                            .playOn(follow);
                    post.setI_follow_post_owner(true);
                }
                break;
        }


    }

    private void animateBtn(View target, StoryEmotionType storyEmotionType) {
        switch (storyEmotionType) {
            case FAVORITE:
                YoYo.with(Techniques.RubberBand)
                        .onEnd(new YoYo.AnimatorCallback() {
                            @Override
                            public void call(Animator animator) {

                            }
                        })
                        .duration(250)
                        .playOn(target);
                break;
            case FOLLOW:
                break;
            case LIKE:
                break;
        }


//        Animation anim = new ScaleAnimation(
//                0f, 1f, // Start and end values for the X axis scaling
//                0f, 1f, // Start and end values for the Y axis scaling
//                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
//                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
//        anim.setFillAfter(true); // Needed to keep the result of the animation
//        anim.setDuration(250);
//        button.startAnimation(anim);
//        text.startAnimation(anim);
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
//        thumbnail.setVisibility(GONE);
    }

    private void resetVideoView() {
        if (isVideoViewAdded) {
            removeVideoView(videoSurfaceView);
            playPosition = -1;
            videoSurfaceView.setVisibility(INVISIBLE);
//            thumbnail.setVisibility(VISIBLE);
        }
    }

    public void releasePlayer() {

        if (videoPlayer != null) {
            videoPlayer.release();
            videoPlayer = null;
        }

        viewHolderParent = null;
    }

    private void toggleVolume() {
        if (videoPlayer != null) {
            if (volumeState == VolumeState.OFF) {
                Log.d(TAG, "togglePlaybackState: enabling volume.");
                setVolumeControl(VolumeState.ON);

            } else if (volumeState == VolumeState.ON) {
                Log.d(TAG, "togglePlaybackState: disabling volume.");
                setVolumeControl(VolumeState.OFF);
            }
        }
    }

    private void setVolumeControl(VolumeState state) {
        volumeState = state;
        if (state == VolumeState.OFF) {
            videoPlayer.setVolume(0f);
            animateVolumeControl();
        } else if (state == VolumeState.ON) {
            videoPlayer.setVolume(1f);
            animateVolumeControl();
        }
    }

    private void animateVolumeControl() {
        if (volumeControl != null) {
            volumeControl.bringToFront();
            if (volumeState == VolumeState.OFF) {
//                requestManager.load(R.drawable.ic_volume_off_grey_24dp).into(volumeControl);
            } else if (volumeState == VolumeState.ON) {
//                requestManager.load(R.drawable.ic_volume_up_grey_24dp).into(volumeControl);
            }
            volumeControl.animate().cancel();

            volumeControl.setAlpha(1f);

            volumeControl.animate().alpha(0f).setDuration(600).setStartDelay(1000);
        }
    }

    public void setPosts(List<PostResponse.Posts> posts) {
//        if (this.posts != null && this.posts.size() != 0)
//            this.posts.addAll(posts);
//        else {
//            this.posts = posts;
//        }
        this.posts = posts;
    }

    public void setHomeFragmentListener(HomeFragmentListener homeFragmentListener) {
        this.homeFragmentListener = homeFragmentListener;
    }

    public void setCustomProgressBar(CustomProgressBar customProgressBar) {
        this.customProgressBar = customProgressBar;
    }

}
