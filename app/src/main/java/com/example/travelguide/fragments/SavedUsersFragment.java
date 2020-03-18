package com.example.travelguide.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.activity.SignInActivity;
import com.example.travelguide.adapter.UserListAdapter;
import com.example.travelguide.utils.Utils;

import org.w3c.dom.Text;

import java.util.Objects;

public class SavedUsersFragment extends Fragment {

    private Button closeFragmentBtn;
    private TextView addAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

    private void initUI(View view) {
        closeFragmentBtn = view.findViewById(R.id.saved_user_fragment_close_btn);
        addAccount = view.findViewById(R.id.saved_user_add_account);
    }

    private void initRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.saved_user_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new UserListAdapter(getContext(), Utils.getSavedUsers(Objects.requireNonNull(getContext())));
        recyclerView.setAdapter(mAdapter);
    }

    private void setClickListeners() {
        closeFragmentBtn.setOnClickListener(v -> Objects.requireNonNull(getActivity()).finish());
        addAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SignInActivity.class);
            startActivity(intent);
        });
    }
}
