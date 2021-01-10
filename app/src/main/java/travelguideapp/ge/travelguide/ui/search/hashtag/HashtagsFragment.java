package travelguideapp.ge.travelguide.ui.search.hashtag;

import android.os.Bundle;
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
import travelguideapp.ge.travelguide.enums.SearchPostBy;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.ui.search.SearchActivity;
import travelguideapp.ge.travelguide.ui.upload.tag.AddTagAdapter;

public class HashtagsFragment extends Fragment implements HashtagsFragmentListener {

    private RecyclerView hashtagRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s_hashtags, container, false);
        hashtagRecycler = view.findViewById(R.id.search_hashtags_recycler);
        return view;
    }

    public void setHashtagRecycler(List<HashtagResponse.Hashtags> hashtags) {
        AddTagAdapter addTagAdapter = new AddTagAdapter(this);
        addTagAdapter.setHashtags(hashtags);
        hashtagRecycler.setLayoutManager(new LinearLayoutManager(hashtagRecycler.getContext()));
        hashtagRecycler.setHasFixedSize(true);
        hashtagRecycler.setAdapter(addTagAdapter);
    }


    @Override
    public void onHashtagChoose(HashtagResponse.Hashtags hashtag) {
        try {
            ((BaseActivity) getActivity()).startSearchPostActivity(hashtag.getHashtag(), SearchPostBy.HASHTAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            List<HashtagResponse.Hashtags> hashtags = ((SearchActivity) hashtagRecycler.getContext()).getHashtags();
            if (hashtags != null) {
                setHashtagRecycler(hashtags);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
