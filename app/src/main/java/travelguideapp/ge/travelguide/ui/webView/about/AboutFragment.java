package travelguideapp.ge.travelguide.ui.webView.about;

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
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.AboutRequest;
import travelguideapp.ge.travelguide.model.response.AboutResponse;

public class AboutFragment extends BaseFragment<AboutPresenter> implements AboutListener {

    private WebView aboutTextWebView;
    private TextView aboutHeader;
    private Button cancelBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        cancelBtn = view.findViewById(R.id.cancel_btn_about);
        cancelBtn.setText(getString(R.string.back_btn));
        cancelBtn.setOnClickListener(v -> closeFragment());

        aboutHeader = view.findViewById(R.id.about_header);
        aboutTextWebView = view.findViewById(R.id.about_text);
        aboutTextWebView.getSettings();
        aboutTextWebView.setBackgroundColor(getResources().getColor(R.color.whiteNone, null));

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
        attachPresenter(AboutPresenter.with(this));
        getAbout();
    }

    private void closeFragment() {
        try {
            getActivity().onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAbout() {
        try {
            presenter.getAbout(new AboutRequest(String.valueOf(GlobalPreferences.getLanguageId())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetAbout(AboutResponse.About about) {
        try {
            aboutHeader.setText(about.getAbout_title());
            aboutTextWebView.loadData("<html><body>" + about.getAbout_text() + "</body></html>", "text/html", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
