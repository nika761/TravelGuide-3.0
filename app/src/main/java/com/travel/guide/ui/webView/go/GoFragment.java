package com.travel.guide.ui.webView.go;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

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
        webView.setBackgroundColor(getResources().getColor(R.color.whiteNone, null));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView.loadUrl(url);
    }
}
