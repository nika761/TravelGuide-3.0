package com.example.travelguide.login.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.util.Attributes;
import com.example.travelguide.R;
import com.example.travelguide.login.activity.SignInActivity;
import com.example.travelguide.login.adapter.recycler.SavedUserAdapter;
import com.example.travelguide.helper.HelperPref;

public class UsersSavedFragment extends Fragment {

    private Button closeFragmentBtn;
    private TextView addAccount;
    private Context context;
    private Window window;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        window = Objects.requireNonNull(getActivity()).getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(ContextCompat.getColor(context, R.color.blackStatusBar));

        View view = inflater.inflate(R.layout.fragment_saved_users, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler(view);
        initUI(view);
        setClickListeners();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    private void initUI(View view) {
        closeFragmentBtn = view.findViewById(R.id.saved_user_fragment_close_btn);
        addAccount = view.findViewById(R.id.saved_user_add_account);
    }

    private void initRecycler(View view) {
//        RecyclerView recyclerView = view.findViewById(R.id.saved_user_recycler);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        RecyclerView.Adapter mAdapter = new SavedUsersAdapter(getContext(), HelperPref.getSavedUsers(Objects.requireNonNull(getContext())));
//        recyclerView.setAdapter(mAdapter);

        SavedUserAdapter swipeAdapter = new SavedUserAdapter(context, HelperPref.getSavedUsers(context));
        swipeAdapter.setMode(Attributes.Mode.Single);
        RecyclerView swipeRecyclerView = view.findViewById(R.id.saved_user_recycler);
        swipeRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        swipeRecyclerView.setAdapter(swipeAdapter);
        swipeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setClickListeners() {
        closeFragmentBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        addAccount.setOnClickListener(v -> {
            Intent intent = new Intent(context, SignInActivity.class);
            startActivity(intent);
        });
    }

}
