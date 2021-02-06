package travelguideapp.ge.travelguide.ui.search.hashtag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.model.parcelable.PostDataSearch;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.ui.search.SearchActivity;
import travelguideapp.ge.travelguide.ui.upload.tag.AddTagAdapter;

public class HashtagsFragment extends Fragment implements HashtagsFragmentListener {

    private RecyclerView hashtagRecycler;
    private AddTagAdapter addTagAdapter;
    private int fromPage = 1;

    private boolean isLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s_hashtags, container, false);
        hashtagRecycler = view.findViewById(R.id.search_hashtags_recycler);
        hashtagRecycler.setLayoutManager(new LinearLayoutManager(hashtagRecycler.getContext()));
        hashtagRecycler.setHasFixedSize(true);
        addTagAdapter = new AddTagAdapter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setHashtags(List<HashtagResponse.Hashtags> hashtags) {
        try {
            isLoading = false;
            addTagAdapter.setHashtags(hashtags);
            if (hashtagRecycler.getAdapter() == null) {
                hashtagRecycler.setAdapter(addTagAdapter);
            }

            hashtagRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if (!isLoading) {
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == hashtags.size() - 1) {
//                            onLazyLoad();
                            isLoading = true;
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHashtagChoose(HashtagResponse.Hashtags hashtag) {
        try {
            ((BaseActivity) getActivity()).startSearchPostActivity(new PostDataSearch(hashtag.getHashtag(), PostDataSearch.SearchBy.HASHTAG));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLazyLoad() {
//        fromPage++;
//        ((SearchActivity) hashtagRecycler.getContext()).getHashtagsNextPage(fromPage);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
