package com.example.travelguide.ui.home.comments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.AddCommentReplyRequest;
import com.example.travelguide.model.request.AddCommentRequest;
import com.example.travelguide.model.request.CommentRequest;
import com.example.travelguide.model.request.GetMoreCommentRequest;
import com.example.travelguide.model.request.LikeCommentReplyRequest;
import com.example.travelguide.model.response.CommentResponse;
import com.example.travelguide.model.response.LikeCommentReplyResponse;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class RepliesFragment extends Fragment implements RepliesListener, View.OnClickListener {

    private TextView userName, commentBody, replyBtn, date, likeCount, viewMore;
    private EditText addField;
    private ImageButton likeBtn, addCommentBtn;
    private RecyclerView repliesRecycler;
    private CircleImageView userImage;
    private LottieAnimationView loader;

    private RepliesAdapter repliesAdapter;
    private RepliesPresenter presenter;

    private int storyId, postId, commentId;
    private boolean requestReply;

    private List<CommentResponse.Comment_reply> replies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_replies, container, false);

        presenter = new RepliesPresenter(this);

        userName = view.findViewById(R.id.replies_user_name);
        commentBody = view.findViewById(R.id.replies_body);
        date = view.findViewById(R.id.replies_date);
        likeCount = view.findViewById(R.id.replies_like_count);
        userImage = view.findViewById(R.id.replies_user_image);
        loader = view.findViewById(R.id.replies_loader);
        viewMore = view.findViewById(R.id.replies_view_more);
        likeBtn = view.findViewById(R.id.replies_like);

        addCommentBtn = view.findViewById(R.id.replies_add_btn);
        addCommentBtn.setOnClickListener(this);

        repliesRecycler = view.findViewById(R.id.replies_recycler);

        ImageButton backBtn = view.findViewById(R.id.replies_back_btn);
        backBtn.setOnClickListener(this);

        addField = view.findViewById(R.id.replies_add_field);
        RxTextView.textChanges(addField)
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

        if (getArguments() != null) {
            this.requestReply = getArguments().getBoolean("requestReply");
            this.storyId = getArguments().getInt("storyId");
            this.postId = getArguments().getInt("postId");

            CommentResponse.Post_story_comments currentComment = (CommentResponse.Post_story_comments)
                    getArguments().getSerializable("currentComment");

            if (currentComment != null) {
                userName.setText(currentComment.getNickname());
                commentBody.setText(currentComment.getText());
                date.setText(currentComment.getComment_time());

                if (currentComment.getComment_liked_by_me())
                    likeBtn.setBackground(likeBtn.getContext().getResources().getDrawable(R.drawable.icon_like_liked, null));
                else
                    likeBtn.setBackground(likeBtn.getContext().getResources().getDrawable(R.drawable.icon_like_unliked, null));

                likeCount.setText(String.valueOf(currentComment.getComment_likes()));

                this.commentId = currentComment.getComment_id();

                HelperMedia.loadCirclePhoto(getContext(), currentComment.getProfile_pic(), userImage);
            }

            this.replies = (List<CommentResponse.Comment_reply>) getArguments().getSerializable("repliesComment");

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//            linearLayoutManager.setStackFromEnd(true);
//            linearLayoutManager.setReverseLayout(true);
            repliesRecycler.setLayoutManager(linearLayoutManager);
            repliesAdapter = new RepliesAdapter(this);
            repliesAdapter.setCommentReplies(replies);
            repliesRecycler.setAdapter(repliesAdapter);

        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (requestReply) {
            commentFieldFocus(true);
        } else {
            commentFieldFocus(false);
        }
    }


    @Override
    public void onGetReplies(List<CommentResponse.Comment_reply> replies) {
        loader.setVisibility(View.GONE);
        viewMore.setVisibility(View.GONE);
        repliesAdapter.setCommentReplies(replies);
    }

    @Override
    public void onChooseReply(int commentId) {
        this.commentId = commentId;
        commentFieldFocus(true);
    }

    @Override
    public void onChooseLike(int commentId, int commentReplyId) {
        this.commentId = commentId;
        presenter.addCommentReplyLike(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(userName.getContext()), new LikeCommentReplyRequest(storyId, postId, commentId, commentReplyId));
    }

    @Override
    public void onLikeSuccess(LikeCommentReplyResponse likeCommentReplyResponse) {
        Log.e("commentLikeResponse", likeCommentReplyResponse.getMessage());
    }

    @Override
    public void onLazyLoad(boolean visible, int replyCommentId) {

        if (!visible) {
            viewMore.setVisibility(View.GONE);
        } else {
            viewMore.setVisibility(View.VISIBLE);
            viewMore.setOnClickListener(v -> {
                loader.setVisibility(View.VISIBLE);
                presenter.getReplies(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(userName.getContext()), new GetMoreCommentRequest(replyCommentId));
            });
        }

    }

    @Override
    public void onAddReply(List<CommentResponse.Comment_reply> replies) {
        loader.setVisibility(View.GONE);
        commentFieldFocus(false);
        repliesAdapter.setCommentAdd(replies);
        this.replies = replies;
    }

    @Override
    public void onError(String message) {
        loader.setVisibility(View.GONE);
        Toast.makeText(userName.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void commentFieldFocus(boolean requestFocus) {

        if (getActivity() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                if (requestFocus) {
                    addField.requestFocus();
                    inputMethodManager.showSoftInput(addField, InputMethodManager.SHOW_FORCED);
                } else {
                    addField.getText().clear();
                    addField.clearFocus();
                    inputMethodManager.hideSoftInputFromWindow(addField.getWindowToken(), 0);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.replies_add_btn:

                loader.setVisibility(View.VISIBLE);

                presenter.addCommentReply(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(likeBtn.getContext()),
                        new AddCommentReplyRequest(storyId, postId, commentId, addField.getText().toString()));

                break;

            case R.id.replies_back_btn:
                if (getActivity() != null)
                    getActivity().onBackPressed();
                break;

        }
    }


}
