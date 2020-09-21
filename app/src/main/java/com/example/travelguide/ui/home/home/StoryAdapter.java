package com.example.travelguide.ui.home.home;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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
import com.example.travelguide.ui.customerUser.CustomerProfileActivity;
import com.example.travelguide.ui.searchPost.SearchPostActivity;
import com.example.travelguide.model.response.PostResponse;

import java.util.List;

import co.hkm.soltag.TagContainerLayout;
import co.hkm.soltag.TagView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.travelguide.helper.HelperUI.UI_HASHTAG;
import static com.example.travelguide.helper.HelperUI.UI_LOCATION;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder> {

    private List<PostResponse.Post_stories> stories;
    private StoryHolder storyHolder;
    private PostResponse.Posts currentPost;
    private HomeFragmentListener homeFragmentListener;

    StoryAdapter(HomeFragmentListener homeFragmentListener) {
        this.homeFragmentListener = homeFragmentListener;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StoryHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_story, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder viewHolder, int position) {
        this.storyHolder = viewHolder;
        Log.e("postsdsdsd", currentPost.getPost_id() + "post id from story");

        viewHolder.nickName.setText(currentPost.getNickname());
        viewHolder.description.setText(currentPost.getDescription());

        if (stories.get(position).getStory_like_by_me()) {
            viewHolder.like.setBackground(viewHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
        } else {
            viewHolder.like.setBackground(viewHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
        }

        if (currentPost.getI_follow_post_owner()) {
            viewHolder.follow.setVisibility(View.GONE);
        } else {
            viewHolder.follow.setVisibility(View.VISIBLE);
        }

        if (currentPost.getI_favor_post()) {
            viewHolder.favorite.setBackground(viewHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
        } else {
            viewHolder.favorite.setBackground(viewHolder.like.getContext().getResources().getDrawable(R.drawable.emoji_link, null));
        }

        viewHolder.storyLikes.setText(String.valueOf(stories.get(position).getStory_likes()));
        viewHolder.storyComments.setText(String.valueOf(stories.get(position).getStory_comments()));
        viewHolder.storyShares.setText(String.valueOf(currentPost.getPost_shares()));
        viewHolder.storyFavorites.setText(String.valueOf(currentPost.getPost_favorites()));
        viewHolder.musicName.setText(currentPost.getMusic_text());
        viewHolder.musicName.setSelected(true);
        viewHolder.tagContainerLayout.setTags(currentPost.getHashtags());
        viewHolder.tagContainerLayout.setTagMaxLength(6);
        if (currentPost.getPost_locations().size() != 0)
            viewHolder.location.setText(currentPost.getPost_locations().get(0).getAddress());
        HelperMedia.loadCirclePhoto(viewHolder.profileImage.getContext(), currentPost.getProfile_pic(), viewHolder.profileImage);
        viewHolder.setVideoItem();
        homeFragmentListener.stopLoader();
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    void setStoryLike(int type, int position, int likeCounts) {
        switch (type) {
            case 0:
                storyHolder.like.setBackground(storyHolder.like.getContext().getResources().
                        getDrawable(R.drawable.emoji_heart_red, null));
                storyHolder.storyLikes.setText(String.valueOf(likeCounts));
                stories.get(position).setStory_like_by_me(true);
                stories.get(position).setStory_likes(likeCounts);
                notifyItemChanged(position);
                break;

            case 1:
                storyHolder.like.setBackground(storyHolder.like.getContext().getResources().
                        getDrawable(R.drawable.emoji_heart, null));
                storyHolder.storyLikes.setText(String.valueOf(likeCounts));
                stories.get(position).setStory_like_by_me(false);
                stories.get(position).setStory_likes(likeCounts);
                notifyItemChanged(position);
                break;
        }
    }

    void setStoryFavorite(int type, int position, int favoritesCount) {
        switch (type) {
            case 0:
                storyHolder.favorite.setBackground(storyHolder.like.getContext().getResources().
                        getDrawable(R.drawable.emoji_link_yellow, null));
                storyHolder.storyFavorites.setText(String.valueOf(favoritesCount));
                notifyItemChanged(position);
                break;

            case 1:
                storyHolder.like.setBackground(storyHolder.like.getContext().getResources().
                        getDrawable(R.drawable.emoji_link, null));
                storyHolder.storyFavorites.setText(String.valueOf(favoritesCount));
                notifyItemChanged(position);
                break;
        }
    }

    public StoryHolder getStoryHolder() {
        return storyHolder;
    }

    void setStories(List<PostResponse.Post_stories> stories) {
        this.stories = stories;
        notifyDataSetChanged();
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

            comment = itemView.findViewById(R.id.story_comment);
            comment.setOnClickListener(this);

            location = itemView.findViewById(R.id.post_location);
            location.setOnClickListener(this);

            profileImage = itemView.findViewById(R.id.user_image_post);
            profileImage.setOnClickListener(this);

            tagContainerLayout = itemView.findViewById(R.id.hashtags);
//            tagContainerLayout.setTagMaxLength(9);

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
                    SearchPostActivity searchPostActivity = new SearchPostActivity(currentPost.getPost_id(), UI_LOCATION, null);
                    Intent postLocationIntent = new Intent(videoItem.getContext(), searchPostActivity.getClass());
                    videoItem.getContext().startActivity(postLocationIntent);
                    break;

                case R.id.story_like:
                    homeFragmentListener.onStoryLikeChoose(currentPost.getPost_id(),
                            currentPost.getPost_stories().get(getLayoutPosition()).getStory_id(), getLayoutPosition());

                    if (stories.get(getLayoutPosition()).getStory_like_by_me()) {
                        like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
                        storyLikes.setText(String.valueOf(stories.get(getLayoutPosition()).getStory_likes() - 1));
                        stories.get(getLayoutPosition()).setStory_like_by_me(false);
                    } else {
                        like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
                        storyLikes.setText(String.valueOf(stories.get(getLayoutPosition()).getStory_likes() + 1));
                        stories.get(getLayoutPosition()).setStory_like_by_me(true);
                    }

                    Log.e("vbnvbn", String.valueOf(currentPost.getPost_id()));
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
                        storyFavorites.setText(String.valueOf(currentPost.getPost_favorites() - 1));
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

            tagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
                @Override
                public void onTagClick(int position, String text) {
                    SearchPostActivity searchPostActivity = new SearchPostActivity(0, UI_HASHTAG, "#" + text);
                    Intent postHashtagIntent = new Intent(videoItem.getContext(), searchPostActivity.getClass());
                    videoItem.getContext().startActivity(postHashtagIntent);
                }

                @Override
                public void onTagLongClick(int position, String text) {

                }
            });
        }
    }
}