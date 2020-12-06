package com.travel.guide.ui.login.loggedUsers;

import android.content.Context;
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

import com.daimajia.swipe.util.Attributes;
import com.travel.guide.R;
import com.travel.guide.ui.login.signIn.SignInActivity;
import com.travel.guide.utility.GlobalPreferences;

public class UsersSavedFragment extends Fragment {

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler(view);
        initUI(view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    private void initUI(View view) {

        Button closeFragmentBtn = view.findViewById(R.id.saved_user_fragment_close_btn);
        closeFragmentBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        TextView addAccount = view.findViewById(R.id.saved_user_add_account);
        addAccount.setOnClickListener(v -> {
            Intent intent = new Intent(context, SignInActivity.class);
            startActivity(intent);
        });

    }

    private void initRecycler(View view) {
//        RecyclerView recyclerView = view.findViewById(R.id.saved_user_recycler);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        RecyclerView.Adapter mAdapter = new SavedUsersAdapter(getContext(), GlobalPreferences.getSavedUsers(Objects.requireNonNull(getContext())));
//        recyclerView.setAdapter(mAdapter);

        SavedUserAdapter swipeAdapter = new SavedUserAdapter(context, GlobalPreferences.getSavedUsers(context));
        swipeAdapter.setMode(Attributes.Mode.Single);
        RecyclerView swipeRecyclerView = view.findViewById(R.id.saved_user_recycler);
        swipeRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        swipeRecyclerView.setAdapter(swipeAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
