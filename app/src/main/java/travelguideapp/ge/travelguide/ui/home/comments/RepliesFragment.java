package travelguideapp.ge.travelguide.ui.home.comments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.AddCommentReplyRequest;
import travelguideapp.ge.travelguide.model.request.DeleteReplyRequest;
import travelguideapp.ge.travelguide.model.request.GetMoreCommentRequest;
import travelguideapp.ge.travelguide.model.request.LikeCommentReplyRequest;
import travelguideapp.ge.travelguide.model.response.AddCommentReplyResponse;
import travelguideapp.ge.travelguide.model.response.CommentResponse;
import travelguideapp.ge.travelguide.model.response.DeleteReplyResponse;
import travelguideapp.ge.travelguide.model.response.LikeCommentReplyResponse;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;


public class RepliesFragment extends Fragment implements RepliesListener, View.OnClickListener {

    private TextView userName, commentBody, replyBtn, date, likeCount, viewMore;
    private EditText addField;
    private ImageButton likeBtn, addCommentBtn;
    private RecyclerView repliesRecycler;
    private CircleImageView userImage;
    private LottieAnimationView loader;

    private RepliesAdapter repliesAdapter;
    private RepliesPresenter presenter;

    private int storyId, postId, commentId, repliesCount;
    private boolean requestReply;

    private CommentResponse.Post_story_comments currentComment;
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

        ImageButton closeBtn = view.findViewById(R.id.replies_close_btn);
        closeBtn.setOnClickListener(this);

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

            currentComment = (CommentResponse.Post_story_comments) getArguments().getSerializable("currentComment");

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

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//            linearLayoutManager.setStackFromEnd(true);
//            linearLayoutManager.setReverseLayout(true);
                repliesRecycler.setLayoutManager(linearLayoutManager);
                repliesAdapter = new RepliesAdapter(this);
                repliesAdapter.setCommentReplies(currentComment.getComment_reply());
                repliesRecycler.setAdapter(repliesAdapter);

            }
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
        try {
            loader.setVisibility(View.GONE);
            viewMore.setVisibility(View.GONE);
            repliesAdapter.setCommentReplies(replies);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onChooseReply(int commentId) {
        this.commentId = commentId;
        commentFieldFocus(true);
    }

    @Override
    public void onReplyAdded(AddCommentReplyResponse addCommentReplyResponse) {
        try {
            loader.setVisibility(View.GONE);
            repliesRecycler.setVisibility(View.VISIBLE);
            commentFieldFocus(false);
            repliesAdapter.onRepliesChanged(addCommentReplyResponse.getComment_replies());

            this.replies = addCommentReplyResponse.getComment_replies();
            this.repliesCount = addCommentReplyResponse.getCommen_replies_count();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onChooseLike(int commentId, int commentReplyId) {
        this.commentId = commentId;
        presenter.addCommentReplyLike(GlobalPreferences.getAccessToken(userName.getContext()), new LikeCommentReplyRequest(storyId, postId, commentId, commentReplyId));
    }

    @Override
    public void onLiked(LikeCommentReplyResponse likeCommentReplyResponse) {
//        Log.e("commentLikeResponse", likeCommentReplyResponse.getMessage());
    }

    @Override
    public void onChooseDelete(int replyId) {

        AlertDialog alertDialog = new AlertDialog.Builder(likeBtn.getContext())
                .setTitle(getString(R.string.delete_comment))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> presenter.deleteCommentReply(GlobalPreferences.getAccessToken(commentBody.getContext()), new DeleteReplyRequest(replyId)))
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();

    }

    @Override
    public void onDeleted(DeleteReplyResponse deleteReplyResponse) {
        try {
            if (deleteReplyResponse.getComment_reply().size() > 0)
                repliesAdapter.onRepliesChanged(deleteReplyResponse.getComment_reply());
            else
                repliesRecycler.setVisibility(View.INVISIBLE);

            this.replies = deleteReplyResponse.getComment_reply();
            this.repliesCount = deleteReplyResponse.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void requestLazyLoad(boolean visible, int replyCommentId) {

        if (!visible) {
            viewMore.setVisibility(View.GONE);
        } else {
            viewMore.setVisibility(View.VISIBLE);
            viewMore.setOnClickListener(v -> {
                loader.setVisibility(View.VISIBLE);
                presenter.getReplies(GlobalPreferences.getAccessToken(userName.getContext()), new GetMoreCommentRequest(replyCommentId));
            });
        }

    }

    @Override
    public void onError(String message) {
        loader.setVisibility(View.GONE);
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
                presenter.addCommentReply(GlobalPreferences.getAccessToken(likeBtn.getContext()), new AddCommentReplyRequest(storyId, postId, commentId, addField.getText().toString()));
                break;

            case R.id.replies_back_btn:
                try {
                    ((HomePageActivity) likeBtn.getContext()).onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.replies_close_btn:
                try {
                    getActivity().onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void onDestroy() {
//        currentComment.setComment_replies_count(repliesCount);
//        currentComment.setComment_reply(replies);
//
//        CommentListener listener = (CommentListener) repliesRecycler.getContext();
//
//        if (listener != null) {
//            listener.onRepliesSetChanged(currentComment);
//        }

        if (presenter != null)
            presenter = null;

        super.onDestroy();
    }

}