package com.example.travelguide.ui.home.adapter.recycler;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.ui.home.activity.CustomerProfileActivity;
import com.example.travelguide.ui.home.activity.PostsByLocationActivity;
import com.example.travelguide.ui.home.interfaces.OnLoadFinishListener;
import com.example.travelguide.model.response.PostResponse;

import java.util.List;

import co.hkm.soltag.TagContainerLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoryRecyclerAdapter extends RecyclerView.Adapter<StoryRecyclerAdapter.StoryHolder> {

    private List<PostResponse.Post_stories> stories;
    private PostResponse.Posts currentPost;
    private OnLoadFinishListener onLoadFinishListener;

    StoryRecyclerAdapter(OnLoadFinishListener onLoadFinishListener, PostResponse.Posts currentPost) {
        this.onLoadFinishListener = onLoadFinishListener;
        this.currentPost = currentPost;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StoryHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_story, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder viewHolder, int position) {
        onLoadFinishListener.stopLoader();
        viewHolder.nickName.setText(currentPost.getNickname());
        viewHolder.description.setText(currentPost.getDescription());

        if (currentPost.getPost_stories().get(position).getStory_like_by_me() == 1) {
            viewHolder.like.setBackground(viewHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
        } else {
            viewHolder.like.setBackground(viewHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
        }

        if (currentPost.getI_follow_post_owner() == 1) {
            viewHolder.follow.setVisibility(View.GONE);
        } else {
            viewHolder.follow.setVisibility(View.VISIBLE);
        }

        viewHolder.storyLikes.setText(String.valueOf(currentPost.getPost_stories().get(position).getStory_likes()));
        viewHolder.storyComments.setText(String.valueOf(currentPost.getPost_stories().get(position).getStory_comments()));
        viewHolder.storyShares.setText(String.valueOf(currentPost.getPost_shares()));
        viewHolder.storyFavorites.setText(String.valueOf(currentPost.getPost_favorites()));
        viewHolder.musicName.setText(currentPost.getMusic_text());
        viewHolder.musicName.setSelected(true);
        viewHolder.tagContainerLayout.setTags(currentPost.getHashtags());
        viewHolder.location.setText(currentPost.getPost_locations().get(0).getAddress());
        HelperMedia.loadCirclePhoto(viewHolder.profileImage.getContext(), currentPost.getProfile_pic(), viewHolder.profileImage);
        viewHolder.setVideoItem();

    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    void setStories(List<PostResponse.Post_stories> stories) {
        this.stories = stories;
        notifyDataSetChanged();
    }

    class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View parent;
        VideoView videoItem;
        TextView nickName, description, musicName, location, storyLikes, storyComments, storyShares, storyFavorites;
        CircleImageView profileImage;
        ImageButton like, follow, share, favorite;
        TagContainerLayout tagContainerLayout;

        StoryHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            nickName = itemView.findViewById(R.id.nickname_post);
            videoItem = itemView.findViewById(R.id.scalable_video);
            description = itemView.findViewById(R.id.post_description);
            musicName = itemView.findViewById(R.id.music_name_post);
            storyLikes = itemView.findViewById(R.id.story_like_count);
            storyComments = itemView.findViewById(R.id.story_comment_count);
            storyShares = itemView.findViewById(R.id.story_share_count);
            storyFavorites = itemView.findViewById(R.id.story_favorites_count);

            favorite = itemView.findViewById(R.id.story_favorites);
            favorite.setOnClickListener(this);

            share = itemView.findViewById(R.id.story_share);
            share.setOnClickListener(this);

            like = itemView.findViewById(R.id.story_like);
            like.setOnClickListener(this);

            follow = itemView.findViewById(R.id.story_follow_btn);
            follow.setOnClickListener(this);

            location = itemView.findViewById(R.id.post_location);
            location.setOnClickListener(this);

            profileImage = itemView.findViewById(R.id.user_image_post);
            profileImage.setOnClickListener(this);

            tagContainerLayout = itemView.findViewById(R.id.hashtags);
        }

        void setVideoItem() {
            videoItem.setVideoURI(Uri.parse(stories.get(getLayoutPosition()).getUrl()));
            videoItem.requestFocus();
            videoItem.setOnPreparedListener(mp -> {
//                        holder.videoItem.setMediaController(new MediaController(context));
                mp.setLooping(true);
//                mp.setVolume(0, 0);
                videoItem.start();
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
                    Intent postLocationIntent = new Intent(videoItem.getContext(), PostsByLocationActivity.class);
                    postLocationIntent.putExtra("post_id", currentPost.getPost_id());
                    videoItem.getContext().startActivity(postLocationIntent);
                    break;

                case R.id.story_like:
                    if (currentPost.getPost_stories().get(getLayoutPosition()).getStory_like_by_me() == 1)
                        like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
                    else
                        like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));

                    onLoadFinishListener.onStoryLikeChoose(currentPost.getPost_id(),
                            currentPost.getPost_stories().get(getLayoutPosition()).getStory_id());
                    break;

                case R.id.story_follow_btn:
                    onLoadFinishListener.onFollowChoose(currentPost.getUser_id());
                    break;

                case R.id.story_share:
                    onLoadFinishListener.onShareChoose(currentPost.getPost_share_url());
                    break;

                case R.id.story_favorites:
                    onLoadFinishListener.onFavoriteChoose(currentPost.getPost_id());
                    break;
            }
        }
    }
}