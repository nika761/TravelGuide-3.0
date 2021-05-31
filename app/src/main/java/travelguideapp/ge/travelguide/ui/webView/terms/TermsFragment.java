package travelguideapp.ge.travelguide.ui.webView.terms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;
import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;
import travelguideapp.ge.travelguide.model.request.TermsPolicyRequest;

import java.util.Objects;

public class TermsFragment extends Fragment implements TermsContract.View {

    private Button cancelBtn;
    private TextView termsHeader;
    private WebView termsTextWebView;
    private TermsPresenter termsPresenter;
    private LottieAnimationView animationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_terms, container, false);

        cancelBtn = v.findViewById(R.id.cancel_btn_terms);
        cancelBtn.setText(getString(R.string.back_btn));
        cancelBtn.setOnClickListener(v1 -> Objects.requireNonNull(getActivity()).onBackPressed());

        animationView = v.findViewById(R.id.animation_view);
        termsHeader = v.findViewById(R.id.terms_of_services_header);
        termsTextWebView = v.findViewById(R.id.terms_text);
        termsTextWebView.getSettings();
        termsTextWebView.setBackgroundColor(getResources().getColor(R.color.whiteNone, null));

        termsPresenter = new TermsPresenter(this);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadAnimation(cancelBtn, R.anim.anim_swipe_left, 50);

        termsPresenter.getTerms(new TermsPolicyRequest(GlobalPreferences.getLanguageId()));
    }

    private void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(cancelBtn.getContext(), animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }

    private void updateUI(TermsPolicyResponse.Terms terms) {
        termsHeader.setText(terms.getTerms_title());
        termsTextWebView.loadData("<html><body>" + terms.getTerms_text() + "</body></html>", "text/html", "UTF-8");

        animationView.setVisibility(View.GONE);
    }

    @Override
    public void onGetTerms(TermsPolicyResponse.Terms terms) {
        updateUI(terms);
    }

    @Override
    public void onGetError(String message) {
        animationView.setVisibility(View.GONE);
        MyToaster.showToast(cancelBtn.getContext(), message);
    }

    @Override
    public void onDestroy() {
        if (termsPresenter != null) {
            termsPresenter = null;
        }
        super.onDestroy();
    }
}
