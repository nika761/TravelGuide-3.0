package com.example.travelguide.ui.home.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.response.CommentResponse;

import java.text.MessageFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private List<CommentResponse.Post_story_comments> comments;
    private CommentFragmentListener listener;
    private int visiblePosition;

    CommentAdapter(List<CommentResponse.Post_story_comments> comments, CommentFragmentListener listener) {
        this.comments = comments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userName, body, date, replyBtn, showReplies, hideReplies, likeCount, bodyMore;
        RecyclerView repliesRecycler;
        CircleImageView userImage;
        ImageButton likeBtn;

        CommentHolder(@NonNull View itemView) {
            super(itemView);

            repliesRecycler = itemView.findViewById(R.id.comments_replies_recycler);

            likeCount = itemView.findViewById(R.id.comments_like_count);
            userImage = itemView.findViewById(R.id.comments_user_image);
            userName = itemView.findViewById(R.id.comments_user_name);
            date = itemView.findViewById(R.id.comments_date);
            body = itemView.findViewById(R.id.comments_body);

            bodyMore = itemView.findViewById(R.id.comments_see_more_body);
            bodyMore.setOnClickListener(this);

            replyBtn = itemView.findViewById(R.id.comments_reply_btn);
            replyBtn.setOnClickListener(this);

            showReplies = itemView.findViewById(R.id.comments_view_replies);
            showReplies.setOnClickListener(this);

            hideReplies = itemView.findViewById(R.id.comments_hide_replies);
            hideReplies.setOnClickListener(this);

            likeBtn = itemView.findViewById(R.id.comments_like_btn);
            likeBtn.setOnClickListener(this);

        }

        void bindView(int position) {
            userName.setText(comments.get(position).getNickname());
            body.setText(comments.get(position).getText());

//            if (body.getLineCount() > 3) {
//                bodyMore.setVisibility(View.VISIBLE);
//            } else {
//                bodyMore.setVisibility(View.GONE);
//            }

            if (visiblePosition == getLayoutPosition()) {

                showReplies.setVisibility(View.GONE);
                replyBtn.setVisibility(View.GONE);

                hideReplies.setVisibility(View.VISIBLE);
                repliesRecycler.setVisibility(View.VISIBLE);

            } else {

                hideReplies.setVisibility(View.GONE);
                repliesRecycler.setVisibility(View.GONE);

                replyBtn.setVisibility(View.VISIBLE);
                showReplies.setVisibility(View.VISIBLE);

            }

            date.setText(comments.get(position).getComment_time());
            likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
            HelperMedia.loadCirclePhoto(body.getContext(), comments.get(position).getProfile_pic(), userImage);

            if (comments.get(position).getComment_reply().size() > 0) {
                showReplies.setVisibility(View.VISIBLE);
                showReplies.setText(MessageFormat.format("View replies ({0})", comments.get(position).getComment_reply().size()));
            } else {
                showReplies.setVisibility(View.GONE);
            }

            if (comments.get(position).isComment_liked_by_me()) {
                likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
            } else {
                likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.ic_icon_like, null));
            }

            if (comments.get(position).isI_can_reply_comment()) {
                replyBtn.setVisibility(View.VISIBLE);
            } else {
                replyBtn.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.comments_view_replies:
//                    if (isRepliesShowing) {
//                        hideReplies.setVisibility(View.GONE);
//                        repliesRecycler.setVisibility(View.GONE);
//                        showReplies.setVisibility(View.VISIBLE);
//                    }
                    visiblePosition = getLayoutPosition();

                    showReplies.setVisibility(View.GONE);
                    replyBtn.setVisibility(View.GONE);

                    hideReplies.setVisibility(View.VISIBLE);
                    repliesRecycler.setVisibility(View.VISIBLE);
                    repliesRecycler.setLayoutManager(new LinearLayoutManager(body.getContext()));
                    repliesRecycler.setAdapter(new CommentRepliesAdapter(comments.get(getLayoutPosition()).getComment_reply()));

                    break;

                case R.id.comments_reply_btn:
                    listener.onReplyChoose(comments.get(getLayoutPosition()).getComment_id());
                    break;

                case R.id.comments_like_btn:
                    listener.onLikeChoose(comments.get(getLayoutPosition()).getComment_id());

                    if (comments.get(getLayoutPosition()).isComment_liked_by_me()) {
                        likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
                        likeCount.setText(String.valueOf(comments.get(getLayoutPosition()).getComment_likes()));
                        comments.get(getLayoutPosition()).setComment_liked_by_me(false);

                    } else {
                        likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
                        likeCount.setText(String.valueOf(comments.get(getLayoutPosition()).getComment_likes() + 1));
                        comments.get(getLayoutPosition()).setComment_liked_by_me(true);
                    }

                    break;

                case R.id.comments_see_more_body:
                    body.setMaxLines(10);
                    break;

                case R.id.comments_hide_replies:
                    visiblePosition = -1;
                    hideReplies.setVisibility(View.GONE);
                    repliesRecycler.setVisibility(View.GONE);
                    showReplies.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
