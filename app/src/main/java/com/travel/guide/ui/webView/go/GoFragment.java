package com.travel.guide.ui.webView.go;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.travel.guide.R;

public class GoFragment extends Fragment {

    public static GoFragment getInstance(String url) {
        GoFragment goFragment = new GoFragment();
        goFragment.url = url;
        return goFragment;
    }

    private WebView webView;
    private String url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_go, container, false);
        webView = view.findViewById(R.id.go_web_view);
        webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true); // enable javascript
        webView.setBackgroundColor(getResources().getColor(R.color.whiteNone, null));
        webView.loadUrl(url);
        webView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == MotionEvent.ACTION_UP && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            return false;
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
