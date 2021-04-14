package travelguideapp.ge.travelguide.ui.search.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.model.response.FullSearchResponse;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersHolder> {

    private List<FullSearchResponse.Users> users;
    private final UsersFragmentListener usersFragmentListener;

    public UserAdapter(UsersFragmentListener usersFragmentListener) {
        this.usersFragmentListener = usersFragmentListener;
    }

    @NonNull
    @Override
    public UserAdapter.UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserAdapter.UsersHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<FullSearchResponse.Users> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    class UsersHolder extends RecyclerView.ViewHolder {

        CircleImageView profileImage;
        ConstraintLayout container;
        TextView userName;
        TextView nickName;
        TextView follow;

        UsersHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.followers_container);
            container.setOnClickListener(v -> {
                try {
                    usersFragmentListener.onUserChoose(users.get(getLayoutPosition()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            profileImage = itemView.findViewById(R.id.followers_user_image);
            userName = itemView.findViewById(R.id.followers_user_name);
            nickName = itemView.findViewById(R.id.followers_user_nick_name);
            follow = itemView.findViewById(R.id.followers_user_follow);
            follow.setVisibility(View.GONE);
        }

        void onBind(int position) {
            try {
                HelperMedia.loadCirclePhoto(nickName.getContext(), users.get(position).getProfile_pic(), profileImage);
                userName.setText(String.format("%s %s", users.get(position).getName(), users.get(position).getLastname()));
                nickName.setText(users.get(position).getNickname());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
