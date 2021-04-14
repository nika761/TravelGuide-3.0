package travelguideapp.ge.travelguide.ui.profile.changeLanguage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.response.LanguagesResponse;

import java.util.List;

public class ChangeLangAdapter extends RecyclerView.Adapter<ChangeLangAdapter.ChangeLanguageViewHolder> {

    private final Context context;
    private final ChangeLangListener iChangeLangFragment;
    private List<LanguagesResponse.Language> languages;
    private final int currentLanguageId;
    private int currentLanguagePosition;

    ChangeLangAdapter(Context context, ChangeLangListener iChangeLangFragment) {
        this.context = context;
        this.iChangeLangFragment = iChangeLangFragment;
        this.currentLanguageId = GlobalPreferences.getLanguageId(context);
    }

    @NonNull
    @Override
    public ChangeLanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chooser_language, parent, false);
        return new ChangeLanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeLanguageViewHolder holder, int position) {

        if (currentLanguageId == languages.get(position).getId()) {
            holder.language.setTextColor(context.getColor(R.color.yellowTextView));
            holder.language.setText(languages.get(position).getNative_full());
            currentLanguagePosition = position;
        } else {
            holder.language.setText(languages.get(position).getNative_full());
            holder.language.setTextColor(context.getColor(R.color.grey));
        }
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    void setLanguageList(List<LanguagesResponse.Language> languages) {
        this.languages = languages;
        notifyDataSetChanged();
    }

    class ChangeLanguageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView language;

        ChangeLanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            language = itemView.findViewById(R.id.language_chooser);
            language.setOnClickListener(this);
        }

        void setLanguageColor() {
            language.setTextColor(context.getColor(R.color.grey));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.language_chooser:
                    setLanguageColor();
                    language.setTextColor(context.getColor(R.color.yellowTextView));
                    iChangeLangFragment.onLanguageChoose(languages.get(getLayoutPosition()).getId());
                    break;
            }
        }

//
//        void setClickListeners() {
//            language.setOnClickListener(v -> {
////                language.setTextColor(Color.parseColor("#F3BC1E"));
////                iChangeLangClickActions.onLanguageChoose(languages.get(getLayoutPosition()).getId());
//            });
    }
}

