package com.travel.guide.ui.home.home;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.travel.guide.R;
import com.travel.guide.enums.SearchPostType;
import com.travel.guide.enums.StoryEmotionType;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.helper.customView.CustomFrameLayout;
import com.travel.guide.helper.customView.CustomProgressBar;
import com.travel.guide.ui.searchPost.SearchPostActivity;
import com.travel.guide.model.response.PostResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder> {

    private List<PostResponse.Post_stories> stories;
    private PostResponse.Posts currentPost;
    private HomeFragmentListener homeFragmentListener;
    private int currentPosition = -1;

    private StoryPlayingListener storyHolderListener;

    private CustomProgressBar customProgressBar;

    StoryAdapter(HomeFragmentListener homeFragmentListener, StoryPlayingListener storyHolderListener) {
        this.homeFragmentListener = homeFragmentListener;
        this.storyHolderListener = storyHolderListener;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new StoryHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_story, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder storyHolder, int position) {

//        storyHolder.playVideo(stories.get(position).getUrl());
//        storyHolderListener.onGetStoryHolder(storyHolder, position);
//        HelperMedia.loadPhoto(storyHolder.like.getContext(), currentPost.getCover(), storyHolder.storyCover);
        storyHolder.setVideoItem(position);

        if (stories.get(position).getStory_like_by_me())
            storyHolder.like.setBackground(storyHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
        else
            storyHolder.like.setBackground(storyHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));


        if (currentPost.getI_follow_post_owner())
            storyHolder.follow.setVisibility(View.GONE);
        else
            storyHolder.follow.setVisibility(View.VISIBLE);

        if (currentPost.getUser_id() == storyHolder.ownerUserId)
            storyHolder.follow.setVisibility(View.GONE);


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

    void setCustomProgressBar(CustomProgressBar customProgressBar) {
        this.customProgressBar = customProgressBar;
    }

    void setCurrentPost(PostResponse.Posts currentPost) {
        this.currentPost = currentPost;
    }

    class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        VideoView videoItem;
        TextView nickName, description, musicName, location, storyLikes, storyComments, storyShares, storyFavorites;
        CircleImageView profileImage;
        ImageButton like, follow, share, favorite, comment;
        ImageView storyCover;
        RecyclerView hashtagRecycler;

        CustomFrameLayout frameLayout;
        PlayerView playerView;
        ExoPlayer exoPlayer;

        int countLikeUp = -1;
        int countLikeDown = Integer.MAX_VALUE;

        int countFavoriteUp = -1;
        int countFavoriteDown = Integer.MAX_VALUE;

        int ownerUserId;

        StoryHolder(@NonNull View itemView) {
            super(itemView);

            storyCover = itemView.findViewById(R.id.story_cover_photo);
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

            ownerUserId = HelperPref.getUserId(like.getContext());

            frameLayout = itemView.findViewById(R.id.pl_container);

//            exoPlayer = HelperExoPlayer.getExoPlayer(like.getContext());
//
//            playerView = new PlayerView(like.getContext());
//            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
//            playerView.setUseController(false);
//            playerView.requestFocus();
//            playerView.setVisibility(View.VISIBLE);
//            playerView.setAlpha(1);

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
                customProgressBar.start(position, duration);

            });

        }

        void playVideo(String url) {
            frameLayout.addPlayerView();
            frameLayout.playRequest(url);

//            MediaSource mediaSource = HelperExoPlayer.getMediaLink(stories.get(position).getUrl());
//            frameLayout.getPlayerView().setPlayer(exoPlayer);
//            exoPlayer.prepare(mediaSource);
//            exoPlayer.setPlayWhenReady(true);
//            exoPlayer.addListener(new Player.EventListener() {
//                @Override
//                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                    switch (playbackState) {
//                        case Player.STATE_ENDED:
//                            exoPlayer.seekTo(0);
//                            break;
//                        case Player.STATE_READY:
////                            homeFragmentListener.onExoPlayerReady();
//                            break;
//                    }
//                }
//            });


        }

        void stopVideo() {
            storyCover.setVisibility(View.VISIBLE);
            HelperMedia.loadPhoto(like.getContext(), currentPost.getCover(), storyCover);
            playerView.setVisibility(View.GONE);
            frameLayout.removeView(playerView);
            playerView = null;
            if (exoPlayer != null) {
                exoPlayer.release();
                exoPlayer = null;
            }

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.user_image_post:
                    homeFragmentListener.onUserChoose(currentPost.getUser_id());
                    break;

                case R.id.post_location:
                    Intent postHashtagIntent = new Intent(videoItem.getContext(), SearchPostActivity.class);
                    postHashtagIntent.putExtra("search_type", SearchPostType.LOCATION);
                    postHashtagIntent.putExtra("search_post_id", currentPost.getPost_id());
                    like.getContext().startActivity(postHashtagIntent);
                    break;

                case R.id.story_like:
                    homeFragmentListener.onStoryLikeChoose(currentPost.getPost_id(), currentPost.getPost_stories().get(getLayoutPosition()).getStory_id(), getLayoutPosition());
                    setStoryEmotion(getLayoutPosition(), StoryEmotionType.LIKE);
                    break;

                case R.id.story_follow_btn:
                    homeFragmentListener.onFollowChoose(currentPost.getUser_id());
                    setStoryEmotion(getLayoutPosition(), StoryEmotionType.FOLLOW);
                    break;

                case R.id.story_share:
                    homeFragmentListener.onShareChoose(currentPost.getPost_share_url(), currentPost.getPost_id());
                    break;

                case R.id.story_favorites:
                    homeFragmentListener.onFavoriteChoose(currentPost.getPost_id(), getLayoutPosition());
                    setStoryEmotion(getLayoutPosition(), StoryEmotionType.FAVORITE);
                    break;

                case R.id.story_comment:
                    homeFragmentListener.onCommentChoose(stories.get(getLayoutPosition()).getStory_id(), currentPost.getPost_id());
                    break;
            }

        }

        void setStoryEmotion(int position, StoryEmotionType storyEmotionType) {

            switch (storyEmotionType) {
                case LIKE:
                    if (stories.get(position).getStory_like_by_me()) {

                        if (countLikeUp > stories.get(position).getStory_likes()) {
                            like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
                            storyLikes.setText(String.valueOf(stories.get(position).getStory_likes()));
                            stories.get(position).setStory_like_by_me(false);
                        } else {
                            storyLikes.setText(String.valueOf(stories.get(position).getStory_likes() - 1));
                            like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
                            countLikeDown = stories.get(position).getStory_likes() - 1;
                            stories.get(position).setStory_like_by_me(false);
                        }

                    } else {

                        if (countLikeDown < stories.get(position).getStory_likes()) {
                            like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
                            storyLikes.setText(String.valueOf(stories.get(position).getStory_likes()));
                            stories.get(position).setStory_like_by_me(true);
                        } else {
                            like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
                            storyLikes.setText(String.valueOf(stories.get(position).getStory_likes() + 1));
                            countLikeUp = stories.get(position).getStory_likes() + 1;
                            stories.get(position).setStory_like_by_me(true);
                        }
                    }

                    break;

                case FAVORITE:
                    if (currentPost.getI_favor_post()) {

                        if (countFavoriteUp > currentPost.getPost_favorites()) {
                            favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link, null));
                            storyFavorites.setText(String.valueOf(currentPost.getPost_favorites()));
                            currentPost.setI_favor_post(false);
                        } else {
                            favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link, null));
                            storyFavorites.setText(String.valueOf(currentPost.getPost_favorites() - 1));
                            countLikeDown = currentPost.getPost_favorites() - 1;
                            currentPost.setI_favor_post(false);
                        }

                    } else {

                        if (countFavoriteDown < currentPost.getPost_favorites()) {
                            favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
                            storyFavorites.setText(String.valueOf(currentPost.getPost_favorites()));
                            currentPost.setI_favor_post(true);
                        } else {
                            favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
                            storyFavorites.setText(String.valueOf(currentPost.getPost_favorites() + 1));
                            countLikeDown = currentPost.getPost_favorites() + 1;
                            currentPost.setI_favor_post(true);
                        }

                    }
                    break;

                case FOLLOW:

                    if (!currentPost.getI_follow_post_owner()) {
                        follow.setVisibility(View.GONE);
                        currentPost.setI_follow_post_owner(true);
                    }
                    break;
            }


        }

    }
}