package com.example.travelguide.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.interfaces.IAboutFragment;
import com.example.travelguide.model.request.AboutRequestModel;
import com.example.travelguide.model.response.AboutResponseModel;
import com.example.travelguide.presenters.AboutPresenter;
import com.example.travelguide.utils.UtilsPref;

import java.util.Objects;

public class AboutFragment extends Fragment implements IAboutFragment {

    private Context context;
    private Button cancelBtn;
    private LottieAnimationView animationView;
    private TextView aboutHeader;
    private WebView aboutTextWebView;
    private AboutPresenter aboutPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        setClickListeners();
        loadAnimation(cancelBtn, R.anim.anim_swipe_left, 50);
        if (UtilsPref.getLanguageId(context) != 0) {
            AboutRequestModel aboutRequestModel = new AboutRequestModel(String.valueOf(UtilsPref.getLanguageId(context)));
            aboutPresenter.sendAboutRequest(aboutRequestModel);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initUI(View v) {
        cancelBtn = v.findViewById(R.id.cancel_btn_about);
        animationView = v.findViewById(R.id.animation_view_about);
        aboutHeader = v.findViewById(R.id.about_header);
        aboutTextWebView = v.findViewById(R.id.about_text);
        aboutPresenter = new AboutPresenter(this);
        aboutTextWebView.getSettings();
        aboutTextWebView.setBackgroundColor(getResources().getColor(R.color.whiteNone));
    }

    private void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(context, animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }

    private void setClickListeners() {
        cancelBtn.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
    }


    @Override
    public void onDestroy() {
        if (aboutPresenter != null) {
            aboutPresenter = null;
        }
        super.onDestroy();
    }

    private void setAbout(AboutResponseModel aboutResponseModel) {
        aboutHeader.setText(aboutResponseModel.getAbout().getAbout_title());
        aboutTextWebView.loadData("<html><body>" + aboutResponseModel.getAbout().getAbout_text() + "</body></html>", "text/html", "UTF-8");
        animationView.setVisibility(View.GONE);
    }

    @Override
    public void onGetAboutResult(AboutResponseModel aboutResponseModel) {
        if (aboutResponseModel.getStatus() == 0) {
            setAbout(aboutResponseModel);
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
