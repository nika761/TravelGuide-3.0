package travelguideapp.ge.travelguide.helper.custom.customPost;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.ui.home.feed.HomeFragmentListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomPostHolder extends RecyclerView.ViewHolder {

    public ImageView storyCover, music, videoPlayIcon;
    public View parent;
    public HomeFragmentListener listener;

    public VideoView videoItem;
    public TextView nickName, description, musicName, location, storyLikes, storyComments, storyShares, storyFavorites;
    public CircleImageView profileImage;
    public ImageButton like, follow, share, favorite, comment, menu, go;
    public RecyclerView hashtagRecycler;
    public FrameLayout media_container;
    public PostResponse.Posts post;

    public int ownerUserId;
    public int position;
    public int countLikeUp = -1;
    public int countLikeDown = Integer.MAX_VALUE;

    public int countFavoriteUp = -1;
    public int countFavoriteDown = Integer.MAX_VALUE;

    CustomPostHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        media_container = itemView.findViewById(R.id.pl_container);
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
        music = itemView.findViewById(R.id.music_icon);
        videoPlayIcon = itemView.findViewById(R.id.video_playback);
        go = itemView.findViewById(R.id.go);
        favorite = itemView.findViewById(R.id.story_favorites);
//        favorite.setOnClickListener(v -> {
//            listener.onFavoriteChoose(post.getPost_id(), position);
//            setStoryEmotion(position, StoryEmotionType.FAVORITE);
//        });

        menu = itemView.findViewById(R.id.story_menu);
//        menu.setOnClickListener(v -> showMenu(menu));

        share = itemView.findViewById(R.id.story_share);
//        share.setOnClickListener(v -> listener.onShareChoose(post.getPost_share_url(), post.getPost_id()));

        like = itemView.findViewById(R.id.story_like);
//        like.setOnClickListener(v -> {
//            listener.onStoryLikeChoose(post.getPost_id(), post.getPost_stories().get(0).getStory_id(), position);
//            setStoryEmotion(position, StoryEmotionType.LIKE);
//        });

        follow = itemView.findViewById(R.id.story_follow_btn);
//        follow.setOnClickListener(v -> {
//            listener.onFollowChoose(post.getUser_id());
//            setStoryEmotion(position, StoryEmotionType.FOLLOW);
//        });

        comment = itemView.findViewById(R.id.story_comment);
//        comment.setOnClickListener(v -> listener.onCommentChoose(post.getPost_stories().get(0).getStory_id(), post.getPost_id()));

        location = itemView.findViewById(R.id.post_location);
//        location.setOnClickListener(v -> {
//            Intent postHashtagIntent = new Intent(videoItem.getContext(), SearchPostActivity.class);
//            postHashtagIntent.putExtra("search_type", SearchPostBy.LOCATION);
//            postHashtagIntent.putExtra("search_post_id", post.getPost_id());
//            like.getContext().startActivity(postHashtagIntent);
//        });

        profileImage = itemView.findViewById(R.id.user_image_post);
//        profileImage.setOnClickListener(v -> listener.onUserChoose(post.getUser_id()));

//        ownerUserId = GlobalPreferences.getUserId(like.getContext());

    }

    void onBind(PostResponse.Posts post, HomeFragmentListener listener, int position) {
        parent.setTag(this);
        this.listener = listener;
        this.post = post;
        this.position = position;
//        try {
//            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(post.getPost_stories().get(0).getUrl(), MediaStore.Images.Thumbnails.MINI_KIND);
//            Drawable drawable = new BitmapDrawable(like.getContext().getResources(), thumb);
////            this.requestManager
////                    .load(drawable)
////                    .into(storyCover);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (post.getPost_stories().get(0).getStory_like_by_me())
//            like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
//        else
//            like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
//
//
//        if (post.getI_follow_post_owner())
//            follow.setVisibility(View.GONE);
//        else
//            follow.setVisibility(View.VISIBLE);
//
//
//        if (post.getUser_id() == ownerUserId)
//            follow.setVisibility(View.GONE);
//
//
//        if (post.getI_favor_post())
//            favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
//        else
//            favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link, null));
//
//
//        if (post.getDescription().isEmpty()) {
//            description.setVisibility(View.GONE);
//        } else {
//            description.setVisibility(View.VISIBLE);
//            description.setText(post.getDescription());
//        }
//
//        if (post.getHashtags().size() > 0) {
//            hashtagRecycler.setLayoutManager(new LinearLayoutManager(share.getContext(), RecyclerView.HORIZONTAL, false));
//            hashtagRecycler.setAdapter(new HashtagAdapter(post.getHashtags()));
//            hashtagRecycler.setVisibility(View.VISIBLE);
//        } else {
//            hashtagRecycler.setVisibility(View.GONE);
//        }
//
//        if (post.getPost_locations().size() != 0)
//            location.setText(post.getPost_locations().get(0).getAddress());
//
//
//        if (GlobalPreferences.getUserId(like.getContext()) == post.getUser_id())
//            menu.setVisibility(View.VISIBLE);
//        else
//            menu.setVisibility(View.GONE);
//
//
//        nickName.setText(post.getNickname());
//        storyShares.setText(String.valueOf(post.getPost_shares()));
//        storyFavorites.setText(String.valueOf(post.getPost_favorites()));
//        storyLikes.setText(String.valueOf(post.getPost_stories().get(0).getStory_likes()));
//        storyComments.setText(String.valueOf(post.getPost_stories().get(0).getStory_comments()));
//
//        musicName.setText(post.getMusic_text());
//        musicName.setSelected(true);
//
//        HelperMedia.loadCirclePhoto(profileImage.getContext(), post.getProfile_pic(), profileImage);
    }

//    private void setStoryEmotion(int position, StoryEmotionType storyEmotionType) {
//        switch (storyEmotionType) {
//            case LIKE:
//                if (post.getPost_stories().get(0).getStory_like_by_me()) {
//
//                    if (countLikeUp > post.getPost_stories().get(0).getStory_likes()) {
//                        animate(like, storyLikes);
//                        like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
//                        storyLikes.setText(String.valueOf(post.getPost_stories().get(0).getStory_likes()));
//                        post.getPost_stories().get(0).setStory_like_by_me(false);
//                    } else {
//                        animate(like, storyLikes);
//                        storyLikes.setText(String.valueOf(post.getPost_stories().get(0).getStory_likes() - 1));
//                        like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
//                        countLikeDown = post.getPost_stories().get(0).getStory_likes() - 1;
//                        post.getPost_stories().get(0).setStory_like_by_me(false);
//                    }
//
//                } else {
//
//                    if (countLikeDown < post.getPost_stories().get(0).getStory_likes()) {
//                        animate(like, storyLikes);
//                        like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
//                        storyLikes.setText(String.valueOf(post.getPost_stories().get(0).getStory_likes()));
//                        post.getPost_stories().get(0).setStory_like_by_me(true);
//                    } else {
//                        animate(like, storyLikes);
//                        like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
//                        storyLikes.setText(String.valueOf(post.getPost_stories().get(0).getStory_likes() + 1));
//                        countLikeUp = post.getPost_stories().get(0).getStory_likes() + 1;
//                        post.getPost_stories().get(0).setStory_like_by_me(true);
//                    }
//                }
//
//                break;
//
//            case FAVORITE:
//                if (post.getI_favor_post()) {
//                    if (countFavoriteUp > post.getPost_favorites()) {
//                        animate(favorite, storyFavorites);
//                        favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link, null));
//                        storyFavorites.setText(String.valueOf(post.getPost_favorites()));
//                        post.setI_favor_post(false);
//                    } else {
//                        animate(favorite, storyFavorites);
//                        favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link, null));
//                        storyFavorites.setText(String.valueOf(post.getPost_favorites() - 1));
//                        countFavoriteDown = post.getPost_favorites() - 1;
//                        post.setI_favor_post(false);
//                    }
//
//                } else {
//
//                    if (countFavoriteDown < post.getPost_favorites()) {
//                        animate(favorite, storyFavorites);
//                        favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
//                        storyFavorites.setText(String.valueOf(post.getPost_favorites()));
//                        post.setI_favor_post(true);
//                    } else {
//                        animate(favorite, storyFavorites);
//                        favorite.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_link_yellow, null));
//                        storyFavorites.setText(String.valueOf(post.getPost_favorites() + 1));
//                        countFavoriteUp = post.getPost_favorites() + 1;
//                        post.setI_favor_post(true);
//                    }
//
//                }
//                break;
//
//            case FOLLOW:
//
//                if (!post.getI_follow_post_owner()) {
//                    follow.setVisibility(View.GONE);
//                    post.setI_follow_post_owner(true);
//                }
//                break;
//        }
//
//
//    }

    private void animate(View button, View text) {
        Animation anim = new ScaleAnimation(
                0f, 1f, // Start and end values for the X axis scaling
                0f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(250);
        button.startAnimation(anim);
        text.startAnimation(anim);
    }

    public void showMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.post_option_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.post_option_delete:
                    listener.onChooseDeleteStory(post.getPost_stories().get(0).getStory_id(), post.getPost_id(), getLayoutPosition());
                    return true;
            }
            return false;
        });
        popupMenu.show();
    }


}
