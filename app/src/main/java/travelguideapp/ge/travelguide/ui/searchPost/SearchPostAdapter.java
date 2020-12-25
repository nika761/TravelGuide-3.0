package travelguideapp.ge.travelguide.ui.searchPost;

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

import java.util.List;

public class SearchPostAdapter extends RecyclerView.Adapter<SearchPostAdapter.PostLocationHolder> {
    private List<PostResponse.Posts> posts;
    private int itemWidth;


    SearchPostAdapter(List<PostResponse.Posts> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostLocationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostLocationHolder holder, int position) {
        holder.postImage.getLayoutParams().width = itemWidth;
        HelperMedia.loadPhoto(holder.postImage.getContext(), posts.get(position).getCover(), holder.postImage);
        holder.nickName.setText(posts.get(position).getNickname());
        holder.reactions.setText(String.valueOf(posts.get(position).getPost_reactions()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    class PostLocationHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView nickName, reactions;

        PostLocationHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.favorite_post_cover);
            reactions = itemView.findViewById(R.id.favorite_post_reactions);
            nickName = itemView.findViewById(R.id.item_customer_post_nick);
        }
    }
}
