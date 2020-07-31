package com.example.travelguide.ui.home.adapter.recycler;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.travelguide.R;
import com.example.travelguide.model.ProgressBarItem;
import com.example.travelguide.ui.home.interfaces.OnLoadFinishListener;
import com.example.travelguide.model.response.PostResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.PostHolder> {

    private List<PostResponseModel.Posts> posts;
    private OnLoadFinishListener onLoadFinishListener;
    private Context context;
    private int postPosition;

    public PostRecyclerAdapter(OnLoadFinishListener onLoadFinishListener, List<PostResponseModel.Posts> posts, Context context) {
        this.posts = posts;
        this.onLoadFinishListener = onLoadFinishListener;
        this.context = context;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        this.postPosition = position;
        if (posts.get(position).getPost_stories() != null) {
            holder.storyRecyclerAdapter.setStories(posts.get(position).getPost_stories());
            if (posts.get(position).getPost_stories().size() > 0) {
                holder.iniStoryBar(posts.get(position).getPost_stories().size());
            }
        } else {
            Toast.makeText(context, "NO STORY", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    class PostHolder extends RecyclerView.ViewHolder implements OnStoryListener {
        private RecyclerView recyclerView;
        private LinearLayout linearLayout;
        private StoryRecyclerAdapter storyRecyclerAdapter;
        private List<ProgressBarItem> progressBarItems = new ArrayList<>();
        private LinearLayoutManager layoutManager;
        private boolean firstLoad = true;
        private int oldPosition = 0;

        PostHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.post_recycler_new);
            linearLayout = itemView.findViewById(R.id.story_container_new_new);
            storyRecyclerAdapter = new StoryRecyclerAdapter(this, onLoadFinishListener);
            initRecycler(recyclerView);
        }

        private void initRecycler(RecyclerView storiesRecycler) {
            layoutManager = new LinearLayoutManager(storiesRecycler.getContext(),
                    RecyclerView.HORIZONTAL, false);
            storiesRecycler.setLayoutManager(layoutManager);
//            videoPlayerRecyclerView.setStories(stories);
            SnapHelper helper = new PagerSnapHelper();
            helper.attachToRecyclerView(storiesRecycler);
            storiesRecycler.setAdapter(storyRecyclerAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
//                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                        //Dragging
//                    } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//
//                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LayerDrawable layerDrawable = (LayerDrawable) context.getResources()
                            .getDrawable(R.drawable.selector_progress_state_list, null);

                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    if (oldPosition > firstVisibleItem) {
                        Drawable drawable = layerDrawable.findDrawableByLayerId(R.id.custom_progress_bar_bg);
                        progressBarItems.get(oldPosition).getProgressBar().clearAnimation();
                        progressBarItems.get(oldPosition).getProgressBar().setProgressDrawable(drawable);
                    }

                    progressBarItems.get(oldPosition).getObjectAnimator().cancel();
                    progressBarItems.get(oldPosition).getObjectAnimator().end();
                    progressBarItems.get(oldPosition).getObjectAnimator().removeAllListeners();
                    progressBarItems.get(oldPosition).getProgressBar().clearAnimation();

                    progressBarItems.get(firstVisibleItem).getProgressBar().setProgressDrawable(layerDrawable);

                    progressBarItems.get(firstVisibleItem).getObjectAnimator()
                            .setTarget(progressBarItems.get(firstVisibleItem).getProgressBar());

                    progressBarItems.get(firstVisibleItem).getObjectAnimator()
                            .setDuration(TimeUnit.SECONDS.toMillis((long)
                                    storyRecyclerAdapter.getStoryDuration(firstVisibleItem)));

                    progressBarItems.get(firstVisibleItem).getObjectAnimator().start();
//                    progressBarItems.get(firstVisibleItem).getObjectAnimator().addListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            super.onAnimationEnd(animation);
////                            if (((LinearLayoutManager) recyclerView.getLayoutManager()).
////                                    findLastVisibleItemPosition() == progressBarItems.size() - 1) {
////                                storyRecyclerAdapter.setStories(posts.get(postPosition).getPost_stories());
////                                iniStoryBar(posts.get(postPosition).getPost_stories().size());
////                            }
//                            recyclerView.post(() -> recyclerView.smoothScrollToPosition(firstVisibleItem + 1));
//                        }
//                    });

                    oldPosition = firstVisibleItem;
                }
            });
        }

        private void iniStoryBar(int count) {
            linearLayout.removeAllViewsInLayout();
//            progressBarItems.clear();
//            firstLoad = true;
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5,
//                    1.0f);

//            Drawable storyBarItemBg = context.getResources().getDrawable(R.drawable.story_bar_item_bg, null);
//            Drawable stateListDrawable = context.getResources().getDrawable(R.drawable.selector_progress_state_list, null);

//            params.leftMargin = 3;
//            params.rightMargin = 3;
            for (int i = 0; i < count; i++) {
//                progressBar = new ProgressBar(context, null,
//                      android.R.attr.progressBarStyleHorizontal);
////                progressBar.setProgressDrawable(storyBarItemBg);
//                progressBar.setProgressDrawable(stateListDrawable);
//                progressBar.setLayoutParams(params);
                progressBarItems.add(i, new ProgressBarItem(new ProgressBar(context, null,
                        android.R.attr.progressBarStyleHorizontal),
                        ObjectAnimator.ofInt(null, "progress", 0, 100)));
                linearLayout.addView(progressBarItems.get(i).getProgressBar(), i);
            }
//
//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//                @Override
//                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//
//                    if (oldPosition < layoutManager.findFirstVisibleItemPosition()) {
//                        int i = layoutManager.findFirstVisibleItemPosition();
//
//                        progressBarItems.get(oldPosition).getObjectAnimator().cancel();
//                        progressBarItems.get(oldPosition).getObjectAnimator().end();
//                        progressBarItems.get(oldPosition).getObjectAnimator().removeAllListeners();
//
//                        progressBarItems.get(i).getObjectAnimator().setTarget(progressBarItems.get(i).getProgressBar());
//                        progressBarItems.get(i).getObjectAnimator().setDuration(TimeUnit.SECONDS.toMillis((long)
//                                storyRecyclerAdapter.getStoryDuration(getLayoutPosition())));
//
//                        progressBarItems.get(i).getObjectAnimator().start();
//
//                        oldPosition = layoutManager.findFirstVisibleItemPosition();
//
//                    } else if (layoutManager.findFirstVisibleItemPosition() > oldPosition) {
//                        int p = layoutManager.findFirstVisibleItemPosition();
//
//                        progressBarItems.get(oldPosition).getObjectAnimator().cancel();
//                        progressBarItems.get(oldPosition).getObjectAnimator().end();
//                        progressBarItems.get(oldPosition).getObjectAnimator().removeAllListeners();
//
//                        progressBarItems.get(p).getObjectAnimator().setTarget(progressBarItems.get(p).getProgressBar());
//                        progressBarItems.get(p).getObjectAnimator().setDuration(TimeUnit.SECONDS.toMillis((long)
//                                storyRecyclerAdapter.getStoryDuration(getLayoutPosition())));
//
//                        progressBarItems.get(p).getObjectAnimator().start();
//
//                        oldPosition = layoutManager.findFirstVisibleItemPosition();
//                    }
//
//                }
//
//
//
////                    else if (newState < oldPosition) {
////
////                        progressBarItems.get(oldPosition + 1).getObjectAnimator().cancel();
////                        progressBarItems.get(oldPosition + 1).getObjectAnimator().end();
////                        progressBarItems.get(oldPosition + 1).getObjectAnimator().removeAllListeners();
////
////                        progressBarItems.get(newState)
////                                .getObjectAnimator()
////                                .setTarget(progressBarItems
////                                        .get(newState)
////                                        .getProgressBar());
////
////                        progressBarItems.get(newState).
////                                getObjectAnimator()
////                                .setDuration(TimeUnit.SECONDS.toMillis((long)
////                                        storyRecyclerAdapter.getStoryDuration(newState)));
////
////                        progressBarItems.get(newState).getObjectAnimator().start();
////
////                        oldPosition = newState;
////
////                    }
//
//            });

//            objectAnimator = new ObjectAnimator();
//            objectAnimator = ObjectAnimator.ofInt(progressBars.get(0), "progress", 0, 100);
//            objectAnimator.setInterpolator(new LinearInterpolator());
//            objectAnimator.start();
//
//            objectAnimator.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    super.onAnimationEnd(animation);
//                }
//            });
        }

        @Override
        public void onGetStories(int currentStoryPosition, int duration) {
            if (firstLoad) {

                progressBarItems.get(currentStoryPosition).getObjectAnimator()
                        .setTarget(progressBarItems.get(currentStoryPosition).getProgressBar());

                progressBarItems.get(currentStoryPosition).getObjectAnimator()
                        .setDuration(TimeUnit.SECONDS.toMillis((long) duration));

                progressBarItems.get(currentStoryPosition).getObjectAnimator().start();

                oldPosition = currentStoryPosition;

                firstLoad = false;
            }
        }

        @Override
        public void onNextStory(int currentStoryPosition, int duration) {
            if (oldPosition < currentStoryPosition) {

                //გადადის შემდეგ სთორზე

                progressBarItems.get(oldPosition).getObjectAnimator().cancel();
                progressBarItems.get(oldPosition).getObjectAnimator().end();
                progressBarItems.get(oldPosition).getObjectAnimator().removeAllListeners();
                progressBarItems.get(oldPosition).getProgressBar().clearAnimation();

                progressBarItems.get(currentStoryPosition).getObjectAnimator()
                        .setTarget(progressBarItems.get(currentStoryPosition).getProgressBar());

                progressBarItems.get(currentStoryPosition).getObjectAnimator()
                        .setDuration(TimeUnit.SECONDS.toMillis((long) duration));

                progressBarItems.get(currentStoryPosition).getObjectAnimator().start();

                oldPosition = currentStoryPosition;

            }

            if (oldPosition > currentStoryPosition) {

                //გადადის წინაზე სთორზე

                progressBarItems.get(currentStoryPosition).getObjectAnimator().cancel();
                progressBarItems.get(currentStoryPosition).getObjectAnimator().end();
                progressBarItems.get(currentStoryPosition).getObjectAnimator().removeAllListeners();
                progressBarItems.get(currentStoryPosition).getProgressBar().clearAnimation();

                progressBarItems.get(oldPosition).getObjectAnimator()
                        .setTarget(progressBarItems.get(currentStoryPosition).getProgressBar());

                progressBarItems.get(oldPosition).getObjectAnimator()
                        .setDuration(TimeUnit.SECONDS.toMillis((long) duration));

                progressBarItems.get(oldPosition).getObjectAnimator().start();

                oldPosition = currentStoryPosition;

            }


        }
//            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(linearLayout.getChildAt(1),
//                    "progress", 0, 100);
//            objectAnimator.setDuration(TimeUnit.SECONDS.toMillis((long) duration));
//            objectAnimator.setInterpolator(new LinearInterpolator());
//            objectAnimator.start();

//        private void setViewPager(ViewPager viewPager) {
//            StoryPagerAdapter storyPagerAdapter = new StoryPagerAdapter(fragmentManager);
//            int size = posts.get(getLayoutPosition()).getPost_stories().size();
//            for (int i = 0; i < size; i++) {
//                storyPagerAdapter.addFragment(newInstance(posts.get(getLayoutPosition()).getPost_stories().get(i).getUrl()
//                        , posts.get(getLayoutPosition()).getPost_stories().get(i).getSecond()));
//            }
//            viewPager.setAdapter(storyPagerAdapter);
//            for (int i = 0; i < size; i++) {
//                tabLayout.addTab(tabLayout.newTab());
//            }
//            tabLayout.setupWithViewPager(viewPager);
//
//            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//                    onLoadFinishListener.onStorySelected(position);
//                    StoryPagerAdapter storyPagerAdapter1 = (StoryPagerAdapter) viewPager.getAdapter();
//                    StoryFragment storyFragment = (StoryFragment) storyPagerAdapter1.getItem(position);
//                    storyFragment.stopVideo();
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
//
//        }
//
//        StoryFragment newInstance(String url, int duration) {
//            StoryFragment myFragment = new StoryFragment();
//
//            Bundle args = new Bundle();
//            args.putString("url", url);
//            args.putInt("duration", duration);
//            myFragment.setArguments(args);
//            return myFragment;
//        }
    }

    public interface OnStoryListener {
        void onGetStories(int currentStoryPosition, int duration);

        void onNextStory(int currentStoryPosition, int duration);
    }
}
