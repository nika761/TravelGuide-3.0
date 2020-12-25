package travelguideapp.ge.travelguide.ui.home.profile.posts;

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

import travelguideapp.ge.travelguide.utility.BaseApplication;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.UserPostHolder> {
    private List<PostResponse.Posts> posts;
    private UserPostListener userPostListener;
    private int itemWidth;

    UserPostAdapter(UserPostListener userPostListener) {
        this.userPostListener = userPostListener;
    }

    @NonNull
    @Override
    public UserPostAdapter.UserPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserPostAdapter.UserPostHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostAdapter.UserPostHolder holder, int position) {
        holder.loadMoreCallback(position);
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<PostResponse.Posts> posts) {
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

    void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    class UserPostHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView reactions, nickName;

        UserPostHolder(@NonNull View itemView) {
            super(itemView);
            reactions = itemView.findViewById(R.id.favorite_post_reactions);
            nickName = itemView.findViewById(R.id.item_customer_post_nick);
            postImage = itemView.findViewById(R.id.favorite_post_cover);
            postImage.setOnClickListener(v -> userPostListener.onPostChoose(posts.get(getLayoutPosition()).getPost_id()));
        }

        void loadMoreCallback(int position) {
            if (position == posts.size() - 1) {
                userPostListener.onLazyLoad(posts.get(position).getPost_id());
            }
        }

        void bindView(int position) {
            try {
                postImage.getLayoutParams().width = BaseApplication.ITEM_WIDTH_FOR_POSTS;
                HelperMedia.loadPhoto(postImage.getContext(), posts.get(position).getCover(), postImage);
                reactions.setText(String.valueOf(posts.get(position).getPost_view()));
                nickName.setText(String.valueOf(posts.get(position).getNickname()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
