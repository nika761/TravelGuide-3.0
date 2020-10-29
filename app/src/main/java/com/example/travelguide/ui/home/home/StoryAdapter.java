package com.example.travelguide.ui.home.home;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.allyants.chipview.ChipView;
import com.allyants.chipview.SimpleChipAdapter;
import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.helper.StoryView;
import com.example.travelguide.ui.customerUser.CustomerProfileActivity;
import com.example.travelguide.ui.searchPost.SearchPostActivity;
import com.example.travelguide.model.response.PostResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import co.hkm.soltag.TagContainerLayout;
import co.hkm.soltag.TagView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.travelguide.helper.HelperUI.UI_HASHTAG;
import static com.example.travelguide.helper.HelperUI.UI_LOCATION;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder> {

    private List<PostResponse.Post_stories> stories;
    private PostResponse.Posts currentPost;
    private HomeFragmentListener homeFragmentListener;

    private StoryView storyView;

    StoryAdapter(HomeFragmentListener homeFragmentListener) {
        this.homeFragmentListener = homeFragmentListener;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StoryHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_story, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder storyHolder, int position) {

        storyHolder.setVideoItem();

        storyHolder.nickName.setText(currentPost.getNickname());

        if (currentPost.getDescription().isEmpty()) {
            storyHolder.description.setVisibility(View.GONE);
        } else {
            storyHolder.description.setVisibility(View.VISIBLE);
            storyHolder.description.setText(currentPost.getDescription());
        }

        if (stories.get(position).getStory_like_by_me()) {
            storyHolder.like.setBackground(storyHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
        } else {
            storyHolder.like.setBackground(storyHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
        }

        if (currentPost.getI_follow_post_owner()) {
            storyHolder.follow.setVisibility(View.GONE);
        } else {
            storyHolder.follow.setVisibility(View.VISIBLE);
        }

        if (currentPost.getI_favor_post()) {
            storyHolder.favorite.setBackground(storyHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
        } else {
            storyHolder.favorite.setBackground(storyHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_link, null));
        }

        if (currentPost.getHashtags().size() > 0) {
            storyHolder.setHashtagRecycler();
            storyHolder.hashtagRecycler.setVisibility(View.VISIBLE);
        } else {
            storyHolder.hashtagRecycler.setVisibility(View.GONE);
        }

        storyHolder.storyLikes.setText(String.valueOf(stories.get(position).getStory_likes()));
        storyHolder.storyComments.setText(String.valueOf(stories.get(position).getStory_comments()));
        storyHolder.storyShares.setText(String.valueOf(currentPost.getPost_shares()));
        storyHolder.storyFavorites.setText(String.valueOf(currentPost.getPost_favorites()));

        storyHolder.musicName.setText(currentPost.getMusic_text());
        storyHolder.musicName.setSelected(true);

        if (currentPost.getPost_locations().size() != 0)
            storyHolder.location.setText(currentPost.getPost_locations().get(0).getAddress());
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

        StoryHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
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

        }

        void setHashtagRecycler() {
            hashtagRecycler.setLayoutManager(new LinearLayoutManager(share.getContext(), RecyclerView.HORIZONTAL, false));
            hashtagRecycler.setAdapter(new HashtagAdapter(currentPost.getHashtags()));
        }

        void setVideoItem() {
            videoItem.setVideoURI(Uri.parse(stories.get(getLayoutPosition()).getUrl()));
            videoItem.requestFocus();
            videoItem.setOnPreparedListener(mp -> {

                mp.setLooping(true);
                videoItem.start();
                int duration = currentPost.getPost_stories().get(0).getSecond();
                storyView.start(0, duration);

            });

        }


        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.user_image_post:
                    Intent intent = new Intent(videoItem.getContext(), CustomerProfileActivity.class);
                    intent.putExtra("id", currentPost.getUser_id());
                    videoItem.getContext().startActivity(intent);
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

                    if (stories.get(getLayoutPosition()).getStory_like_by_me()) {
                        like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
                        storyLikes.setText(String.valueOf(stories.get(getLayoutPosition()).getStory_likes()));
                        stories.get(getLayoutPosition()).setStory_like_by_me(false);

                    } else {
                        like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
                        storyLikes.setText(String.valueOf(stories.get(getLayoutPosition()).getStory_likes() + 1));
                        stories.get(getLayoutPosition()).setStory_like_by_me(true);
                    }

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
    }
}