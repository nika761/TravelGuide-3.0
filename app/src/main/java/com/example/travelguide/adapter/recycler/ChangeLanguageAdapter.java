package com.example.travelguide.adapter.recycler;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.model.response.LanguagesResponseModel;
import com.example.travelguide.utils.UtilsGlide;
import com.example.travelguide.utils.UtilsPref;

import java.util.List;

public class ChangeLanguageAdapter extends RecyclerView.Adapter<ChangeLanguageAdapter.ChangeLanguageViewHolder> {

    private Context context;
    private List<LanguagesResponseModel.Language> languages;

    public ChangeLanguageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ChangeLanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chooser_language_item, parent, false);
        return new ChangeLanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeLanguageViewHolder holder, int position) {
        int currentLanguage = UtilsPref.getLanguageId(context);

        if (languages.get(position).getId() == currentLanguage) {
            holder.language.setText(languages.get(position).getNative_full());
            holder.language.setTextColor(context.getColor(R.color.yellowTextView));
        } else {
            holder.language.setText(languages.get(position).getNative_full());
            holder.language.setTextColor(context.getColor(R.color.grey));
        }
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public void setLanguageList(List<LanguagesResponseModel.Language> getLanguagesList) {
        this.languages = getLanguagesList;
        notifyDataSetChanged();
    }

    class ChangeLanguageViewHolder extends RecyclerView.ViewHolder {
        TextView language;
        ImageView flag;

        ChangeLanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            initUI();
            setClickListeners();
        }


        void initUI() {
            language = itemView.findViewById(R.id.language_chooser);
        }

        void setClickListeners() {
            language.setOnClickListener(v -> {
                language.setTextColor(Color.parseColor("#F3BC1E"));
                UtilsPref.saveLanguageId(context, languages.get(getLayoutPosition()).getId());
            });
        }
    }
}
