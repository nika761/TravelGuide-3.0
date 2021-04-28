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
import travelguideapp.ge.travelguide.model.response.CommentResponse;

import java.text.MessageFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private List<CommentResponse.Post_story_comments> comments;
    private final CommentListener callback;

    CommentAdapter(CommentListener callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        holder.loadMoreCallback(position);

        holder.bindUI(position);
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

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder {

        TextView userName, body, date, replyBtn, likeCount, bodyMore, showReplies;
        CircleImageView userImage;
        ImageButton likeBtn;

        int countPlus = -1;
        int countMinus = Integer.MAX_VALUE;

        CommentHolder(@NonNull View itemView) {
            super(itemView);

            likeCount = itemView.findViewById(R.id.comments_like_count);

            userImage = itemView.findViewById(R.id.comments_user_image);
            userImage.setOnClickListener(v -> {
                try {
                    callback.onUserChoose(comments.get(getLayoutPosition()).getUser_id());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            userName = itemView.findViewById(R.id.comments_user_name);
            date = itemView.findViewById(R.id.comments_date);
            body = itemView.findViewById(R.id.comments_body);

            bodyMore = itemView.findViewById(R.id.comments_see_more_body);
            bodyMore.setOnClickListener(v -> {
                try {
                    body.setMaxLines(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            replyBtn = itemView.findViewById(R.id.comments_reply_btn);
            replyBtn.setOnClickListener(v -> {
                try {
                    callback.onReplyChoose(comments.get(getLayoutPosition()), true, getLayoutPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            showReplies = itemView.findViewById(R.id.comments_view_replies);
            showReplies.setOnClickListener(v -> {
                try {
                    callback.onReplyChoose(comments.get(getLayoutPosition()), false, getLayoutPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            likeBtn = itemView.findViewById(R.id.comments_like_btn);
            likeBtn.setOnClickListener(v -> {
                try {
                    callback.onLikeChoose(comments.get(getLayoutPosition()).getComment_id());
                    setCommentLike(getLayoutPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        void loadMoreCallback(int position) {
            if (comments.get(position).can_load_more_comments()) {
                if (position == comments.size() - 1) {
                    callback.onLazyLoad(true, comments.get(position).getComment_id());
                } else {
                    callback.onLazyLoad(false, 0);
                }
            } else {
                callback.onLazyLoad(false, 0);
            }
        }

        void bindUI(int position) {
            likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
            userName.setText(comments.get(position).getNickname());
            date.setText(comments.get(position).getComment_time());
            body.setText(comments.get(position).getText());

            HelperMedia.loadCirclePhoto(body.getContext(), comments.get(position).getProfile_pic(), userImage);

            if (comments.get(position).getComment_reply().size() > 0) {
                showReplies.setVisibility(View.VISIBLE);
                showReplies.setText(MessageFormat.format("{0} ({1}) ", body.getContext().getString(R.string.view_replies), comments.get(position).getComment_replies_count()));
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

        void setCommentLike(int position) {
            if (comments.get(position).getComment_liked_by_me()) {
                comments.get(position).setComment_likes(comments.get(position).getComment_likes() - 1);
                comments.get(position).setComment_liked_by_me(false);
                likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
                YoYo.with(Techniques.RubberBand)
                        .onEnd(animator -> likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_unliked)))
                        .duration(250)
                        .playOn(likeBtn);
            } else {
                comments.get(position).setComment_likes(comments.get(position).getComment_likes() + 1);
                comments.get(position).setComment_liked_by_me(true);
                likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
                YoYo.with(Techniques.RubberBand)
                        .onEnd(animator -> likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_liked)))
                        .duration(250)
                        .playOn(likeBtn);
            }
//            if (comments.get(position).getComment_liked_by_me()) {
//
//                if (countPlus > comments.get(position).getComment_likes()) {
//                    YoYo.with(Techniques.RubberBand)
//                            .onEnd(animator -> likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_unliked)))
//                            .duration(250)
//                            .playOn(likeBtn);
//                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
//                    comments.get(position).setComment_liked_by_me(false);
//                } else {
//                    YoYo.with(Techniques.RubberBand)
//                            .onEnd(animator -> likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_unliked)))
//                            .duration(250)
//                            .playOn(likeBtn);
//                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes() - 1));
//                    countMinus = comments.get(position).getComment_likes() - 1;
//                    comments.get(position).setComment_liked_by_me(false);
//                }
//
//            } else {
//
//                if (countMinus < comments.get(position).getComment_likes()) {
//                    YoYo.with(Techniques.RubberBand)
//                            .onEnd(animator -> likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_liked)))
//                            .duration(250)
//                            .playOn(likeBtn);
//                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes()));
//                    comments.get(position).setComment_liked_by_me(true);
//                } else {
//                    YoYo.with(Techniques.RubberBand)
//                            .onEnd(animator -> likeBtn.setBackground(ContextCompat.getDrawable(body.getContext(), R.drawable.icon_like_liked)))
//                            .duration(250)
//                            .playOn(likeBtn);
//                    likeCount.setText(String.valueOf(comments.get(position).getComment_likes() + 1));
//                    countPlus = comments.get(position).getComment_likes() + 1;
//                    comments.get(position).setComment_liked_by_me(true);
//                }
//            }
        }

        void setCommentOption(boolean canEdit, int position) {
            itemView.setOnLongClickListener(v -> {
                if (canEdit) {
                    callback.onDeleteChoose(comments.get(position).getComment_id());
                } else {
                    callback.onReportChoose(comments.get(position).getComment_id());
                }
                return true;
            });
        }
    }
}
