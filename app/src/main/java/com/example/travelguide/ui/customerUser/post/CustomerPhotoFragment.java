package com.example.travelguide.ui.customerUser.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.PostByUserRequest;
import com.example.travelguide.model.response.PostResponse;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class CustomerPhotoFragment extends Fragment implements CustomerPhotoListener {
    private RecyclerView recyclerView;

    private CustomerPhotoPresenter customerPhotoPresenter;
    private int userId;

    public CustomerPhotoFragment(int userId) {
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_photo, container, false);
        customerPhotoPresenter = new CustomerPhotoPresenter(this);
        recyclerView = view.findViewById(R.id.customer_photo_recycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customerPhotoPresenter.getCustomerPosts(ACCESS_TOKEN_BEARER +
                HelperPref.getAccessToken(recyclerView.getContext()), new PostByUserRequest(userId));
    }

    @Override
    public void onGetPosts(PostResponse postResponse) {

        CustomerPhotoAdapter adapter = new CustomerPhotoAdapter(postResponse.getPosts(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onGetPostsError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostChoose(int userId) {
//        Intent intent = new Intent(recyclerView.getContext(), PostActivity.class);
//        intent.putExtra("user_id", userId);
//        recyclerView.getContext().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        if (customerPhotoPresenter != null) {
            customerPhotoPresenter = null;
        }
        super.onDestroy();
    }
}
