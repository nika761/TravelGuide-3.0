package travelguideapp.ge.travelguide.ui.upload.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.ui.upload.UploadPostListener;

import java.util.List;

public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.HashtagHolder> {

    private List<HashtagResponse.Hashtags> hashtags;
    private TagPostListener tagPostListener;
    private UploadPostListener uploadPostListener;
    private List<String> hashs;
    private int type;

    public HashtagAdapter(int type, UploadPostListener uploadPostListener) {
        this.type = type;
        this.uploadPostListener = uploadPostListener;
    }

    HashtagAdapter(TagPostListener tagPostListener, int type) {
        this.type = type;
        this.tagPostListener = tagPostListener;
    }

    @NonNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HashtagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_hashtag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagHolder holder, int position) {
        if (type == 0) {
            holder.hashtag.setText(hashtags.get(position).getHashtag());
            holder.delete.setVisibility(View.GONE);
        } else {
            holder.hashtag.setText(hashs.get(position));
            holder.delete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (type == 0)
            return hashtags.size();
        else
            return hashs.size();
    }

    public void setHashs(List<String> hashs) {
        this.hashs = hashs;
        notifyDataSetChanged();
    }

    public List<String> getHashs() {
        return this.hashs;
    }

    ;

    public void setHashtags(List<HashtagResponse.Hashtags> hashtags) {
        this.hashtags = hashtags;
        notifyDataSetChanged();
    }

    class HashtagHolder extends RecyclerView.ViewHolder {

        TextView hashtag;
        ImageButton delete;

        HashtagHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.item_search_hashtag_delete);
            hashtag = itemView.findViewById(R.id.hashtag_search);

            if (type == 0)
                hashtag.setOnClickListener(v -> tagPostListener.onChooseHashtag(hashtags.get(getLayoutPosition()).getHashtag()));
            else
                delete.setOnClickListener(v -> {
                    try {
                        uploadPostListener.onHashtagRemoved(hashs.get(getLayoutPosition()));
                        hashs.remove(getLayoutPosition());
                        if (hashs.size() > 0)
                            notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        }

    }
}
