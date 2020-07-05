package com.example.travelguide.adapter.recycler;

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
import com.example.travelguide.activity.LanguageActivity;
import com.example.travelguide.model.response.LanguagesResponseModel;
import com.example.travelguide.utils.UtilsPref;

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

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.animation_languages);
        animation.setDuration(position * 500);
        holder.itemView.startAnimation(animation);
        holder.languageFullAdapter.setText(languageList.get(position).getNative_full());
        holder.languageSmallAdapter.setText(languageList.get(position).getNative_short());
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    public void setLanguageList(List<LanguagesResponseModel.Language> getLanguagesList) {
        this.languageList = getLanguagesList;
        notifyDataSetChanged();
    }

    class LanguageViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView languageFullAdapter, languageSmallAdapter;

        LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            iniUI();
            setClickListeners();
        }

        void iniUI() {
            languageFullAdapter = itemView.findViewById(R.id.language_full_adapter);
            languageSmallAdapter = itemView.findViewById(R.id.language_small_adapter);
        }

        void setClickListeners() {
            languageFullAdapter.setOnLongClickListener(this);
            languageSmallAdapter.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {

            switch (v.getId()) {
                case R.id.language_full_adapter:
                    languageFullAdapter.setTextColor(Color.parseColor("#F3BC1E"));
                    UtilsPref.saveLanguageId(context,languageList.get(getLayoutPosition()).getId());
                    ((LanguageActivity) context).checkSavedUsers();
                    break;
            }
            return false;
        }
    }
}
