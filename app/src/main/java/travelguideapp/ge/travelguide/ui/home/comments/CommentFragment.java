package travelguideapp.ge.travelguide.ui.home.comments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import gun0912.tedkeyboardobserver.TedKeyboardObserver;
import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.callback.AskingDialogResultCallback;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.ui.home.customerUser.CustomerProfileActivity;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.AddCommentRequest;
import travelguideapp.ge.travelguide.model.request.CommentRequest;
import travelguideapp.ge.travelguide.model.request.DeleteCommentRequest;
import travelguideapp.ge.travelguide.model.request.LikeCommentRequest;
import travelguideapp.ge.travelguide.model.response.AddCommentResponse;
import travelguideapp.ge.travelguide.model.response.CommentResponse;
import travelguideapp.ge.travelguide.model.response.DeleteCommentResponse;
import travelguideapp.ge.travelguide.model.response.LikeCommentResponse;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;


public class CommentFragment extends Fragment implements CommentListener {

    public enum CommentFragmentType {
        COMMENT_REPLY, COMMENT;
    }

    public static CommentFragment getInstance(CommentFragment.LoadCommentFragmentListener callback) {
        CommentFragment commentFragment = new CommentFragment();
        commentFragment.callback = callback;
        return commentFragment;
    }

    private CommentFragment.LoadCommentFragmentListener callback;

    private CommentPresenter presenter;

    private LottieAnimationView loader, pagingLoader;
    private RecyclerView commentRecycler;
    private Context context;
    private TextView commentsHead, loadMore;
    private FrameLayout pagingContainer;
    private TextView commentField;
    private ImageButton addCommentBtn;

    private CommentAdapter commentAdapter;

    private int storyId;
    private int postId;
    private int commentPosition;

    private boolean bottomSheetOpen = false;

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

        loader = view.findViewById(R.id.loading_comment);
        pagingLoader = view.findViewById(R.id.comment_paging_loader);
        commentField = view.findViewById(R.id.comments_add_txt);
        loadMore = view.findViewById(R.id.comments_load_more);
        pagingContainer = view.findViewById(R.id.comments_paging_container);

        ImageButton closeBtn = view.findViewById(R.id.comments_close_btn);
        closeBtn.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());

        addCommentBtn = view.findViewById(R.id.comments_add_image_btn);
//        addCommentBtn.setOnClickListener(v -> addComment(null));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        try {
//            RxTextView.textChanges(commentField)
//                    .debounce(100, TimeUnit.MILLISECONDS)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .map(CharSequence::toString)
//                    .subscribe((Consumer<CharSequence>) charSequence -> {
//                        if (charSequence.toString().isEmpty()) {
//                            addCommentBtn.setClickable(false);
//                            addCommentBtn.setBackground(getResources().getDrawable(R.drawable.icon_add_comment_black, null));
//                        } else {
//                            addCommentBtn.setClickable(true);
//                            addCommentBtn.setBackground(getResources().getDrawable(R.drawable.icon_add_comment, null));
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            commentField.setOnClickListener(v -> openBottomSheetFragment());
        } catch (Exception e) {
            e.printStackTrace();
        }

        presenter.getComments(GlobalPreferences.getAccessToken(context), new CommentRequest(storyId, postId, 0));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            if (context instanceof HomePageActivity) {
                ((HomePageActivity) context).hideBottomNavigation(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetComments(CommentResponse commentResponse) {
        try {
            loader.setVisibility(View.GONE);
            pagingContainer.setVisibility(View.GONE);
            pagingLoader.setVisibility(View.GONE);
            if (commentAdapter == null) {
                commentsHead.setText(MessageFormat.format("{0} {1}", getString(R.string.comments), commentResponse.getCount()));
                commentAdapter = new CommentAdapter(this);
                commentAdapter.setComments(commentResponse.getPost_story_comments());
                commentRecycler.setLayoutManager(new LinearLayoutManager(context));
                commentRecycler.setAdapter(commentAdapter);
            } else {
                loadMore.setVisibility(View.GONE);
                commentAdapter.setComments(commentResponse.getPost_story_comments());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void openBottomSheetFragment() {
        try {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(commentRecycler.getContext());
            View bottomSheetView = View.inflate(commentRecycler.getContext(), R.layout.dialog_comment, null);

            EditText editText = bottomSheetView.findViewById(R.id.bottom_sheet_comment_field);
            ImageButton imageButton = bottomSheetView.findViewById(R.id.bottom_sheet_comment_add_btn);

            imageButton.setOnClickListener(v -> {
                addComment(editText.getText().toString());
                bottomSheetDialog.dismiss();
            });

            RxTextView.textChanges(editText)
                    .debounce(100, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(CharSequence::toString)
                    .subscribe((Consumer<CharSequence>) charSequence -> {
                        if (charSequence.toString().isEmpty()) {
                            imageButton.setClickable(false);
                            imageButton.setBackground(getResources().getDrawable(R.drawable.icon_add_comment_black, null));
                        } else {
                            imageButton.setClickable(true);
                            imageButton.setBackground(getResources().getDrawable(R.drawable.icon_add_comment, null));
                        }
                    });

//                editText.setOnKeyListener((v, keyCode, event) -> {
//                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        inputMethodManager.hideSoftInputFromWindow(bottomSheetView.getWindowToken(), 0);
//                        return true;
//                    }
//                    return false;
//                });

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            bottomSheetDialog.show();
            editText.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAddComment(AddCommentResponse addCommentResponse) {
        try {
            loader.setVisibility(View.GONE);
            commentFieldFocus(false);
            commentsHead.setText(MessageFormat.format("{0} {1}", getString(R.string.comments), addCommentResponse.getCount()));
            commentAdapter.onCommentsChanged(addCommentResponse.getPost_story_comments());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLikeChoose(int commentId) {
        presenter.likeComment(GlobalPreferences.getAccessToken(context), new LikeCommentRequest(storyId, postId, commentId));
    }

    @Override
    public void onReplyChoose(CommentResponse.Post_story_comments currentComment, boolean requestReply, int position) {
        try {
            this.commentPosition = position;

            Bundle repliesFragmentData = new Bundle();
            repliesFragmentData.putSerializable("currentComment", currentComment);
            repliesFragmentData.putBoolean("requestReply", requestReply);
            repliesFragmentData.putInt("storyId", storyId);
            repliesFragmentData.putInt("postId", postId);

            callback.commitCommentFragment(repliesFragmentData, CommentFragmentType.COMMENT_REPLY);
//            try {
//                if (getContext() instanceof HomePageActivity) {
//                    ((HomePageActivity) context).loadRepliesFragment(repliesFragmentData);
//                } else if (getContext() instanceof CustomerProfileActivity) {
//                    ((CustomerProfileActivity) context).loadRepliesFragment(repliesFragmentData);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            commentAdapter = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLazyLoad(boolean visible, int commentId) {

        if (!visible) {
            pagingContainer.setVisibility(View.GONE);
        } else {
            pagingContainer.setVisibility(View.VISIBLE);
            loadMore.setVisibility(View.VISIBLE);
            loadMore.setOnClickListener(v -> {
                pagingLoader.setVisibility(View.VISIBLE);
                loadMore.setVisibility(View.GONE);
                presenter.getComments(GlobalPreferences.getAccessToken(context), new CommentRequest(storyId, postId, commentId));
            });
        }

    }

    private void commentFieldFocus(boolean requestFocus) {

        if (getActivity() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                if (requestFocus) {
//                    commentField.requestFocus();
                    inputMethodManager.showSoftInput(commentField, InputMethodManager.SHOW_FORCED);
                } else {
//                    commentField.getText().clear();
//                    commentField.clearFocus();
                    inputMethodManager.hideSoftInputFromWindow(commentField.getWindowToken(), 0);
                }
            }
        }
    }

    @Override
    public void onLikeSuccess(LikeCommentResponse likeCommentResponse) {
//        Log.e("commentLikeResponse", likeCommentResponse.getMessage());
    }

    @Override
    public void onDeleteChoose(int commentId) {
        DialogManager.getAskingDialog(context, getString(R.string.delete_comment), () -> presenter.deleteComment(GlobalPreferences.getAccessToken(context), new DeleteCommentRequest(postId, storyId, commentId)));
    }

    @Override
    public void onDeleted(DeleteCommentResponse deleteCommentResponse) {
        commentsHead.setText(MessageFormat.format("{0} {1}", getString(R.string.comments), deleteCommentResponse.getCount()));
        commentAdapter.onCommentsChanged(deleteCommentResponse.getPost_story_comments());
    }

    @Override
    public void onError(String message) {
        loader.setVisibility(View.GONE);
        pagingLoader.setVisibility(View.GONE);
        MyToaster.getErrorToaster(context, message);
    }

    private void addComment(String comment) {
        addCommentBtn.setClickable(false);
        loader.setVisibility(View.VISIBLE);
//        if (comment == null)
//            presenter.addComment(GlobalPreferences.getAccessToken(context), new AddCommentRequest(storyId, postId, commentField.getText().toString()));
//        else
        presenter.addComment(GlobalPreferences.getAccessToken(context), new AddCommentRequest(storyId, postId, comment));

    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }

        if (context != null) {
            try {
                if (context instanceof HomePageActivity) {
                    ((HomePageActivity) context).hideBottomNavigation(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (commentAdapter != null) {
            commentAdapter = null;
        }

        super.onDestroy();
    }

    public interface LoadCommentFragmentListener {
        void commitCommentFragment(Bundle dataForFragment, CommentFragmentType commentFragmentType);
    }

}
