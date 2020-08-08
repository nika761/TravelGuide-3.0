package com.example.travelguide.ui.home.interfaces;

import com.example.travelguide.model.response.CustomerPostResponse;

public interface ICustomerPhoto {
    void onGetPosts(CustomerPostResponse customerPostResponse);
    void onGetPostsError(String message);
}
