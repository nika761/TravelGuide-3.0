package travelguideapp.ge.travelguide.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.helper.MyToaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {

    private ArrayList<String> paths = new ArrayList<>();
    private HashMap<Integer, Integer> selectedItemPositions = new HashMap<>();
    private Context context;
    private boolean isImage;
    private GalleryFragment.ItemCountChangeListener listener;

    private int itemWidth;

    GalleryAdapter(Context context, boolean isImage) {
        this.isImage = isImage;
        this.context = context;
        listener = (GalleryFragment.ItemCountChangeListener) context;
    }

    @NonNull
    @Override
    public GalleryAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_picker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ImageViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    void setItems(ArrayList<String> uris) {
        this.paths = uris;
        notifyDataSetChanged();
    }

    void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView selectedItemCount, duration;
        View selectItems;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.media_photo);
            imageView.setOnClickListener(this);

            duration = itemView.findViewById(R.id.duration);

            selectedItemCount = itemView.findViewById(R.id.selected_image_item_cont);

            selectItems = itemView.findViewById(R.id.selected_image_item_count_view);
            selectItems.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selected_image_item_count_view:
                    if (selectedItemPositions.size() > 0) {
                        if (selectedItemPositions.containsKey(getLayoutPosition())) {
                            selectedItemPositions.remove(getLayoutPosition());
                            listener.onItemSelected(paths.get(getLayoutPosition()));
                        } else {
                            MyToaster.getToast(context, "You can choose only one item");
                        }
                    } else {
                        selectedItemPositions.put(getLayoutPosition(), selectedItemPositions.size() + 1);
                        listener.onItemSelected(paths.get(getLayoutPosition()));
                    }
//                    if (selectedItemPositions.containsKey(getLayoutPosition())) {
//                        for (Map.Entry<Integer, Integer> integerIntegerEntry : selectedItemPositions.entrySet()) {
//                            if (selectedItemPositions.get(getLayoutPosition()) < (int) ((Map.Entry) integerIntegerEntry).getValue())
//                                selectedItemPositions.put((Integer) ((Map.Entry) integerIntegerEntry).getKey(), ((Integer) ((Map.Entry) integerIntegerEntry).getValue()) - 1);
//                        }
//                        selectedItemPositions.remove(getLayoutPosition());
//                    } else {
//                        selectedItemPositions.put(getLayoutPosition(), selectedItemPositions.size() + 1);
//                    }
                    notifyDataSetChanged();
//                    listener.selectedPaths(getSelectedPaths(), isImage);
//                    if (isImage) {
//                        listener.imageSelectedPaths(uris.get(getLayoutPosition()));
//                    } else {
//                        listener.videoSelectedPaths(uris.get(getLayoutPosition()));
//                    }
                    break;

                case R.id.media_photo:
                    try {
                        Intent intent = new Intent(context, MediaDetailActivity.class);
                        intent.putExtra("is_image", isImage);
                        intent.putExtra("path", paths.get(getLayoutPosition()));
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, imageView, "image_transition");
                        context.startActivity(intent, options.toBundle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        private void bindView(int position) {
            if (paths.get(position) != null && itemWidth != 0) {
                if (isImage) {
                    imageView.getLayoutParams().width = itemWidth;
                    HelperMedia.loadPhoto(context, paths.get(position), imageView);
                } else {
                    imageView.getLayoutParams().width = itemWidth;
                    HelperMedia.loadPhoto(context, paths.get(position), imageView);
                }

                try {
                    if (selectedItemPositions.containsKey(position)) {
                        selectedItemCount.setBackground(context.getDrawable(R.drawable.unselected_image_bg));
                        selectedItemCount.setText(String.valueOf(selectedItemPositions.get(position)));
                    } else {
                        selectedItemCount.setText("");
                        selectedItemCount.setBackground(context.getDrawable(R.drawable.selected_image_circle));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private ArrayList<String> getSelectedPaths() {
        ArrayList<String> selectedPaths = new ArrayList<>();

        for (Map.Entry<Integer, Integer> integerIntegerEntry : selectedItemPositions.entrySet()) {
            selectedPaths.add(paths.get((int) ((Map.Entry) integerIntegerEntry).getKey()));
        }
        return selectedPaths;
    }
}
