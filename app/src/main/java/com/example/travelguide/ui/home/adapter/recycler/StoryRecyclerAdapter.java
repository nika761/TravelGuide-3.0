package com.example.travelguide.ui.home.adapter.recycler;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.ui.home.activity.CustomerProfileActivity;
import com.example.travelguide.ui.home.interfaces.OnLoadFinishListener;
import com.example.travelguide.model.response.PostResponse;

import java.util.List;

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
        HelperMedia.loadCirclePhoto(viewHolder.profileImage.getContext(), currentPost.getProfile_pic(), viewHolder.profileImage);
        viewHolder.musicName.setText(currentPost.getMusic_text());
        viewHolder.musicName.setSelected(true);
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
        TextView nickName, description, musicName;
        CircleImageView profileImage;

        StoryHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            nickName = itemView.findViewById(R.id.nickname_post);
            videoItem = itemView.findViewById(R.id.scalable_video);
            description = itemView.findViewById(R.id.post_description);
            musicName = itemView.findViewById(R.id.music_name_post);

            profileImage = itemView.findViewById(R.id.user_image_post);
            profileImage.setOnClickListener(this);
        }

        void setVideoItem() {
            videoItem.setVideoURI(Uri.parse(stories.get(getLayoutPosition()).getUrl()));
            videoItem.requestFocus();
            videoItem.setOnPreparedListener(mp -> {
//                        holder.videoItem.setMediaController(new MediaController(context));
                mp.setLooping(true);
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
            }
        }
    }
}