package travelguideapp.ge.travelguide.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.helper.MyToaster;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private Context context;

    private ArrayList<String> photosAll;
    private final ArrayList<String> photosInner = new ArrayList<>();

    private ArrayList<String> videosAll;
    private final ArrayList<String> videosInner = new ArrayList<>();

    private boolean isNeedLazyLoad;
    private boolean isLoading;
    private boolean is_image;

    private GalleryAdapter galleryAdapter;
    private RecyclerView galleryRecycler;

    private LottieAnimationView lottieAnimationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_media, container, false);
        galleryRecycler = layout.findViewById(R.id.media_recyclerview);
        lottieAnimationView = layout.findViewById(R.id.item_gallery_loading);

        try {
            if (getArguments() != null) {
                is_image = getArguments().getBoolean("is_image");

                galleryAdapter = new GalleryAdapter(context, is_image);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                galleryRecycler.setLayoutManager(gridLayoutManager);
                galleryRecycler.setHasFixedSize(true);
                int width = HelperMedia.getScreenWidth((Activity) context);
                if (width != 0)
                    galleryAdapter.setItemWidth(width);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLazyPaths(is_image ? 1 : 2);
        initScrollListener(is_image ? 1 : 2);
    }

    private void initScrollListener(int type) {
        switch (type) {
            case 1:
                galleryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                        if (!isLoading && isNeedLazyLoad) {
                            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == photosInner.size() - 1) {
                                //bottom of list!
                                loadMore(1);
                                isLoading = true;
                            }
                        }
                    }
                });
                break;

            case 2:
                galleryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                        if (!isLoading && isNeedLazyLoad) {
                            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == videosInner.size() - 1) {
                                //bottom of list!
                                loadMore(2);
                                isLoading = true;
                            }
                        }
                    }
                });
                break;
        }


    }

    private void loadMore(int type) {
        switch (type) {
            case 1:
                new Handler().postDelayed(() -> {
                    int currentSize = photosInner.size();
                    int nextLimit = currentSize + 15;

                    while (currentSize - 1 < nextLimit && currentSize < photosAll.size()) {
                        photosInner.add(photosAll.get(currentSize));
                        currentSize++;
                    }

                    galleryAdapter.notifyDataSetChanged();

                    isLoading = false;
                }, 500);
                break;

            case 2:
                new Handler().postDelayed(() -> {
                    int currentSize = videosInner.size();
                    int nextLimit = currentSize + 15;

                    while (currentSize - 1 < nextLimit && currentSize < videosAll.size()) {
                        videosInner.add(videosAll.get(currentSize));
                        currentSize++;
                    }

                    galleryAdapter.notifyDataSetChanged();

                    isLoading = false;
                }, 500);
                break;
        }
    }

    private void getLazyPaths(int type) {
        switch (type) {
            case 1:
                new Thread(() -> {
                    try {
                        photosAll = HelperMedia.getImagesPathByDate(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

                new Handler().postDelayed(() -> {
                    if (photosAll == null)
                        return;

                    if (photosAll.size() > 15) {
                        isNeedLazyLoad = true;
                        for (int i = 0; i < 15; i++) {
                            photosInner.add(photosAll.get(i));
                        }
                        galleryAdapter.setItems(photosInner);
                    } else {
                        isNeedLazyLoad = false;
                        galleryAdapter.setItems(photosAll);
                    }

                    lottieAnimationView.setVisibility(View.GONE);
                    galleryRecycler.setAdapter(galleryAdapter);

                }, 2000);
                break;

            case 2:
                new Thread(() -> {
                    try {
                        videosAll = HelperMedia.getVideosPathByDate(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

                new Handler().postDelayed(() -> {
                    if (videosAll == null)
                        return;

                    if (videosAll.size() > 15) {
                        isNeedLazyLoad = true;
                        for (int i = 0; i < 15; i++) {
                            videosInner.add(videosAll.get(i));
                        }
                        galleryAdapter.setItems(videosInner);
                    } else {
                        isNeedLazyLoad = false;
                        galleryAdapter.setItems(videosAll);
                    }

                    lottieAnimationView.setVisibility(View.GONE);
                    galleryRecycler.setAdapter(galleryAdapter);

                }, 2000);
                break;

        }
    }

    private ArrayList<String> fetchMedia(int type) {
        ArrayList<String> listOfAllItems;
        if (type == 1) {
            listOfAllItems = HelperMedia.getImagesPathByDate(context);
            return listOfAllItems;
        }
        listOfAllItems = HelperMedia.getVideosPathByDate(context);
        return listOfAllItems;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public interface ItemCountChangeListener {
        void onItemSelected(String path);

        void onItemRemoved(ArrayList<String> items);
    }
}
