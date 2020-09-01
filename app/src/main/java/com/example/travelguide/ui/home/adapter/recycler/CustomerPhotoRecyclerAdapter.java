package com.example.travelguide.ui.home.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.ui.home.interfaces.ICustomerPhoto;

import java.util.List;

public class CustomerPhotoRecyclerAdapter extends RecyclerView.Adapter<CustomerPhotoRecyclerAdapter.PhotoViewHolder> {

    private List<PostResponse.Posts> posts;
    private ICustomerPhoto iCustomerPhoto;

    public CustomerPhotoRecyclerAdapter(List<PostResponse.Posts> posts, ICustomerPhoto iCustomerPhoto) {
        this.posts = posts;
        this.iCustomerPhoto = iCustomerPhoto;
    }

    @NonNull
    @Override
    public CustomerPhotoRecyclerAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerPhotoRecyclerAdapter.PhotoViewHolder holder, int position) {
        HelperMedia.loadPhoto(holder.postImage.getContext(), posts.get(position).getProfile_pic(), holder.postImage);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.item_customer_photo);
            postImage.setOnClickListener(v -> {
                iCustomerPhoto.onPostChoose(posts.get(getLayoutPosition()).getPost_id());
            });

        }
    }
}
