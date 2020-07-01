package com.example.travelguide.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import de.hdodenhof.circleimageview.CircleImageView;

public class UtilsGlide {

    public static void loadPhoto(Context context, String url, CircleImageView circleImageView) {

        long time = System.currentTimeMillis();

        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(url)
//                .signature(new ObjectKey(time / 24 * 60 * 60 * 1000))
                .into(circleImageView);
    }

    public static void loadPhotoSquare(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(url)
                .into(imageView);
    }

}
