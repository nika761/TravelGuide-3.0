package travelguideapp.ge.travelguide.ui.upload.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.ui.search.hashtag.HashtagsFragmentListener;

import java.util.List;

public class AddTagAdapter extends RecyclerView.Adapter<AddTagAdapter.AddTagHolder> {

    private TagPostListener tagPostListener;
    private List<HashtagResponse.Hashtags> hashtags;
    private HashtagsFragmentListener hashtagsFragmentListener;

    public AddTagAdapter(TagPostListener tagPostListener) {
        this.tagPostListener = tagPostListener;
    }

    public AddTagAdapter(HashtagsFragmentListener hashtagsFragmentListener) {
        this.hashtagsFragmentListener = hashtagsFragmentListener;
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
//        holder.lazyLoadCallback(position);
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
            try {
                if (tagPostListener != null)
                    hashtag.setOnClickListener(v -> tagPostListener.onChooseHashtag(hashtags.get(getLayoutPosition()).getHashtag()));
                else
                    hashtag.setOnClickListener(v -> hashtagsFragmentListener.onHashtagChoose(hashtags.get(getLayoutPosition())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        void lazyLoadCallback(int position) {
//            try {
//                if (position == hashtags.size() - 1) {
//                    hashtagsFragmentListener.onLazyLoad();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
    }
}
