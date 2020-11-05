package com.travel.guide.ui.home.comments;

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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.travel.guide.R;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.model.request.AddCommentRequest;
import com.travel.guide.model.request.CommentRequest;
import com.travel.guide.model.request.DeleteCommentRequest;
import com.travel.guide.model.request.LikeCommentRequest;
import com.travel.guide.model.response.AddCommentResponse;
import com.travel.guide.model.response.CommentResponse;
import com.travel.guide.model.response.DeleteCommentResponse;
import com.travel.guide.model.response.LikeCommentResponse;
import com.travel.guide.ui.home.HomePageActivity;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class CommentFragment extends Fragment implements CommentListener, View.OnClickListener {

    private CommentPresenter presenter;

    private LottieAnimationView loading;
    private RecyclerView commentRecycler;
    private Context context;
    private TextView commentsHead, loadMore;
    private EditText commentField;
    private ImageButton addCommentBtn;

    private CommentAdapter commentAdapter;

    private int storyId;
    private int postId;
    private int commentPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        if (getArguments() != null) {
            this.storyId = getArguments().getInt("storyId");
            this.postId = getArguments().getInt("postId");
        }

        presenter = new CommentPresenter(this);

        commentRecycler = view.findViewById(R.id.comments_recycler);
        commentsHead = view.findViewById(R.id.comments_head);

        loading = view.findViewById(R.id.loading_comment);
        commentField = view.findViewById(R.id.comments_add_txt);
        loadMore = view.findViewById(R.id.comments_load_more);

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

        presenter.getComments(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new CommentRequest(storyId, postId, 0));

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((HomePageActivity) context).hideBottomNavigation(false);
    }

    @Override
    public void onGetComments(CommentResponse commentResponse) {
        if (commentAdapter == null) {

            commentsHead.setText(MessageFormat.format("Comments  {0}", commentResponse.getCount()));

            commentAdapter = new CommentAdapter(this);
            commentAdapter.setComments(commentResponse.getPost_story_comments());
            commentRecycler.setLayoutManager(new LinearLayoutManager(context));
            commentRecycler.setAdapter(commentAdapter);

            loading.setVisibility(View.GONE);

        } else {
            loading.setVisibility(View.GONE);
            loadMore.setVisibility(View.GONE);
            commentAdapter.setComments(commentResponse.getPost_story_comments());
        }

    }

    @Override
    public void onAddComment(AddCommentResponse addCommentResponse) {
        loading.setVisibility(View.GONE);

        commentFieldFocus(false);
        commentsHead.setText(MessageFormat.format("Comments  {0}", addCommentResponse.getCount()));
        commentAdapter.onCommentsChanged(addCommentResponse.getPost_story_comments());

    }

    @Override
    public void onLikeChoose(int commentId) {
        presenter.likeComment(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new LikeCommentRequest(storyId, postId, commentId));
    }

    @Override
    public void onReplyChoose(CommentResponse.Post_story_comments currentComment, boolean requestReply, int position) {

        this.commentPosition = position;

        Bundle repliesFragmentData = new Bundle();
        repliesFragmentData.putSerializable("currentComment", currentComment);
        repliesFragmentData.putBoolean("requestReply", requestReply);
        repliesFragmentData.putInt("storyId", storyId);
        repliesFragmentData.putInt("postId", postId);

        ((HomePageActivity) context).loadRepliesFragment(repliesFragmentData);

        commentAdapter = null;
//
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(commentRecycler.getContext());
//        View view = View.inflate(getContext(), R.layout.dialog_comment, null);
//        EditText editText = view.findViewById(R.id.dialog____);
//        editText.requestFocus();
//
//        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.showSoftInput(commentField, InputMethodManager.SHOW_FORCED);
//
//        bottomSheetDialog.setContentView(view);
//        bottomSheetDialog.show();

    }

    @Override
    public void onLazyLoad(boolean visible, int commentId) {

        if (!visible) {
            loadMore.setVisibility(View.GONE);
        } else {
            loadMore.setVisibility(View.VISIBLE);
            loadMore.setOnClickListener(v -> {
                loading.setVisibility(View.VISIBLE);
                presenter.getComments(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new CommentRequest(storyId, postId, commentId));
            });
        }

    }

    private void commentFieldFocus(boolean requestFocus) {

        if (getActivity() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                if (requestFocus) {
                    commentField.requestFocus();
                    inputMethodManager.showSoftInput(commentField, InputMethodManager.SHOW_FORCED);
                } else {
                    commentField.getText().clear();
                    commentField.clearFocus();
                    inputMethodManager.hideSoftInputFromWindow(commentField.getWindowToken(), 0);
                }
            }
        }
    }

    @Override
    public void onLikeSuccess(LikeCommentResponse likeCommentResponse) {
        Log.e("commentLikeResponse", likeCommentResponse.getMessage());
    }

    @Override
    public void onDeleteChoose(int commentId) {

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("Delete comment ?")
                .setPositiveButton("Yes", (dialog, which) -> presenter.deleteComment(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new DeleteCommentRequest(postId, storyId, commentId)))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();

    }

    @Override
    public void onDeleted(DeleteCommentResponse deleteCommentResponse) {
        commentsHead.setText(MessageFormat.format("Comments  {0}", deleteCommentResponse.getCount()));
        commentAdapter.onCommentsChanged(deleteCommentResponse.getPost_story_comments());
    }

    @Override
    public void onError(String message) {
        loading.setVisibility(View.GONE);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.comments_add_image_btn:
                loading.setVisibility(View.VISIBLE);
                presenter.addComment(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new AddCommentRequest(storyId, postId, commentField.getText().toString()));
                break;
        }
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

        if (commentAdapter != null) {
            commentAdapter = null;
        }

        super.onDestroy();
    }

}
