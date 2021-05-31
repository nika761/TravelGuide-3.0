package travelguideapp.ge.travelguide.ui.profile.posts;

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

import travelguideapp.ge.travelguide.base.MainApplication;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.UserPostHolder> {
    private List<PostResponse.Posts> posts;
    private final PostListener postListener;
    private boolean canLazyLoad;

    PostAdapter(PostListener postListener) {
        this.postListener = postListener;
    }

    @NonNull
    @Override
    public PostAdapter.UserPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostAdapter.UserPostHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.UserPostHolder holder, int position) {
        if (canLazyLoad)
            holder.loadMoreCallback(position);
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<PostResponse.Posts> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public void setCanLazyLoad(boolean canLazyLoad) {
        this.canLazyLoad = canLazyLoad;
    }

    class UserPostHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView reactions, nickName;

        UserPostHolder(@NonNull View itemView) {
            super(itemView);
            reactions = itemView.findViewById(R.id.favorite_post_reactions);
            nickName = itemView.findViewById(R.id.item_customer_post_nick);
            postImage = itemView.findViewById(R.id.favorite_post_cover);
            postImage.setOnClickListener(v -> postListener.onPostChoose(getLayoutPosition()));
        }

        void loadMoreCallback(int position) {
            if (position == posts.size() - 1) {
                postListener.onLazyLoad(posts.get(position).getPost_id());
            }
        }

        void onBind(int position) {
            try {
                postImage.getLayoutParams().width = MainApplication.ITEM_WIDTH_FOR_POSTS;
                HelperMedia.loadPhoto(postImage.getContext(), posts.get(position).getCover(), postImage);
                reactions.setText(String.valueOf(posts.get(position).getPost_view()));
                nickName.setText(String.valueOf(posts.get(position).getNickname()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
