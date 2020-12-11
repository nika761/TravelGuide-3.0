package com.travel.guide.ui.editPost.filterActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ja.burhanrashid52.photoeditor.PhotoFilter;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
    private IFilterListener mIFilterListener;
    private List<Pair<String, PhotoFilter>> mPairList = new ArrayList<>();
    private int currentPosition = -1;

    FilterAdapter(IFilterListener IFilterListener) {
        mIFilterListener = IFilterListener;
        setupFilters();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mPairList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageFilterView;
        TextView mTxtFilterName;
        FrameLayout choosedFilter;

        ViewHolder(View itemView) {
            super(itemView);
            mImageFilterView = itemView.findViewById(R.id.imgFilterView);
            mImageFilterView.setOnClickListener(this);

            choosedFilter = itemView.findViewById(R.id.current_filter);

            mTxtFilterName = itemView.findViewById(R.id.txtFilterName);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgFilterView:
                    mIFilterListener.onFilterSelected(mPairList.get(getLayoutPosition()).second);
                    currentPosition = getLayoutPosition();
                    notifyDataSetChanged();
                    break;
            }
        }

        private void bindView(int position) {
            try {
                Pair<String, PhotoFilter> filterPair = mPairList.get(position);
                Bitmap fromAsset = getBitmapFromAsset(itemView.getContext(), filterPair.first);
                if (currentPosition == position) {
                    choosedFilter.setVisibility(View.VISIBLE);
                } else {
                    choosedFilter.setVisibility(View.GONE);
                }
                mImageFilterView.setImageBitmap(fromAsset);
                mTxtFilterName.setText(filterPair.second.name().replace("_", " "));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
            return BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setupFilters() {
//        mPairList.add(new Pair<>("filters/original.jpg", PhotoFilter.NONE));
//        mPairList.add(new Pair<>("filters/auto_fix.png", PhotoFilter.AUTO_FIX));
        mPairList.add(new Pair<>("filters/brightness.png", PhotoFilter.BRIGHTNESS));
        mPairList.add(new Pair<>("filters/contrast.png", PhotoFilter.CONTRAST));
        mPairList.add(new Pair<>("filters/documentary.png", PhotoFilter.DOCUMENTARY));
        mPairList.add(new Pair<>("filters/dual_tone.png", PhotoFilter.DUE_TONE));
        mPairList.add(new Pair<>("filters/fill_light.png", PhotoFilter.FILL_LIGHT));
        mPairList.add(new Pair<>("filters/fish_eye.png", PhotoFilter.FISH_EYE));
        mPairList.add(new Pair<>("filters/grain.png", PhotoFilter.GRAIN));
        mPairList.add(new Pair<>("filters/gray_scale.png", PhotoFilter.GRAY_SCALE));
        mPairList.add(new Pair<>("filters/lomish.png", PhotoFilter.LOMISH));
        mPairList.add(new Pair<>("filters/negative.png", PhotoFilter.NEGATIVE));
        mPairList.add(new Pair<>("filters/posterize.png", PhotoFilter.POSTERIZE));
        mPairList.add(new Pair<>("filters/saturate.png", PhotoFilter.SATURATE));
        mPairList.add(new Pair<>("filters/sepia.png", PhotoFilter.SEPIA));
        mPairList.add(new Pair<>("filters/sharpen.png", PhotoFilter.SHARPEN));
        mPairList.add(new Pair<>("filters/temprature.png", PhotoFilter.TEMPERATURE));
        mPairList.add(new Pair<>("filters/tint.png", PhotoFilter.TINT));
        mPairList.add(new Pair<>("filters/vignette.png", PhotoFilter.VIGNETTE));
        mPairList.add(new Pair<>("filters/cross_process.png", PhotoFilter.CROSS_PROCESS));
        mPairList.add(new Pair<>("filters/b_n_w.png", PhotoFilter.BLACK_WHITE));
    }
}
