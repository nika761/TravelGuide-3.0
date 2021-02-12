package travelguideapp.ge.travelguide.ui.profile.favorites;

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

import travelguideapp.ge.travelguide.base.BaseApplication;

public class FavoritePostAdapter extends RecyclerView.Adapter<FavoritePostAdapter.PostViewHolder> {
    private List<PostResponse.Posts> posts;
    private FavoritePostListener favoritePostListener;
    private boolean canLazyLoad;
    private int itemWidth;

    FavoritePostAdapter(FavoritePostListener favoritePostListener) {
        this.favoritePostListener = favoritePostListener;
    }

    @NonNull
    @Override
    public FavoritePostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoritePostAdapter.PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritePostAdapter.PostViewHolder holder, int position) {
        if (canLazyLoad)
            holder.loadMoreCallback(position);
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public void setCanLazyLoad(boolean canLazyLoad) {
        this.canLazyLoad = canLazyLoad;
    }

    void setPosts(List<PostResponse.Posts> posts) {
//        if (this.posts != null && this.posts.size() != 0) {
//            this.posts.addAll(posts);
//            notifyDataSetChanged();
//        } else {
//            this.posts = posts;
//            notifyDataSetChanged();
//        }

        this.posts = posts;
        notifyDataSetChanged();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView reactions;
        TextView nickName;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);

            reactions = itemView.findViewById(R.id.favorite_post_reactions);

            nickName = itemView.findViewById(R.id.item_customer_post_nick);

            postImage = itemView.findViewById(R.id.favorite_post_cover);

            setClickListeners();
        }

        void loadMoreCallback(int position) {
            if (position == posts.size() - 1) {
                favoritePostListener.onLazyLoad(posts.get(position).getPost_id());
            }
        }

        void bindView(int position) {
            try {
                postImage.getLayoutParams().width = BaseApplication.ITEM_WIDTH_FOR_POSTS;
                HelperMedia.loadPhoto(postImage.getContext(), posts.get(position).getCover(), postImage);
                reactions.setText(String.valueOf(posts.get(position).getPost_view()));
                nickName.setText(posts.get(position).getNickname());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        void setClickListeners() {
            postImage.setOnClickListener(v -> favoritePostListener.onPostChoose(posts.get(getLayoutPosition()).getPost_id()));
        }

    }
}

