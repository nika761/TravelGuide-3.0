package com.example.travelguide.adapter.recycler;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperGlide;

import java.util.ArrayList;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<String> posts;
    private int oldPosition;
    private int currentPosition;

    public PostAdapter(ArrayList<String> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
//        oldPosition = currentPosition;
//        currentPosition = position;
//        if (oldPosition != position) {
//            holder.progressBar.setProgress(0);
//            holder.objectAnimator.end();
//            holder.videoView.stopPlayback();
//        } else if (oldPosition > currentPosition) {
//            holder.videoView.start();
//            holder.objectAnimator.start();
//        }
//        Uri uri = Uri.parse("https://s3.eu-central-1.amazonaws.com/travel-guide-3.0/videos/vvv.mp4?fbclid=IwAR17TbgW3fUJZA0PyUyR68Gaz1EtIODIT036xlmMWseaMk1YaVBGPEF5jFQ");
        holder.videoView.setVideoPath(posts.get(position));
        holder.objectAnimator.setDuration(10000);
        holder.objectAnimator.start();
        holder.videoView.start();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull PostViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull PostViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.objectAnimator.end();
        holder.videoView.stopPlayback();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder implements StoriesProgressView.StoriesListener {
        StoriesProgressView storiesProgressView;
        ImageView imageView;
        ArrayList<String> links;
        ProgressBar progressBar;
        ObjectAnimator objectAnimator;
        VideoView videoView;

        int counter = 0;
        long pressTime = 0L;
        long limit = 500L;

        private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pressTime = System.currentTimeMillis();
                        storiesProgressView.pause();
                        return false;

                    case MotionEvent.ACTION_UP:
                        long now = System.currentTimeMillis();
                        return limit < now - pressTime;
                }
                return false;
            }
        };

        PostViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.image_story);
            storiesProgressView = itemView.findViewById(R.id.stories);
            videoView = itemView.findViewById(R.id.video_story);
            objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);

            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });

//            View reverse = itemView.findViewById(R.id.reverse_post);
//            reverse.setOnClickListener(v -> storiesProgressView.reverse());
//            reverse.setOnTouchListener(onTouchListener);
//
//            View skip = itemView.findViewById(R.id.skip_post);
//            skip.setOnClickListener(v -> storiesProgressView.skip());
//            skip.setOnTouchListener(onTouchListener);

        }

        void getStories(ArrayList<String> links) {
            if (links != null) {
                storiesProgressView.setStoriesCount(links.size());

                storiesProgressView.setStoryDuration(5000L);
                storiesProgressView.setStoriesListener(this);
                if (counter > 0) {
                    counter = 0;
                    storiesProgressView.startStories(counter);
                } else {
                    storiesProgressView.startStories(counter);
                }
                HelperGlide.loadPhoto(imageView.getContext(), links.get(counter), imageView);
            }
        }

        @Override
        public void onNext() {
            HelperGlide.loadPhoto(imageView.getContext(), links.get(++counter), imageView);
        }

        @Override
        public void onPrev() {
            HelperGlide.loadPhoto(imageView.getContext(), links.get(--counter), imageView);
        }

        @Override
        public void onComplete() {
            getStories(links);
        }
    }
}
