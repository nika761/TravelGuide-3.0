package travelguideapp.ge.travelguide.ui.webView.policy;

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

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;
import travelguideapp.ge.travelguide.model.request.TermsPolicyRequest;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

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
        cancelBtn.setText(getString(R.string.back_btn));
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            SystemManager.setLanguage(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadAnimation(cancelBtn, R.anim.anim_swipe_left, 50);

        policyPresenter.sendPolicyResponse(new TermsPolicyRequest(GlobalPreferences.getLanguageId(cancelBtn.getContext())));

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
        MyToaster.getToast(cancelBtn.getContext(), message);
    }
}
