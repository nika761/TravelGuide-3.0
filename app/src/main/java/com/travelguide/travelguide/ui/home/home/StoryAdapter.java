package com.travelguide.travelguide.ui.home.home;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travelguide.travelguide.R;
import com.travelguide.travelguide.helper.HelperMedia;
import com.travelguide.travelguide.helper.StoryView;
import com.travelguide.travelguide.ui.customerUser.CustomerProfileActivity;
import com.travelguide.travelguide.ui.searchPost.SearchPostActivity;
import com.travelguide.travelguide.model.response.PostResponse;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.VISIBLE;
import static com.travelguide.travelguide.helper.HelperUI.UI_LOCATION;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder> {

    private List<PostResponse.Post_stories> stories;
    private PostResponse.Posts currentPost;
    private HomeFragmentListener homeFragmentListener;
    private int currentPosition = -1;

    private StoryView storyView;

    StoryAdapter(HomeFragmentListener homeFragmentListener) {
        this.homeFragmentListener = homeFragmentListener;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new StoryHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_story, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder storyHolder, int position) {

//        storyHolder.onPlayStory(stories.get(position).getUrl());
        storyHolder.setVideoItem(position);

        if (stories.get(position).getStory_like_by_me())
            storyHolder.like.setBackground(storyHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
        else
            storyHolder.like.setBackground(storyHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));

        if (currentPost.getI_follow_post_owner())
            storyHolder.follow.setVisibility(View.GONE);
        else
            storyHolder.follow.setVisibility(View.VISIBLE);

        if (currentPost.getI_favor_post())
            storyHolder.favorite.setBackground(storyHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
        else
            storyHolder.favorite.setBackground(storyHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_link, null));


        if (currentPost.getDescription().isEmpty()) {
            storyHolder.description.setVisibility(View.GONE);
        } else {
            storyHolder.description.setVisibility(View.VISIBLE);
            storyHolder.description.setText(currentPost.getDescription());
        }


        if (currentPost.getHashtags().size() > 0) {
            storyHolder.setHashtagRecycler();
            storyHolder.hashtagRecycler.setVisibility(View.VISIBLE);
        } else {
            storyHolder.hashtagRecycler.setVisibility(View.GONE);
        }

        if (currentPost.getPost_locations().size() != 0)
            storyHolder.location.setText(currentPost.getPost_locations().get(0).getAddress());


        storyHolder.nickName.setText(currentPost.getNickname());
        storyHolder.storyShares.setText(String.valueOf(currentPost.getPost_shares()));
        storyHolder.storyFavorites.setText(String.valueOf(currentPost.getPost_favorites()));
        storyHolder.storyLikes.setText(String.valueOf(stories.get(position).getStory_likes()));
        storyHolder.storyComments.setText(String.valueOf(stories.get(position).getStory_comments()));

        storyHolder.musicName.setText(currentPost.getMusic_text());
        storyHolder.musicName.setSelected(true);

        HelperMedia.loadCirclePhoto(storyHolder.profileImage.getContext(), currentPost.getProfile_pic(), storyHolder.profileImage);

        homeFragmentListener.stopLoader();

    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    void setStories(List<PostResponse.Post_stories> stories) {
        this.stories = stories;
        notifyDataSetChanged();
    }

    void setStoryView(StoryView storyView) {
        this.storyView = storyView;
    }

    void setCurrentPost(PostResponse.Posts currentPost) {
        this.currentPost = currentPost;
    }

    class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View parent;
        VideoView videoItem;
        TextView nickName, description, musicName, location, storyLikes, storyComments, storyShares, storyFavorites;
        CircleImageView profileImage;
        ImageButton like, follow, share, favorite, comment;
        ImageView storyCover;
        RecyclerView hashtagRecycler;

        private boolean isVideoViewAdded;
        private FrameLayout frameLayout;

        int countPlus = -1;
        int countMinus = Integer.MAX_VALUE;

        StoryHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            storyCover = itemView.findViewById(R.id.story_cover_photo);

            frameLayout = itemView.findViewById(R.id.pl_container);
            nickName = itemView.findViewById(R.id.nickname_post);
            videoItem = itemView.findViewById(R.id.scalable_video);
            description = itemView.findViewById(R.id.post_description);
            musicName = itemView.findViewById(R.id.music_name_post);
            storyLikes = itemView.findViewById(R.id.story_like_count);
            storyComments = itemView.findViewById(R.id.story_comment_count);
            storyShares = itemView.findViewById(R.id.story_share_count);
            storyFavorites = itemView.findViewById(R.id.story_favorites_count);
            hashtagRecycler = itemView.findViewById(R.id.hashtag_recycler);

            favorite = itemView.findViewById(R.id.story_favorites);
            favorite.setOnClickListener(this);

            share = itemView.findViewById(R.id.story_share);
            share.setOnClickListener(this);

            like = itemView.findViewById(R.id.story_like);
            like.setOnClickListener(this);

            follow = itemView.findViewById(R.id.story_follow_btn);
            follow.setOnClickListener(this);

            comment = itemView.findViewById(R.id.story_comment);
            comment.setOnClickListener(this);

            location = itemView.findViewById(R.id.post_location);
            location.setOnClickListener(this);

            profileImage = itemView.findViewById(R.id.user_image_post);
            profileImage.setOnClickListener(this);

        }

        void setHashtagRecycler() {
            hashtagRecycler.setLayoutManager(new LinearLayoutManager(share.getContext(), RecyclerView.HORIZONTAL, false));
            hashtagRecycler.setAdapter(new HashtagAdapter(currentPost.getHashtags()));
        }

        void setVideoItem(int position) {
            videoItem.setVideoURI(Uri.parse(stories.get(position).getUrl()));
            videoItem.requestFocus();
            videoItem.setOnPreparedListener(mp -> {

                mp.setLooping(true);
                videoItem.start();
                int duration = stories.get(position).getSecond();
                storyView.start(position, duration);

            });

        }

//        public void onPlayStory(String url) {
//
//            LoadControl loadControl = new DefaultLoadControl();
//            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//
//            videoPlayer = ExoPlayerFactory.newSimpleInstance(postRecycler.getContext(), trackSelector, loadControl);
//
//            public void removeVideoView() {
//                frameLayout.removeView(playerView);
//                videoPlayer.release();
////            videoPlayer.release();
//                isVideoViewAdded = false;
//            }
//
//            private void addVideoView(PlayerView playerView) {
//                frameLayout.addView(playerView);
//                playerView.requestFocus();
//                playerView.setUseController(false);
//                playerView.setVisibility(VISIBLE);
//                playerView.setAlpha(1);
//                isVideoViewAdded = true;
//            }
//
//
//            playerView = new PlayerView(nickName.getContext());
//            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
//
//            addVideoView(playerView);
//
//            DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("Travel Guide");
//            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), factory, extractorsFactory, null, null);
//
//            playerView.setPlayer(videoPlayer);
//            playerView.setKeepScreenOn(true);
//
//            videoPlayer.prepare(mediaSource);
//            videoPlayer.setPlayWhenReady(true);
//
//            videoPlayer.addListener(new Player.EventListener() {
//                @Override
//                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                    switch (playbackState) {
//                        case Player.STATE_ENDED:
//                            videoPlayer.seekTo(0);
//                            break;
//                    }
//                }
//            });
//
//        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.user_image_post:
                    homeFragmentListener.onUserChoose(currentPost.getUser_id());
                    break;

                case R.id.post_location:
                    Intent postHashtagIntent = new Intent(videoItem.getContext(), SearchPostActivity.class);
                    postHashtagIntent.putExtra("search_type", UI_LOCATION);
                    postHashtagIntent.putExtra("search_post_id", currentPost.getPost_id());
                    videoItem.getContext().startActivity(postHashtagIntent);
                    break;

                case R.id.story_like:
                    homeFragmentListener.onStoryLikeChoose(currentPost.getPost_id(),
                            currentPost.getPost_stories().get(getLayoutPosition()).getStory_id(), getLayoutPosition());

                    setStoryLike(getLayoutPosition());

                    break;

                case R.id.story_follow_btn:
                    homeFragmentListener.onFollowChoose(currentPost.getUser_id());
                    break;

                case R.id.story_share:
                    homeFragmentListener.onShareChoose(currentPost.getPost_share_url(), currentPost.getPost_id());
                    break;

                case R.id.story_favorites:
                    homeFragmentListener.onFavoriteChoose(currentPost.getPost_id(), getLayoutPosition());

                    if (currentPost.getI_favor_post()) {
                        favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link, null));
                        storyFavorites.setText(String.valueOf(currentPost.getPost_favorites()));
                        currentPost.setI_favor_post(false);
                    } else {
                        favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
                        storyFavorites.setText(String.valueOf(currentPost.getPost_favorites() + 1));
                        currentPost.setI_favor_post(true);
                    }

                    break;

                case R.id.story_comment:
                    homeFragmentListener.onCommentChoose(stories.get(getLayoutPosition()).getStory_id(), currentPost.getPost_id());
                    break;
            }

        }

        void setStoryLike(int position) {

            if (stories.get(position).getStory_like_by_me()) {

                if (countPlus > stories.get(position).getStory_likes()) {
                    like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
                    storyLikes.setText(String.valueOf(stories.get(position).getStory_likes()));
                    stories.get(position).setStory_like_by_me(false);
                } else {
                    storyLikes.setText(String.valueOf(stories.get(position).getStory_likes() - 1));
                    like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
                    countMinus = stories.get(position).getStory_likes() - 1;
                    stories.get(position).setStory_like_by_me(false);
                }

            } else {

                if (countMinus < stories.get(position).getStory_likes()) {
                    like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
                    storyLikes.setText(String.valueOf(stories.get(position).getStory_likes()));
                    stories.get(position).setStory_like_by_me(true);
                } else {
                    like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
                    storyLikes.setText(String.valueOf(stories.get(position).getStory_likes() + 1));
                    countPlus = stories.get(position).getStory_likes() + 1;
                    stories.get(position).setStory_like_by_me(true);
                }
            }

        }

    }
}