package com.example.travelguide.adapter.recycler;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.interfaces.IChangeLangFragment;
import com.example.travelguide.model.response.LanguagesResponseModel;

import java.util.List;

public class ChangeLangAdapter extends RecyclerView.Adapter<ChangeLangAdapter.ChangeLanguageViewHolder> {

    private Context context;
    private IChangeLangFragment iChangeLangFragment;
    private List<LanguagesResponseModel.Language> languages;
    private int currentLanguage;

    public ChangeLangAdapter(Context context, IChangeLangFragment iChangeLangFragment) {
        this.context = context;
        this.iChangeLangFragment = iChangeLangFragment;
    }

    @NonNull
    @Override
    public ChangeLanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chooser_language, parent, false);
        return new ChangeLanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeLanguageViewHolder holder, int position) {

//        currentLanguage = UtilsPref.getLanguageId(context);
//        if (languages.get(position).getId() == currentLanguage) {
//            holder.language.setText(languages.get(position).getNative_full());
//            holder.language.setTextColor(context.getColor(R.color.yellowTextView));
//        } else {
//            holder.language.setText(languages.get(position).getNative_full());
//            holder.language.setTextColor(context.getColor(R.color.grey));
//        }

        holder.language.setText(languages.get(position).getNative_full());
        holder.language.setTextColor(context.getColor(R.color.grey));

        holder.language.setOnClickListener(v -> {
//            currentLanguage = languages.get(position).getId();
            holder.language.setTextColor(Color.parseColor("#F3BC1E"));
            iChangeLangFragment.onLanguageChoose(languages.get(position).getId());
        });
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

        ChangeLanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            initUI();
        }

        void initUI() {
            language = itemView.findViewById(R.id.language_chooser);
        }
//
//        void setClickListeners() {
//            language.setOnClickListener(v -> {
////                language.setTextColor(Color.parseColor("#F3BC1E"));
////                iChangeLangClickActions.onLanguageChoose(languages.get(getLayoutPosition()).getId());
//            });
    }
}

