package travelguideapp.ge.travelguide.ui.webView.policy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseFragment;
import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;
import travelguideapp.ge.travelguide.model.request.TermsPolicyRequest;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;

public class PolicyFragment extends BaseFragment<PolicyPresenter> implements PolicyListener {

    private Button cancelBtn;
    private TextView policyHeader;
    private WebView policyTextWebView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_policy, container, false);

        cancelBtn = view.findViewById(R.id.cancel_btn_policy);
        cancelBtn.setText(getString(R.string.back_btn));
        cancelBtn.setOnClickListener(v -> closeFragment());

        policyHeader = view.findViewById(R.id.policy_header);
        policyTextWebView = view.findViewById(R.id.policy_text);
        policyTextWebView.getSettings();
        policyTextWebView.setBackgroundColor(getResources().getColor(R.color.whiteNone));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadAnimation(cancelBtn, R.anim.anim_swipe_left, 50);
    }

    @Override
    public void onStart() {
        super.onStart();
        attachPresenter(PolicyPresenter.with(this));
        getPolicy();
    }

    private void closeFragment() {
        try {
            getActivity().onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetPolicy(TermsPolicyResponse.Policy policy) {
        try {
            policyHeader.setText(policy.getPolicy_title());
            policyTextWebView.loadData("<html><body>" + policy.getPolicy_text() + "</body></html>", "text/html", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPolicy() {
        try {
            presenter.getPolicy(new TermsPolicyRequest(GlobalPreferences.getLanguageId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
