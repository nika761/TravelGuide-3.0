package com.travelguide.travelguide.ui.customerUser.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travelguide.travelguide.R;
import com.travelguide.travelguide.helper.HelperMedia;
import com.travelguide.travelguide.model.response.PostResponse;

import java.util.List;

public class CustomerPhotoAdapter extends RecyclerView.Adapter<CustomerPhotoAdapter.PhotoViewHolder> {

    private List<PostResponse.Posts> posts;
    private CustomerPhotoListener customerPhotoListener;

    CustomerPhotoAdapter(List<PostResponse.Posts> posts, CustomerPhotoListener customerPhotoListener) {
        this.posts = posts;
        this.customerPhotoListener = customerPhotoListener;
    }

    @NonNull
    @Override
    public CustomerPhotoAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerPhotoAdapter.PhotoViewHolder holder, int position) {
        HelperMedia.loadPhoto(holder.postImage.getContext(), posts.get(position).getCover(), holder.postImage);
        holder.reactions.setText(String.valueOf(posts.get(position).getPost_reactions()));
        holder.nickName.setText(String.valueOf(posts.get(position).getNickname()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView reactions, nickName;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);

            reactions = itemView.findViewById(R.id.favorite_post_reactions);
            nickName = itemView.findViewById(R.id.item_customer_post_nick);
            postImage = itemView.findViewById(R.id.favorite_post_cover);
            postImage.setOnClickListener(v -> customerPhotoListener.onPostChoose(posts.get(getLayoutPosition()).getPost_id()));
        }
    }
}
