package travelguideapp.ge.travelguide.ui.search.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.HomeParentActivity;
import travelguideapp.ge.travelguide.model.response.FullSearchResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.ui.search.SearchActivity;

public class UsersFragment extends Fragment implements UsersFragmentListener {

    private RecyclerView usersRecycler;
    private LinearLayout nothingFound;
    private UserAdapter userAdapter;

    private boolean isFirstOpen = true;
    private boolean isLoading;
    private int fromPage = 1;

    private List<FullSearchResponse.Users> users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s_users, container, false);
        nothingFound = view.findViewById(R.id.nothing_found_users);
        usersRecycler = view.findViewById(R.id.search_user_recycler);
        usersRecycler.setLayoutManager(new LinearLayoutManager(usersRecycler.getContext()));
        usersRecycler.setHasFixedSize(true);
        userAdapter = new UserAdapter(this);
        return view;
    }

    public void listenOnRecycler() {
        try {
            usersRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if (!isLoading) {
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == users.size() - 1) {
                            isLoading = true;
                            fromPage++;
//                            onLazyLoad(fromPage);
                        }
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public void onLazyLoad(int page) {
//        try {
//            ((SearchActivity) usersRecycler.getContext()).getUsers(page);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void setUsers(List<FullSearchResponse.Users> users) {
        try {
            this.users = users;
            nothingFound.setVisibility(View.GONE);
            usersRecycler.setVisibility(View.VISIBLE);
            userAdapter.setUsers(this.users);
            usersRecycler.setAdapter(userAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNothingFound() {
        try {
            usersRecycler.setVisibility(View.GONE);
            nothingFound.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getCachedUsers();
    }

    @Override
    public void onUserChoose(FullSearchResponse.Users user) {
        try {
            ((HomeParentActivity) getActivity()).startCustomerActivity(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setLazyUsers(List<FullSearchResponse.Users> users) {
        try {
            isLoading = false;
            this.users = users;
            userAdapter.setUsers(this.users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getCachedUsers() {
        try {
            List<FullSearchResponse.Users> users = ((SearchActivity) usersRecycler.getContext()).getUsers();
            if (users != null) {
                setUsers(users);
                if (isFirstOpen)
                    isFirstOpen = false;
            } else {
                if (!isFirstOpen) {
                    setNothingFound();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
