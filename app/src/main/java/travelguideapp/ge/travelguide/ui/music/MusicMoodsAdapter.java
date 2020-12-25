package travelguideapp.ge.travelguide.ui.music;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.response.MoodResponse;
import travelguideapp.ge.travelguide.ui.music.searchMusic.SearchMusicListener;

import java.util.List;

public class MusicMoodsAdapter extends RecyclerView.Adapter<MusicMoodsAdapter.MoodsHolder> {

    private List<MoodResponse.Moods> moods;
    private SearchMusicListener searchMusicListener;
    private int currentPosition = -1;

    public MusicMoodsAdapter(List<MoodResponse.Moods> moods, SearchMusicListener searchMusicListener) {
        this.moods = moods;
        this.searchMusicListener = searchMusicListener;
    }

    @NonNull
    @Override
    public MoodsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoodsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moods, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoodsHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return moods.size();
    }

    class MoodsHolder extends RecyclerView.ViewHolder {
        TextView moodTitle;

        MoodsHolder(@NonNull View itemView) {
            super(itemView);

            moodTitle = itemView.findViewById(R.id.item_moods_title);
            moodTitle.setOnClickListener(v -> {
                currentPosition = getLayoutPosition();
                searchMusicListener.onChooseMood(moods.get(getLayoutPosition()).getId());
                notifyDataSetChanged();
            });

//            Animation animation = AnimationUtils.loadAnimation(moodTitle.getContext(), R.anim.anim_follow_item_left);
//            itemView.startAnimation(animation);

        }

        void bindView(int position) {

            if (currentPosition == position) {
                moodTitle.setTextColor(moodTitle.getResources().getColor(R.color.yellowTextView, null));
            } else {
                moodTitle.setTextColor(moodTitle.getResources().getColor(R.color.blackStatusBar, null));
            }

            moodTitle.setText(moods.get(position).getMood());

        }
    }
}
