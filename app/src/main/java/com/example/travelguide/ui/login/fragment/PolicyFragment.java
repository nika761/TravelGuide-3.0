package com.example.travelguide.ui.login.fragment;

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
import com.example.travelguide.model.response.TermsPolicyResponse;
import com.example.travelguide.ui.login.interfaces.IPolicyFragment;
import com.example.travelguide.model.request.TermsPolicyRequest;
import com.example.travelguide.ui.login.presenter.PolicyPresenter;
import com.example.travelguide.helper.HelperPref;

import java.util.Objects;

public class PolicyFragment extends Fragment implements IPolicyFragment {

    private Context context;
    private Button cancelBtn;
    private LottieAnimationView animationView;
    private TextView policyHeader;
    private WebView policyTextWebView;
    private TermsPolicyRequest termsPolicyRequest;
    private PolicyPresenter policyPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_policy, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        setClickListeners();
        loadAnimation(cancelBtn, R.anim.anim_swipe_left, 50);

        if (HelperPref.getLanguageId(context) != 0)
            termsPolicyRequest.setLanguage_id(HelperPref.getLanguageId(context));
        policyPresenter.sendPolicyResponse(termsPolicyRequest);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (policyPresenter != null) {
            policyPresenter = null;
        }
        super.onDestroy();
    }


    private void initUI(View v) {
        cancelBtn = v.findViewById(R.id.cancel_btn_policy);
        animationView = v.findViewById(R.id.animation_view_policy);
//        agreeBtn = v.findViewById(R.id.agree_btn_terms);
//        termsScroll = v.findViewById(R.id.scroll_terms);
        policyHeader = v.findViewById(R.id.policy_header);
        policyTextWebView = v.findViewById(R.id.policy_text);
        termsPolicyRequest = new TermsPolicyRequest();
        policyPresenter = new PolicyPresenter(this);
        policyTextWebView.getSettings();
        policyTextWebView.setBackgroundColor(getResources().getColor(R.color.whiteNone));
    }

    private void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(context, animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }


    private void setClickListeners() {
        cancelBtn.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
    }

    private void setPolicy(TermsPolicyResponse termsPolicyResponse) {
        policyHeader.setText(termsPolicyResponse.getPolicy().getPolicy_title());
        policyTextWebView.loadData("<html><body>" + termsPolicyResponse.getPolicy().getPolicy_text() + "</body></html>", "text/html", "UTF-8");

        animationView.setVisibility(View.GONE);
    }

    @Override
    public void onGetPolicyResult(TermsPolicyResponse termsPolicyResponse) {
        if (termsPolicyResponse.getStatus() == 0) {
            setPolicy(termsPolicyResponse);
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}