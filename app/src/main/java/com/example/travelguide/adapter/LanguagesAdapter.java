package com.example.travelguide.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.model.LanguagesResponseModel;

import java.util.List;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.LanguageViewHolder> {

    private List<LanguagesResponseModel.Language> languageList;
    private Context context;

    public LanguagesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_item, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        holder.languageFullAdapter.setText(languageList.get(position).getName());
        holder.languageSmallAdapter.setText(languageList.get(position).getNative_short());
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.animation_languages);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    public void setLanguageList(List<LanguagesResponseModel.Language> getLanguagesList) {
        this.languageList = getLanguagesList;
        notifyDataSetChanged();
    }

    class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView languageFullAdapter, languageSmallAdapter;

        LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            languageFullAdapter = itemView.findViewById(R.id.language_full_adapter);
            languageSmallAdapter = itemView.findViewById(R.id.language_small_adapter);
        }
    }
}
