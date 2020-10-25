package com.example.travelguide.ui.home.comments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.AddCommentReplyRequest;
import com.example.travelguide.model.request.AddCommentRequest;
import com.example.travelguide.model.request.CommentRequest;
import com.example.travelguide.model.request.LikeCommentRequest;
import com.example.travelguide.model.request.SearchMusicRequest;
import com.example.travelguide.model.response.AddCommentReplyResponse;
import com.example.travelguide.model.response.AddCommentResponse;
import com.example.travelguide.model.response.CommentResponse;
import com.example.travelguide.model.response.LikeCommentResponse;
import com.example.travelguide.ui.home.HomePageActivity;
import com.facebook.appevents.suggestedevents.ViewOnClickListener;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class CommentFragment extends Fragment implements CommentFragmentListener, View.OnClickListener {

    private CommentFragmentPresenter presenter;

    private LottieAnimationView loading;
    private RecyclerView commentRecycler;
    private Context context;
    private TextView commentsHead;
    private EditText commentField;
    private ImageButton addCommentBtn;

    private boolean isReply;
    private int commentId;
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

        presenter = new CommentFragmentPresenter(this);

        commentRecycler = view.findViewById(R.id.comments_recycler);
        commentsHead = view.findViewById(R.id.comments_head);

        loading = view.findViewById(R.id.loading_comment);
        commentField = view.findViewById(R.id.comments_add_txt);

        ImageButton closeBtn = view.findViewById(R.id.comments_close_btn);
        closeBtn.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());

        addCommentBtn = view.findViewById(R.id.comments_add_image_btn);
        addCommentBtn.setOnClickListener(this);

        RxTextView.textChanges(commentField)
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .subscribe((Consumer<CharSequence>) charSequence -> {
                    if (charSequence.toString().isEmpty()) {
                        addCommentBtn.setClickable(false);
                        addCommentBtn.setBackground(getResources().getDrawable(R.drawable.icon_add_comment_black, null));
                    } else {
                        addCommentBtn.setClickable(true);
                        addCommentBtn.setBackground(getResources().getDrawable(R.drawable.icon_add_comment, null));
                    }
                });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.getComments(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new CommentRequest(storyId, postId));
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
        commentsHead.setText(MessageFormat.format("Comments  {0}", commentResponse.getCount()));

        CommentAdapter commentAdapter = new CommentAdapter(commentResponse.getPost_story_comments(), this);
        commentRecycler.setLayoutManager(new LinearLayoutManager(context));
        commentRecycler.setAdapter(commentAdapter);

        loading.setVisibility(View.GONE);
    }

    @Override
    public void onAddComment(AddCommentResponse addCommentResponse) {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onAddCommentReply(AddCommentReplyResponse addCommentReplyResponse) {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onLikeChoose(int commentId) {
        presenter.likeComment(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new LikeCommentRequest(storyId, postId, commentId));
    }

    @Override
    public void onReplyChoose(int commendId) {
        this.commentId = commendId;
        this.isReply = true;
        commentField.requestFocus();
        if (getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.showSoftInput(commentField, InputMethodManager.SHOW_FORCED);
        }
    }

    @Override
    public void onCommentLiked(LikeCommentResponse likeCommentResponse) {
        Toast.makeText(context, likeCommentResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        loading.setVisibility(View.GONE);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }

        if (context != null) {
            ((HomePageActivity) context).hideBottomNavigation(true);
            context = null;
        }

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.comments_add_image_btn:
                loading.setVisibility(View.VISIBLE);

                if (isReply) {
                    presenter.addCommentReply(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new AddCommentReplyRequest(storyId, postId, commentId, commentField.getText().toString()));
                } else {
                    presenter.addComment(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new AddCommentRequest(storyId, postId, commentField.getText().toString()));
                }

                isReply = false;

                break;

        }
    }
}
