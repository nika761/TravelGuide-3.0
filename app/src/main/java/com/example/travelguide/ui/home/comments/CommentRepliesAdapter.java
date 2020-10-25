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

public class CommentRepliesAdapter extends RecyclerView.Adapter<CommentRepliesAdapter.RepliesHolder> {

    private List<CommentResponse.Comment_reply> commentReplies;

    CommentRepliesAdapter(List<CommentResponse.Comment_reply> commentReplies) {
        this.commentReplies = commentReplies;
    }

    @NonNull
    @Override
    public RepliesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepliesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_replies, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepliesHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return commentReplies.size();
    }

    class RepliesHolder extends RecyclerView.ViewHolder {
        private TextView userName, commentBody, commentDate, likeCount;
        private CircleImageView userImage;
        private ImageButton like;

        RepliesHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.com_replies_user_name);
            commentBody = itemView.findViewById(R.id.com_replies_body);
            commentDate = itemView.findViewById(R.id.com_replies_date);
            likeCount = itemView.findViewById(R.id.com_replies_like_count);
            userImage = itemView.findViewById(R.id.com_replies_user_image);
            like = itemView.findViewById(R.id.com_replies_like);

        }

        void bindView(int position) {
            HelperMedia.loadCirclePhoto(like.getContext(), commentReplies.get(position).getProfile_pic(), userImage);
            userName.setText(commentReplies.get(position).getNickname());
            commentBody.setText(commentReplies.get(position).getText());
            commentDate.setText(commentReplies.get(position).getReply_time());
        }
    }
}
