package com.example.travelguide.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelguide.R;

public class TermsOfServiceFragment extends Fragment {

    private Context context;
    private View layout, termsScroll;
    private Button cancelBtn, agreeBtn;
    private TextView termsHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.terms_and_services_layout, container, false);

        return layout;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadAnimation(termsHeader, R.anim.fade_in_center_anim, 0);
        loadAnimation(termsScroll, R.anim.fade_in_center_anim, 0);
        loadAnimation(cancelBtn, R.anim.swipe_from_left_anim, 50);
        loadAnimation(agreeBtn, R.anim.swipe_from_right_anim, 50);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(View v) {
        cancelBtn = v.findViewById(R.id.cancel_btn_terms);
        agreeBtn = v.findViewById(R.id.agree_btn_terms);
        termsScroll = v.findViewById(R.id.scroll_terms);
        termsHeader = v.findViewById(R.id.terms_of_services_header);

    }

    private void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(context, animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }
}
