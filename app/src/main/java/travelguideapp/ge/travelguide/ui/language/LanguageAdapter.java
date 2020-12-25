package travelguideapp.ge.travelguide.ui.language;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.response.LanguagesResponse;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

    private List<LanguagesResponse.Language> languages;
    private LanguageListener iLanguageActivity;

    private int selectedPosition = -1;

    LanguageAdapter(LanguageListener iLanguageActivity, List<LanguagesResponse.Language> languages) {
        this.iLanguageActivity = iLanguageActivity;
        this.languages = languages;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LanguageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        try {
            if (selectedPosition == position) {
                holder.langFull.setTextColor(Color.parseColor("#F3BC1E"));
            } else {
                holder.langFull.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            langFull = itemView.findViewById(R.id.language_full_adapter);

            langFull.setOnClickListener(v -> {
                selectedPosition = getLayoutPosition();
                langFull.setTextColor(Color.parseColor("#F3BC1E"));
                iLanguageActivity.onChooseLanguage(languages.get(getLayoutPosition()).getId());
            });

            langFull.setOnLongClickListener(v -> {
                selectedPosition = getLayoutPosition();
                langFull.setTextColor(Color.parseColor("#F3BC1E"));
                iLanguageActivity.onChooseLanguage(languages.get(getLayoutPosition()).getId());
                return true;
            });

            langSmall = itemView.findViewById(R.id.language_small_adapter);

            langSmall.setOnClickListener(v -> {
                selectedPosition = getLayoutPosition();
                langFull.setTextColor(Color.parseColor("#F3BC1E"));
                iLanguageActivity.onChooseLanguage(languages.get(getLayoutPosition()).getId());
            });

            langSmall.setOnLongClickListener(v -> {
                selectedPosition = getLayoutPosition();
                langFull.setTextColor(Color.parseColor("#F3BC1E"));
                iLanguageActivity.onChooseLanguage(languages.get(getLayoutPosition()).getId());
                return true;
            });

        }
    }
}
