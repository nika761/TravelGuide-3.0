package travelguideapp.ge.travelguide.ui.search.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.base.BaseApplication;

import java.util.List;

public class SearchPostAdapter extends RecyclerView.Adapter<SearchPostAdapter.PostLocationHolder> {

    private final ChoosePostCallback callback;
    private List<PostResponse.Posts> posts;

    SearchPostAdapter(ChoosePostCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public PostLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostLocationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostLocationHolder holder, int position) {
        holder.bindUI(position);
    }

    public void setPosts(List<PostResponse.Posts> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostLocationHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView nickName, reactions;

        PostLocationHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.favorite_post_cover);
            reactions = itemView.findViewById(R.id.favorite_post_reactions);
            nickName = itemView.findViewById(R.id.item_customer_post_nick);

            postImage.setOnClickListener(v -> callback.onChoosePost(posts.get(getLayoutPosition()).getPost_id()));

        }

        void bindUI(int position) {
            try {
                postImage.getLayoutParams().width = BaseApplication.ITEM_WIDTH_FOR_POSTS;
                HelperMedia.loadPhoto(postImage.getContext(), posts.get(position).getCover(), postImage);
                nickName.setText(posts.get(position).getNickname());
                reactions.setText(String.valueOf(posts.get(position).getPost_reactions()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public interface ChoosePostCallback {
        void onChoosePost(int postId);
    }

}
