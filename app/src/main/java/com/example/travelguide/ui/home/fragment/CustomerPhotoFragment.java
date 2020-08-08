package com.example.travelguide.ui.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.CustomerPostRequest;
import com.example.travelguide.model.response.CustomerPostResponse;
import com.example.travelguide.ui.home.adapter.recycler.CustomerPhotoRecyclerAdapter;
import com.example.travelguide.ui.home.interfaces.ICustomerPhoto;
import com.example.travelguide.ui.home.presenter.CustomerPhotoPresenter;
import com.example.travelguide.ui.home.presenter.CustomerProfilePresenter;
import com.example.travelguide.ui.upload.adapter.recycler.GalleryAdapter;

public class CustomerPhotoFragment extends Fragment implements ICustomerPhoto {
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
        customerPhotoPresenter.getCustomerPosts("Bearer" + " " + HelperPref.getCurrentAccessToken(recyclerView.getContext()), new CustomerPostRequest(userId));
    }

    @Override
    public void onGetPosts(CustomerPostResponse customerPostResponse) {

        CustomerPhotoRecyclerAdapter adapter = new CustomerPhotoRecyclerAdapter(customerPostResponse.getPosts());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onGetPostsError(String message) {

    }

    @Override
    public void onDestroy() {
        if (customerPhotoPresenter != null) {
            customerPhotoPresenter = null;
        }
        super.onDestroy();
    }
}
