package com.example.travelguide.adapter.recycler;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.model.response.LanguagesResponseModel;

import java.util.ArrayList;
import java.util.List;

public class UploadStoryAdapter extends RecyclerView.Adapter<UploadStoryAdapter.UploadStoryViewHolder> {
    private Context context;
    private ArrayList<Uri> uriArrayList;

    public UploadStoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UploadStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload_story, parent, false);
        return new UploadStoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadStoryViewHolder holder, int position) {
        holder.photoItem.setImageURI(uriArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public void setUriArrayList(ArrayList<Uri> uriArrayList) {
        this.uriArrayList = uriArrayList;
        notifyDataSetChanged();
    }

    class UploadStoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView photoItem;
        private ImageButton editPhotoItemBtn;

        UploadStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            initUI(itemView);
            setClickListeners();
        }

        private void initUI(View itemView) {
            photoItem = itemView.findViewById(R.id.photo_item);
            editPhotoItemBtn = itemView.findViewById(R.id.edit_photo_item_btn);
        }

        private void setClickListeners() {
            editPhotoItemBtn.setOnClickListener(v -> {

            });
        }
    }
}
