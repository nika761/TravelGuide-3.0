package travelguideapp.ge.travelguide.ui.search.hashtag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.HomeParentActivity;
import travelguideapp.ge.travelguide.model.parcelable.PostSearchParams;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.ui.search.SearchActivity;
import travelguideapp.ge.travelguide.ui.upload.tag.AddTagAdapter;

public class HashtagsFragment extends Fragment implements HashtagsFragmentListener {

    private RecyclerView hashtagRecycler;
    private AddTagAdapter hashtagAdapter;
    private LinearLayout nothingFound;
    private int fromPage = 1;

    private boolean isLoading = false;
    private boolean isFirstOpen = true;
    private List<HashtagResponse.Hashtags> hashtags;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s_hashtags, container, false);
        nothingFound = view.findViewById(R.id.nothing_found_users);
        hashtagRecycler = view.findViewById(R.id.search_hashtags_recycler);
        hashtagRecycler.setLayoutManager(new LinearLayoutManager(hashtagRecycler.getContext()));
        hashtagRecycler.setHasFixedSize(true);
        hashtagAdapter = new AddTagAdapter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addOnScrollListener();
    }

    public void addOnScrollListener() {
        hashtagRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if (!isLoading) {
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == hashtags.size() - 1) {
                            isLoading = true;
                            fromPage++;
                            onLazyLoad(fromPage);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setHashtags(List<HashtagResponse.Hashtags> hashtags) {
        try {
            nothingFound.setVisibility(View.GONE);
            hashtagRecycler.setVisibility(View.VISIBLE);

            this.hashtags = hashtags;
            hashtagAdapter.setHashtags(this.hashtags);

            if (hashtagRecycler.getAdapter() == null) {
                hashtagRecycler.setAdapter(hashtagAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNothingFound() {
        try {
            hashtagRecycler.setVisibility(View.GONE);
            nothingFound.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setLazyHashtags(List<HashtagResponse.Hashtags> hashtags) {
        try {
            isLoading = false;
            this.hashtags = hashtags;
            hashtagAdapter.setHashtags(this.hashtags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHashtagChoose(HashtagResponse.Hashtags hashtag) {
        try {
            ((HomeParentActivity) getActivity()).startSearchPostActivity(new PostSearchParams(hashtag.getHashtag(), PostSearchParams.SearchBy.HASHTAG));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLazyLoad(int page) {
        try {
            ((SearchActivity) hashtagRecycler.getContext()).getHashtagsNextPage(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getCachedHashtags();
    }

    private void getCachedHashtags() {
        try {
            List<HashtagResponse.Hashtags> hashtags = ((SearchActivity) hashtagRecycler.getContext()).getHashtags();
            if (hashtags != null) {
                setHashtags(hashtags);
                if (isFirstOpen)
                    isFirstOpen = false;
            } else {
                if (!isFirstOpen) {
                    setNothingFound();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
