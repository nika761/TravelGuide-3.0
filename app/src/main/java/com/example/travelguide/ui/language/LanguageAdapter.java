package com.example.travelguide.ui.language;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.response.LanguagesResponse;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

    private List<LanguagesResponse.Language> languages;
    private LanguageListener iLanguageActivity;

    LanguageAdapter(LanguageListener iLanguageActivity, List<LanguagesResponse.Language> languages) {
        this.iLanguageActivity = iLanguageActivity;
        this.languages = languages;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {

        Animation animation = AnimationUtils.loadAnimation(holder.langFull.getContext(), R.anim.anim_languages);
        animation.setDuration(position * 500);
        holder.itemView.startAnimation(animation);
        holder.langFull.setText(languages.get(position).getNative_full());
        holder.langSmall.setText(languages.get(position).getNative_short());
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }


    class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView langFull, langSmall;

        LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            initUI();
        }

        void initUI() {
            langFull = itemView.findViewById(R.id.language_full_adapter);

            langFull.setOnClickListener(v -> {
                langFull.setTextColor(Color.parseColor("#F3BC1E"));
                HelperPref.saveLanguageId(langFull.getContext(), languages.get(getLayoutPosition()).getId());
                iLanguageActivity.onChooseLanguage();
            });

            langFull.setOnLongClickListener(v -> {
                langFull.setTextColor(Color.parseColor("#F3BC1E"));
                HelperPref.saveLanguageId(langFull.getContext(), languages.get(getLayoutPosition()).getId());
                iLanguageActivity.onChooseLanguage();
                return false;
            });

            langSmall = itemView.findViewById(R.id.language_small_adapter);

        }
    }
}
