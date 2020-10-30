package com.example.travelguide.ui.home.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.model.response.CommentResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.RepliesHolder> {

    private List<CommentResponse.Comment_reply> commentReplies;

    private RepliesListener listener;

    private int currentPosition = -1;

    RepliesAdapter(RepliesListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RepliesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepliesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_replies, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepliesHolder holder, int position) {
        holder.loadMoreCallback(position);

        holder.bindView(position);
    }

    void setCommentReplies(List<CommentResponse.Comment_reply> commentReplies) {
        if (this.commentReplies != null && this.commentReplies.size() != 0) {
            this.commentReplies.addAll(commentReplies);
        } else {
            this.commentReplies = commentReplies;
        }
        notifyDataSetChanged();
    }

    void setCommentAdd(List<CommentResponse.Comment_reply> replies) {
        this.commentReplies = replies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return commentReplies.size();
    }

    class RepliesHolder extends RecyclerView.ViewHolder {

        private TextView userName, commentBody, commentDate, likeCount, replyBtn;
        private CircleImageView userImage;
        private ImageButton like;

        RepliesHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.com_replies_user_name);
            commentBody = itemView.findViewById(R.id.com_replies_body);
            commentDate = itemView.findViewById(R.id.com_replies_date);
            likeCount = itemView.findViewById(R.id.com_replies_like_count);
            userImage = itemView.findViewById(R.id.com_replies_user_image);

            replyBtn = itemView.findViewById(R.id.com_replies_reply);
            replyBtn.setOnClickListener(v -> listener.onChooseReply(commentReplies.get(getLayoutPosition()).getComment_id()));

            like = itemView.findViewById(R.id.com_replies_like);
        }


        void loadMoreCallback(int position) {

            if (commentReplies.get(position).can_load_more_replies()) {
                if (position == commentReplies.size() - 1) {
                    listener.onLazyLoad(true, commentReplies.get(position).getComment_reply_id());
                } else {
                    listener.onLazyLoad(false, 0);
                }
            } else {
                listener.onLazyLoad(false, 0);
            }
        }


        void bindView(int position) {

            HelperMedia.loadCirclePhoto(like.getContext(), commentReplies.get(position).getProfile_pic(), userImage);

            userName.setText(commentReplies.get(position).getNickname());
            commentBody.setText(commentReplies.get(position).getText());
            commentDate.setText(commentReplies.get(position).getReply_time());
            likeCount.setText(String.valueOf(commentReplies.get(position).getReply_likes()));

            if (commentReplies.get(position).isReply_liked_by_me())
                like.setBackground(like.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
            else
                like.setBackground(like.getContext().getResources().getDrawable(R.drawable.ic_icon_like, null));


            if (commentReplies.get(position).isI_can_reply_comment_reply())
                replyBtn.setVisibility(View.VISIBLE);
            else
                replyBtn.setVisibility(View.GONE);

        }
    }
}
