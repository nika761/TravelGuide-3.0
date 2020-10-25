package com.example.travelguide.ui.webView.policy;

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
import com.example.travelguide.model.request.TermsPolicyRequest;
import com.example.travelguide.helper.HelperPref;

import java.util.Objects;

public class PolicyFragment extends Fragment implements PolicyListener {

    private Button cancelBtn;
    private LottieAnimationView animationView;
    private TextView policyHeader;
    private WebView policyTextWebView;
    private PolicyPresenter policyPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_policy, container, false);

        cancelBtn = v.findViewById(R.id.cancel_btn_policy);
        cancelBtn.setOnClickListener(view -> Objects.requireNonNull(getActivity()).onBackPressed());

        animationView = v.findViewById(R.id.animation_view_policy);
        policyHeader = v.findViewById(R.id.policy_header);
        policyTextWebView = v.findViewById(R.id.policy_text);
        policyTextWebView.getSettings();
        policyTextWebView.setBackgroundColor(getResources().getColor(R.color.whiteNone));

        policyPresenter = new PolicyPresenter(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadAnimation(cancelBtn, R.anim.anim_swipe_left, 50);

        policyPresenter.sendPolicyResponse(new TermsPolicyRequest(HelperPref.getLanguageId(cancelBtn.getContext())));

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

    private void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(cancelBtn.getContext(), animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }

    private void updateUI(TermsPolicyResponse.Policy policy) {
        policyHeader.setText(policy.getPolicy_title());
        policyTextWebView.loadData("<html><body>" + policy.getPolicy_text() + "</body></html>", "text/html", "UTF-8");

        animationView.setVisibility(View.GONE);
    }

    @Override
    public void onGetPolicy(TermsPolicyResponse.Policy policy) {
        updateUI(policy);
    }

    @Override
    public void onGetError(String message) {
        Toast.makeText(cancelBtn.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
