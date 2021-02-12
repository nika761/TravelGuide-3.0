package travelguideapp.ge.travelguide.ui.home.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.response.CommentResponse;

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

    void onRepliesChanged(List<CommentResponse.Comment_reply> replies) {
        this.commentReplies = replies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return commentReplies.size();
    }

    class RepliesHolder extends RecyclerView.ViewHolder {

        private final TextView userName;
        private final TextView commentBody;
        private final TextView commentDate;
        private final TextView likeCount;
        private final TextView replyBtn;
        private final CircleImageView userImage;
        private final ImageButton like;

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
            like.setOnClickListener(v -> {
                try {
                    listener.onChooseLike(commentReplies.get(getLayoutPosition()).getComment_id(), commentReplies.get(getLayoutPosition()).getComment_reply_id());
                    setCommentLike(getLayoutPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }


        void loadMoreCallback(int position) {

            if (commentReplies.get(position).can_load_more_replies()) {
                if (position == commentReplies.size() - 1) {
                    listener.requestLazyLoad(true, commentReplies.get(position).getComment_reply_id());
                } else {
                    listener.requestLazyLoad(false, 0);
                }
            } else {
                listener.requestLazyLoad(false, 0);
            }
        }


        void bindView(int position) {

            HelperMedia.loadCirclePhoto(like.getContext(), commentReplies.get(position).getProfile_pic(), userImage);

            userName.setText(commentReplies.get(position).getNickname());
            commentBody.setText(commentReplies.get(position).getText());
            commentDate.setText(commentReplies.get(position).getReply_time());
            likeCount.setText(String.valueOf(commentReplies.get(position).getReply_likes()));

            if (commentReplies.get(position).isReply_liked_by_me())
                like.setBackground(ContextCompat.getDrawable(like.getContext(), R.drawable.icon_like_liked));
            else
                like.setBackground(ContextCompat.getDrawable(like.getContext(), R.drawable.icon_like_unliked));

            if (commentReplies.get(position).isI_can_reply_comment_reply())
                replyBtn.setVisibility(View.VISIBLE);
            else
                replyBtn.setVisibility(View.GONE);

            setCommentOption(commentReplies.get(position).isI_can_edit_reply(), position);

        }

        void setCommentLike(int position) {
            if (commentReplies.get(position).isReply_liked_by_me()) {
                commentReplies.get(position).setReply_likes(commentReplies.get(position).getReply_likes() - 1);
                commentReplies.get(position).setReply_liked_by_me(false);
                likeCount.setText(String.valueOf(commentReplies.get(position).getReply_likes()));
                YoYo.with(Techniques.RubberBand)
                        .onEnd(animator -> like.setBackground(ContextCompat.getDrawable(like.getContext(), R.drawable.icon_like_unliked)))
                        .duration(250)
                        .playOn(like);
            } else {
                commentReplies.get(position).setReply_likes(commentReplies.get(position).getReply_likes() + 1);
                commentReplies.get(position).setReply_liked_by_me(true);
                likeCount.setText(String.valueOf(commentReplies.get(position).getReply_likes()));
                YoYo.with(Techniques.RubberBand)
                        .onEnd(animator -> like.setBackground(ContextCompat.getDrawable(like.getContext(), R.drawable.icon_like_liked)))
                        .duration(250)
                        .playOn(like);
            }
        }

        void setCommentOption(boolean canEdit, int position) {
            itemView.setOnLongClickListener(v -> {
                if (canEdit) {
                    listener.onChooseDelete(commentReplies.get(position).getComment_reply_id());
                } else {
                    listener.onChooseReport(commentReplies.get(position).getComment_reply_id());
                }
                return true;
            });
        }
    }
}
