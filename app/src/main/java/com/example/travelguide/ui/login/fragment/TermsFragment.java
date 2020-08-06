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
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.response.TermsPolicyResponse;
import com.example.travelguide.ui.login.interfaces.ITermsFragment;
import com.example.travelguide.model.request.TermsPolicyRequest;
import com.example.travelguide.ui.login.presenter.TermsPresenter;

import java.util.Objects;

public class TermsFragment extends Fragment implements ITermsFragment {

    private Context context;
    private View termsScroll;
    private Button cancelBtn, agreeBtn;
    private TextView termsHeader;
    private WebView termsTextWebView;
    private TermsPresenter termsPresenter;
    private TermsPolicyRequest termsPolicyRequest;
    private LottieAnimationView animationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_terms, container, false);
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        setClickListeners();
//        loadAnimation(termsScroll, R.anim.fade_in_center_anim, 0);
        loadAnimation(cancelBtn, R.anim.anim_swipe_left, 50);
//        loadAnimation(agreeBtn, R.anim.anim_swipe_right, 50);

        if (HelperPref.getLanguageId(context) != 0)
            termsPolicyRequest.setLanguage_id(HelperPref.getLanguageId(context));
        termsPresenter.sendTermsResponse(termsPolicyRequest);

    }

//    private boolean checkTermOrPrivacy(Bundle arguments) {
//        boolean terms = true;
//        boolean termsOrPolicy = arguments.getBoolean("terms");
//
//        if (!termsOrPolicy) {
//            terms = false;
//        }
//        return terms;
//    }

    private void initUI(View v) {
        cancelBtn = v.findViewById(R.id.cancel_btn_terms);
        animationView = v.findViewById(R.id.animation_view);
//        agreeBtn = v.findViewById(R.id.agree_btn_terms);
//        termsScroll = v.findViewById(R.id.scroll_terms);
        termsHeader = v.findViewById(R.id.terms_of_services_header);
        termsTextWebView = v.findViewById(R.id.terms_text);
        termsPolicyRequest = new TermsPolicyRequest();
        termsPresenter = new TermsPresenter(this);
        termsTextWebView.getSettings();
        termsTextWebView.setBackgroundColor(getResources().getColor(R.color.whiteNone));
    }


    private void setClickListeners() {
        cancelBtn.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
    }

    private void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(context, animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }

    private void setTerms(TermsPolicyResponse termsPolicyResponse) {
        termsHeader.setText(termsPolicyResponse.getTerms().getTerms_title());
        termsTextWebView.loadData("<html><body>" + termsPolicyResponse.getTerms().getTerms_text() + "</body></html>", "text/html", "UTF-8");

        animationView.setVisibility(View.GONE);
    }

    private void setPolicy(TermsPolicyResponse termsPolicyResponse) {
        termsHeader.setText(termsPolicyResponse.getPolicy().getPolicy_title());
        termsTextWebView.loadData("<html><body>" + termsPolicyResponse.getPolicy().getPolicy_text() + "</body></html>", "text/html", "UTF-8");

        animationView.setVisibility(View.GONE);
    }

    @Override
    public void onGetTermsResult(TermsPolicyResponse termsPolicyResponse) {
        if (termsPolicyResponse.getStatus() == 0) {
//            assert getArguments() != null;
//            if (checkTermOrPrivacy(getArguments())) {
//                setTerms(termsPolicyResponse);
//            } else {
//                setPolicy(termsPolicyResponse);
//            }
            setTerms(termsPolicyResponse);
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        if (termsPresenter != null) {
            termsPresenter = null;
        }
        super.onDestroy();
    }
}
