package com.example.travelguide.ui.home.comments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.CommentRequest;
import com.example.travelguide.model.response.CommentResponse;
import com.example.travelguide.ui.home.HomePageActivity;

import java.util.Objects;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class CommentFragment extends Fragment implements CommentFragmentListener {

    private CommentFragmentPresenter commentFragmentPresenter;
    private RecyclerView commentRecycler;
    private Context context;
    private int storyId;
    private int postId;

    public CommentFragment(int storyId, int postId) {
        this.storyId = storyId;
        this.postId = postId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        commentFragmentPresenter = new CommentFragmentPresenter(this);

        commentRecycler = view.findViewById(R.id.comments_recycler);

        ImageButton closeBtn = view.findViewById(R.id.comments_close_btn);
        closeBtn.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commentFragmentPresenter.getStoryComments(ACCESS_TOKEN_BEARER +
                HelperPref.getAccessToken(context), new CommentRequest(storyId, postId));
        Log.e("comment", "request send");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (context != null)
            ((HomePageActivity) context).hideBottomNavigation(false);
    }

    @Override
    public void onGetComments(CommentResponse commentResponse) {
        if (commentResponse.getStatus() == 0) {
            Log.e("comment", "request done");
            CommentAdapter commentAdapter = new CommentAdapter(commentResponse.getPost_story_comments());
            commentRecycler.setLayoutManager(new LinearLayoutManager(context));
            commentRecycler.setAdapter(commentAdapter);
        } else {
            Log.e("comment", "request 0");
        }
    }

    @Override
    public void onGetCommentsError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        if (commentFragmentPresenter != null) {
            commentFragmentPresenter = null;
        }

        if (context != null) {
            ((HomePageActivity) context).hideBottomNavigation(true);
            context = null;
        }

        super.onDestroy();
    }
}
