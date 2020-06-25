package com.example.travelguide.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelguide.R;
import com.example.travelguide.activity.SavedUserActivity;

import java.util.Objects;

public class ForgotPswFragment extends Fragment {

    private TextView backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        window.setStatusBarColor(getResources().getColor(R.color.white));

        View view = inflater.inflate(R.layout.fragment_forgot_psw, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        setClickListeners();
    }

    private void initUI(View view) {
        backBtn = view.findViewById(R.id.forgot_psw_back);
    }

    private void setClickListeners() {
        backBtn.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
    }
}
