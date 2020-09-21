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

    CommentAdapter(List<CommentResponse.Post_story_comments> comments) {
        this.comments = comments;
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
        CircleImageView userImage;
        RecyclerView commentRepliesRecycler;
        TextView userName, commentBody, commentDate, commentReply, commentReplies, commentLikeCount, commentRepliesHide;
        ImageButton commentLike;


        CommentHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.comments_user_image);
            userName = itemView.findViewById(R.id.comments_user_name);
            commentBody = itemView.findViewById(R.id.comments_comment);
            commentDate = itemView.findViewById(R.id.comments_date);
            commentReply = itemView.findViewById(R.id.comments_reply);
            commentReplies = itemView.findViewById(R.id.comments_replies);
            commentLikeCount = itemView.findViewById(R.id.comments_like_count);
            commentRepliesHide = itemView.findViewById(R.id.comments_hide_replies);
            commentRepliesRecycler = itemView.findViewById(R.id.comments_replies_recycler);
            commentLike = itemView.findViewById(R.id.comments_emoji);
        }

        void bindView(int position) {
            userName.setText(comments.get(position).getNickname());
            HelperMedia.loadCirclePhoto(commentLike.getContext(), comments.get(position).getProfile_pic(), userImage);
            commentBody.setText(comments.get(position).getText());
            commentDate.setText(comments.get(position).getComment_time());
            commentLikeCount.setText(String.valueOf(comments.get(position).getComment_likes()));

            if (comments.get(position).getComment_reply().size() > 0) {
                commentReplies.setVisibility(View.VISIBLE);
                commentReplies.setText(MessageFormat.format("View replies ({0})", comments.get(position).
                        getComment_reply().size()));
            } else {
                commentReplies.setVisibility(View.GONE);
            }

            if (comments.get(position).isComment_liked_by_me()) {
                commentLike.setBackground(commentLike.getContext().getResources().getDrawable(R.drawable.emoji_heart_red, null));
            } else {
                commentLike.setBackground(commentLike.getContext().getResources().getDrawable(R.drawable.emoji_heart, null));
            }

            if (comments.get(position).isI_can_reply_comment()) {
                commentReply.setVisibility(View.VISIBLE);
            } else {
                commentReply.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.comments_replies:
                    commentRepliesHide.setVisibility(View.VISIBLE);
                    commentReplies.setVisibility(View.GONE);
                    commentReply.setVisibility(View.GONE);
                    commentRepliesRecycler.setVisibility(View.VISIBLE);
                    commentRepliesRecycler.setLayoutManager(new LinearLayoutManager(commentLike.getContext()));
                    commentRepliesRecycler.setAdapter(new CommentRepliesAdapter(comments.get(getLayoutPosition()).getComment_reply()));
                    break;

                case R.id.comments_hide_replies:
                    commentRepliesHide.setVisibility(View.GONE);
                    commentReplies.setVisibility(View.VISIBLE);
                    commentRepliesRecycler.setVisibility(View.GONE);
                    break;
            }
        }
    }
}
