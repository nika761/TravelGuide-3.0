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

    private int visiblePosition = -1;

    CommentAdapter(CommentFragmentListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {

        holder.loadMoreCallback(position);

        holder.bindView(position);

    }

    void setComments(List<CommentResponse.Post_story_comments> comments) {
        if (this.comments != null && this.comments.size() != 0) {
            this.comments.addAll(comments);
        } else {
            this.comments = comments;
        }
        notifyDataSetChanged();
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

        void loadMoreCallback(int position) {

            if (comments.get(position).can_load_more_comments()) {
                if (position == comments.size() - 1) {
                    listener.onMoreCommentCallback(true, comments.get(position).getComment_id());
                } else {
                    listener.onMoreCommentCallback(false, 0);
                }
            } else {
                listener.onMoreCommentCallback(false, 0);
            }

        }

        void bindView(int position) {

            likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
            userName.setText(comments.get(position).getNickname());
            date.setText(comments.get(position).getComment_time());
            body.setText(comments.get(position).getText());

            HelperMedia.loadCirclePhoto(body.getContext(), comments.get(position).getProfile_pic(), userImage);

            if (visiblePosition == position)
                showReplies(true, visiblePosition);
            else
                showReplies(false, visiblePosition);


            if (comments.get(position).getComment_reply().size() > 0) {
                showReplies.setVisibility(View.VISIBLE);
                showReplies.setText(MessageFormat.format("View replies ({0})", comments.get(position).getComment_replies_count()));
            } else {
                showReplies.setVisibility(View.GONE);
            }

            if (comments.get(position).getComment_liked_by_me())
                likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
            else
                likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.ic_icon_like, null));


            if (comments.get(position).getI_can_reply_comment())
                replyBtn.setVisibility(View.VISIBLE);
            else
                replyBtn.setVisibility(View.GONE);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.comments_view_replies:
                    showReplies(true, getLayoutPosition());
                    break;

                case R.id.comments_reply_btn:
                    listener.onReplyChoose(comments.get(getLayoutPosition()).getComment_id());
                    break;

                case R.id.comments_like_btn:

                    listener.onLikeChoose(comments.get(getLayoutPosition()).getComment_id());

                    setCommentLike(getLayoutPosition());

                    break;

                case R.id.comments_see_more_body:
                    body.setMaxLines(10);
                    break;

                case R.id.comments_hide_replies:
                    showReplies(false, getLayoutPosition());
                    break;
            }
        }

        void showReplies(boolean show, int position) {

            if (show) {

                showReplies.setVisibility(View.GONE);
                replyBtn.setVisibility(View.GONE);

                hideReplies.setVisibility(View.VISIBLE);
                repliesRecycler.setVisibility(View.VISIBLE);

                repliesRecycler.setLayoutManager(new LinearLayoutManager(body.getContext()));
                repliesRecycler.setAdapter(new CommentRepliesAdapter(comments.get(position).getComment_reply()));

                visiblePosition = position;

            } else {

                hideReplies.setVisibility(View.GONE);
                repliesRecycler.setVisibility(View.GONE);

                showReplies.setVisibility(View.VISIBLE);

                if (comments.get(getLayoutPosition()).getI_can_reply_comment())
                    replyBtn.setVisibility(View.VISIBLE);
                else
                    replyBtn.setVisibility(View.GONE);

                visiblePosition = -1;

            }

        }

        void setCommentLike(int position) {

            if (comments.get(position).getComment_liked_by_me()) {

                likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.ic_icon_like, null));
                likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));

                comments.get(position).setComment_liked_by_me(false);

            } else {

                likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
                likeCount.setText(String.valueOf(comments.get(position).getComment_likes() + 1));

                comments.get(position).setComment_liked_by_me(true);

            }

        }

    }
}
