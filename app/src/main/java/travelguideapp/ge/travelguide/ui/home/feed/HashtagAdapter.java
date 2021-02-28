package travelguideapp.ge.travelguide.ui.home.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;

import java.util.List;

import static travelguideapp.ge.travelguide.model.parcelable.SearchPostParams.SearchBy.HASHTAG;


public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.HashtagHolder> {

    private HomeFragmentListener homeFragmentListener;
    private List<String> hashtags;

    public HashtagAdapter(List<String> hashtags, HomeFragmentListener homeFragmentListener) {
        this.hashtags = hashtags;
        this.homeFragmentListener = homeFragmentListener;
    }

    @NonNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HashtagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hashtag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagHolder holder, int position) {
        holder.hashtag.setText(hashtags.get(position));
    }

    @Override
    public int getItemCount() {
        return hashtags.size();
    }

    class HashtagHolder extends RecyclerView.ViewHolder {

        TextView hashtag;

        HashtagHolder(@NonNull View itemView) {
            super(itemView);
            hashtag = itemView.findViewById(R.id.hashtag_hashtag);
            hashtag.setOnClickListener(v -> homeFragmentListener.onHashtagChoose(hashtags.get(getLayoutPosition()), HASHTAG));
        }

    }
}
