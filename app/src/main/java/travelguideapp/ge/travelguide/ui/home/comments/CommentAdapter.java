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
            userImage.setOnClickListener(this);

            userName = itemView.findViewById(R.id.comments_user_name);
            date = itemView.findViewById(R.id.comments_date);
            body = itemView.findViewById(R.id.comments_body);

            bodyMore = itemView.findViewById(R.id.comments_see_more_body);
            bodyMore.setOnClickListener(this);

            replyBtn = itemView.findViewById(R.id.comments_reply_btn);
            replyBtn.setOnClickListener(this);

            showReplies = itemView.findViewById(R.id.comments_view_replies);
            showReplies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

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
                showReplies.setText(body.getContext().getString(R.string.view_replies) + " (" + comments.get(position).getComment_replies_count() + ") ");
            } else {
                showReplies.setVisibility(View.GONE);
            }

            if (comments.get(position).getComment_liked_by_me())
                likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_liked));
            else
                likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_unliked));


            if (comments.get(position).getI_can_edit_comment()) {
                replyBtn.setVisibility(View.VISIBLE);
                setCommentOption(true, position);
            } else {
                replyBtn.setVisibility(View.GONE);
                setCommentOption(false, position);
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

                case R.id.comments_user_image:
                    listener.onUserChoose(comments.get(getLayoutPosition()).getUser_id());
                    break;
            }
        }

        void setCommentLike(int position) {

            if (comments.get(position).getComment_liked_by_me()) {

                if (countPlus > comments.get(position).getComment_likes()) {
                    YoYo.with(Techniques.RubberBand)
                            .onEnd(animator -> likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_unliked)))
                            .duration(250)
                            .playOn(likeBtn);
                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
                    comments.get(position).setComment_liked_by_me(false);
                } else {
                    YoYo.with(Techniques.RubberBand)
                            .onEnd(animator -> likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_unliked)))
                            .duration(250)
                            .playOn(likeBtn);
                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes() - 1));
                    countMinus = comments.get(position).getComment_likes() - 1;
                    comments.get(position).setComment_liked_by_me(false);
                }

            } else {

                if (countMinus < comments.get(position).getComment_likes()) {
                    YoYo.with(Techniques.RubberBand)
                            .onEnd(animator -> likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_liked)))
                            .duration(250)
                            .playOn(likeBtn);
                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
                    comments.get(position).setComment_liked_by_me(true);
                } else {
                    YoYo.with(Techniques.RubberBand)
                            .onEnd(animator -> likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_liked)))
                            .duration(250)
                            .playOn(likeBtn);
                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes() + 1));
                    countPlus = comments.get(position).getComment_likes() + 1;
                    comments.get(position).setComment_liked_by_me(true);
                }
            }


        }

        void setCommentOption(boolean canEdit, int position) {
            itemView.setOnLongClickListener(v -> {
                if (canEdit) {
                    listener.onDeleteChoose(comments.get(position).getComment_id());
                } else {
                    listener.onReportChoose(comments.get(position).getComment_id());
                }
                return true;
            });
        }
    }
}
