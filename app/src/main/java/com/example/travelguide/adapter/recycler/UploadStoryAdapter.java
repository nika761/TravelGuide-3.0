package com.example.travelguide.adapter.recycler;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.activity.UploadStoryActivity;
import com.example.travelguide.interfaces.IUploadStory;
import com.example.travelguide.model.response.LanguagesResponseModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

public class UploadStoryAdapter extends RecyclerView.Adapter<UploadStoryAdapter.UploadStoryViewHolder> {
    private Context context;
    private ArrayList<Uri> uriArrayList;
    private IUploadStory iUploadStory;
    private UploadStoryViewHolder holder;

    public UploadStoryAdapter(Context context, IUploadStory iUploadStory) {
        this.context = context;
        this.iUploadStory = iUploadStory;
    }

    @NonNull
    @Override
    public UploadStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload_story, parent, false);
        return new UploadStoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadStoryViewHolder holder, int position) {
        this.holder = holder;
        holder.photoItem.setImageURI(uriArrayList.get(position));
        holder.editPhotoItemBtn.setOnClickListener(v -> iUploadStory.onGetItem(uriArrayList.get(position), position));
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }


    public void setUriArrayList(ArrayList<Uri> uriArrayList) {
        this.uriArrayList = uriArrayList;
        notifyDataSetChanged();
    }

    public void onCropResult(Uri uri, int adapterPosition) {
            uriArrayList.set(adapterPosition, uri);
            notifyDataSetChanged();
    }


    class UploadStoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView photoItem;
        private Button editPhotoItemBtn;

        UploadStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            initUI(itemView);
        }

        private void initUI(View itemView) {
            photoItem = itemView.findViewById(R.id.photo_item);
            editPhotoItemBtn = itemView.findViewById(R.id.edit_photo_item_btn);
        }

//        private void setClickListeners() {
//            editPhotoItemBtn.setOnClickListener(v -> {
//                iUploadStory.onGetItem(uriArrayList.get(getLayoutPosition()),getLayoutPosition());
//            });
//        }

    }
}
