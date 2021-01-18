package travelguideapp.ge.travelguide.ui.upload.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import travelguideapp.ge.travelguide.ui.search.user.UsersFragmentListener;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsHolder> {

    private TagPostListener tagPostListener;
    private List<FollowerResponse.Followers> followers;
    private UsersFragmentListener usersFragmentListener;

    public FriendsAdapter(TagPostListener tagPostListener) {
        this.tagPostListener = tagPostListener;
    }

    public FriendsAdapter(UsersFragmentListener usersFragmentListener) {
        this.usersFragmentListener = usersFragmentListener;
    }


    @NonNull
    @Override
    public FriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public void setFollowers(List<FollowerResponse.Followers> followers) {
        this.followers = followers;
        notifyDataSetChanged();
    }

    class FriendsHolder extends RecyclerView.ViewHolder {

        CircleImageView profileImage;
        ConstraintLayout container;
        TextView userName;
        TextView nickName;
        TextView follow;

        FriendsHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.followers_container);
            try {
                if (tagPostListener != null)
                    container.setOnClickListener(v -> tagPostListener.onChooseFollower(followers.get(getLayoutPosition()).getUser_id(), followers.get(getLayoutPosition()).getNickname()));
                else
                    container.setOnClickListener(v -> usersFragmentListener.onUserChoose(followers.get(getLayoutPosition())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            profileImage = itemView.findViewById(R.id.followers_user_image);
            userName = itemView.findViewById(R.id.followers_user_name);
            nickName = itemView.findViewById(R.id.followers_user_nick_name);
            follow = itemView.findViewById(R.id.followers_user_follow);
            follow.setVisibility(View.GONE);
        }

        void bindView(int position) {
            HelperMedia.loadCirclePhoto(nickName.getContext(), followers.get(position).getProfile_pic(), profileImage);
            userName.setText(followers.get(position).getName());
            nickName.setText(followers.get(position).getNickname( ));
        }
    }
}
