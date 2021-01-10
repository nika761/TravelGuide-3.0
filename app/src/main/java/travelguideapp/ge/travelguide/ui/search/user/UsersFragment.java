package travelguideapp.ge.travelguide.ui.search.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.ui.search.SearchActivity;
import travelguideapp.ge.travelguide.ui.upload.tag.AddTagAdapter;
import travelguideapp.ge.travelguide.ui.upload.tag.FriendsAdapter;

public class UsersFragment extends Fragment implements UsersFragmentListener {

    private RecyclerView usersRecycler;
    private FriendsAdapter friendsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s_users, container, false);
        usersRecycler = view.findViewById(R.id.search_user_recycler);
        return view;
    }

    public void setUsers(List<FollowerResponse.Followers> followers) {
        friendsAdapter.setFollowers(followers);
    }

    public void initUsersRecycler(List<FollowerResponse.Followers> followers) {
        friendsAdapter = new FriendsAdapter(this);
        friendsAdapter.setFollowers(followers);

        usersRecycler.setLayoutManager(new LinearLayoutManager(usersRecycler.getContext()));
        usersRecycler.setHasFixedSize(true);
        usersRecycler.setAdapter(friendsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        getCachedUsers();
    }

    @Override
    public void onUserChoose(FollowerResponse.Followers user) {
        try {
            ((BaseActivity) getActivity()).startCustomerActivity(user.getUser_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCachedUsers() {
        try {
            List<FollowerResponse.Followers> followers = ((SearchActivity) usersRecycler.getContext()).getFollowers();
            if (followers != null) {
                setUsers(followers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
