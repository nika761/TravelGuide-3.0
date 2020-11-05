package com.travel.guide.ui.home.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.model.response.CommentResponse;

import java.text.MessageFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private List<CommentResponse.Post_story_comments> comments;

    private CommentListener listener;

    CommentAdapter(CommentListener listener) {
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

        if (this.comments != null && this.comments.size() != 0)
            this.comments.addAll(comments);

        else
            this.comments = comments;

        notifyDataSetChanged();
    }

    void onCommentsChanged(List<CommentResponse.Post_story_comments> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    void onCommentChanged(CommentResponse.Post_story_comments comment, int position) {
        notifyItemChanged(position, comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName, body, date, replyBtn, likeCount, bodyMore, showReplies;
        CircleImageView userImage;
        ImageButton likeBtn;

        int countPlus = -1;
        int countMinus = Integer.MAX_VALUE;


        CommentHolder(@NonNull View itemView) {

            super(itemView);

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

            likeBtn = itemView.findViewById(R.id.comments_like_btn);
            likeBtn.setOnClickListener(this);

        }

        void loadMoreCallback(int position) {

            if (comments.get(position).can_load_more_comments()) {
                if (position == comments.size() - 1) {
                    listener.onLazyLoad(true, comments.get(position).getComment_id());
                } else {
                    listener.onLazyLoad(false, 0);
                }
            } else {
                listener.onLazyLoad(false, 0);
            }

        }

        void bindView(int position) {

            likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
            userName.setText(comments.get(position).getNickname());
            date.setText(comments.get(position).getComment_time());
            body.setText(comments.get(position).getText());

            HelperMedia.loadCirclePhoto(body.getContext(), comments.get(position).getProfile_pic(), userImage);

            if (comments.get(position).getComment_reply().size() > 0) {
                showReplies.setVisibility(View.VISIBLE);
                showReplies.setText(MessageFormat.format("View replies ({0})", comments.get(position).getComment_replies_count()));
            } else {
                showReplies.setVisibility(View.GONE);
            }

            if (comments.get(position).getComment_liked_by_me())
                likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.icon_like_liked, null));
            else
                likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.icon_like_unliked, null));


            if (comments.get(position).getI_can_edit_comment()) {
                setDeleteFunction(true, position);
                replyBtn.setVisibility(View.VISIBLE);
            } else {
                replyBtn.setVisibility(View.GONE);
                setDeleteFunction(false, 0);
            }


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.comments_view_replies:
                    listener.onReplyChoose(comments.get(getLayoutPosition()), false, getLayoutPosition());
                    break;

                case R.id.comments_reply_btn:
                    listener.onReplyChoose(comments.get(getLayoutPosition()), true, getLayoutPosition());
                    break;

                case R.id.comments_like_btn:
                    listener.onLikeChoose(comments.get(getLayoutPosition()).getComment_id());
                    setCommentLike(getLayoutPosition());
                    break;

                case R.id.comments_see_more_body:
                    body.setMaxLines(10);
                    break;
            }
        }

        void setCommentLike(int position) {

            if (comments.get(position).getComment_liked_by_me()) {

                if (countPlus > comments.get(position).getComment_likes()) {
                    likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.icon_like_unliked, null));
                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
                    comments.get(position).setComment_liked_by_me(false);
                } else {
                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes() - 1));
                    likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.icon_like_unliked, null));
                    countMinus = comments.get(position).getComment_likes() - 1;
                    comments.get(position).setComment_liked_by_me(false);
                }

            } else {

                if (countMinus < comments.get(position).getComment_likes()) {
                    likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.icon_like_liked, null));
                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
                    comments.get(position).setComment_liked_by_me(true);
                } else {
                    likeBtn.setBackground(body.getContext().getResources().getDrawable(R.drawable.icon_like_liked, null));
                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes() + 1));
                    countPlus = comments.get(position).getComment_likes() + 1;
                    comments.get(position).setComment_liked_by_me(true);
                }
            }


        }

        void setDeleteFunction(boolean canDelete, int position) {
            if (canDelete) {
                itemView.setOnLongClickListener(v -> {
                    listener.onDeleteChoose(comments.get(position).getComment_id());
                    return true;
                });
            } else {
                itemView.setOnLongClickListener(null);
            }
        }

    }
}
