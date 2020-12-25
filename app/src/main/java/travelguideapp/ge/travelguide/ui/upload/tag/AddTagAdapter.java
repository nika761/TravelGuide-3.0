package travelguideapp.ge.travelguide.ui.upload.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;

import java.util.List;

public class AddTagAdapter extends RecyclerView.Adapter<AddTagAdapter.AddTagHolder> {

    private List<HashtagResponse.Hashtags> hashtags;
    private TagPostListener tagPostListener;

    AddTagAdapter(TagPostListener tagPostListener) {
        this.tagPostListener = tagPostListener;
    }

    @NonNull
    @Override
    public AddTagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddTagAdapter.AddTagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_hashtag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddTagHolder holder, int position) {
        try {
            holder.hashtag.setText(hashtags.get(position).getHashtag());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHashtags(List<HashtagResponse.Hashtags> hashtags) {
        this.hashtags = hashtags;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return hashtags.size();
    }

    class AddTagHolder extends RecyclerView.ViewHolder {
        TextView hashtag;

        AddTagHolder(@NonNull View itemView) {
            super(itemView);
            hashtag = itemView.findViewById(R.id.hashtag_add);
            hashtag.setOnClickListener(v -> tagPostListener.onChooseHashtag(hashtags.get(getLayoutPosition()).getHashtag()));
        }
    }
}
