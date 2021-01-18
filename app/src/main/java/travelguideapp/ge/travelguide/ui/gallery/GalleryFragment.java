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

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.helper.MyToaster;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private Context context;
    private int widthDiff;

    private ArrayList<String> pathsGlobal;
    private ArrayList<String> photosAll;
    private ArrayList<String> photosInner = new ArrayList<>();
    private ArrayList<String> videosAll;
    private ArrayList<String> videosInner = new ArrayList<>();

    private boolean is_image;
    private boolean isLoading;
    private boolean isNeedLazyLoad;

    private GalleryAdapter galleryAdapter;
    private RecyclerView galleryRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_media, container, false);
        galleryRecycler = layout.findViewById(R.id.media_recyclerview);
//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ViewTreeObserver viewTreeObserver = layout.getViewTreeObserver();
//        viewTreeObserver.addOnGlobalLayoutListener(() -> {
//            layout.getRootView().getDisplay().getMetrics(displayMetrics);
//            widthDiff = displayMetrics.widthPixels;
////                widthDiff = layout.getRootView().getWidth() / 3;
////                try {
////                    layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//        });

//        widthDiff = layout.getRootView().getWidth() / 3;

        try {
            if (getArguments() != null) {
//                int wd = widthDiff / 3;
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
//        getPathsAsync(is_image ? 1 : 2);
        getLazyPaths(is_image ? 1 : 2);
        initScrollListener(is_image ? 1 : 2);
    }

    private void initScrollListener(int type) {
        switch (type) {
            case 1:
                galleryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

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
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

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
                Handler handler = new Handler();
                handler.postDelayed(() -> {
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
                Handler handler1 = new Handler();
                handler1.postDelayed(() -> {
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

    private void getPathsAsync(int type) {
        switch (type) {
            case 1:
                new Thread(() -> {
                    try {
                        pathsGlobal = HelperMedia.getImagesPathByDate(context);
                        if (pathsGlobal != null) {
                            galleryAdapter.setItems(pathsGlobal);
                            galleryRecycler.setAdapter(galleryAdapter);
                        }
                    } catch (Exception e) {
//                        runOnUiThread(() -> MyToaster.getErrorToaster(context, "Content Provider Failed Photos"));
                        e.printStackTrace();
                    }
                }).start();
                break;

            case 2:
                new Thread(() -> {
                    try {
                        pathsGlobal = HelperMedia.getVideosPathByDate(context);
                        if (pathsGlobal != null) {
                            galleryAdapter.setItems(pathsGlobal);
                            galleryRecycler.setAdapter(galleryAdapter);
                        }
                    } catch (Exception e) {
//                        runOnUiThread(() -> MyToaster.getErrorToaster(context, "Content Provider Failed Videos"));
                        e.printStackTrace();
                    }
                }).start();
                break;
        }

    }

    private void getLazyPaths(int type) {
        switch (type) {
            case 1:
                photosAll = HelperMedia.getImagesPathByDate(context);
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    MyToaster.getErrorToaster(context, String.valueOf(photosAll.size()));
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
                    galleryRecycler.setAdapter(galleryAdapter);
                }, 1500);
                break;

            case 2:
                videosAll = HelperMedia.getVideosPathByDate(context);
                Handler handler1 = new Handler();
                handler1.postDelayed(() -> {
                    MyToaster.getErrorToaster(context, String.valueOf(videosAll.size()));
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
                    galleryRecycler.setAdapter(galleryAdapter);
                }, 1500);
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
