package com.travel.guide.ui.customerUser.post;

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

import com.travel.guide.R;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.model.request.PostByUserRequest;
import com.travel.guide.model.response.PostResponse;

import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class CustomerPhotoFragment extends Fragment implements CustomerPhotoListener {
    private RecyclerView recyclerView;

    private CustomerPhotoPresenter customerPhotoPresenter;
    private int customerUserId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_photo, container, false);

        if (getArguments() != null) {
            this.customerUserId = getArguments().getInt(("customer_user_id"));
        }

        customerPhotoPresenter = new CustomerPhotoPresenter(this);
        recyclerView = view.findViewById(R.id.customer_photo_recycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customerPhotoPresenter.getCustomerPosts(ACCESS_TOKEN_BEARER +
                HelperPref.getAccessToken(recyclerView.getContext()), new PostByUserRequest(customerUserId));
    }

    @Override
    public void onGetPosts(PostResponse postResponse) {
//        int count = HelperUI.calculateNoOfColumns(recyclerView.getContext(), 105);

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
