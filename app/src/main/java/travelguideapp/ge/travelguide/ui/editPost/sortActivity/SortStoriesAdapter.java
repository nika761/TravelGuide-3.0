package travelguideapp.ge.travelguide.ui.editPost.sortActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.model.ItemMedia;

import java.util.ArrayList;
import java.util.List;

public class SortStoriesAdapter extends RecyclerView.Adapter<SortStoriesAdapter.SortStoriesHolder> {

    private ArrayList<String> stories;
    private List<ItemMedia> itemMedia;

    SortStoriesAdapter(List<ItemMedia> itemMedia) {
        this.itemMedia = itemMedia;
    }

    @NonNull
    @Override
    public SortStoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SortStoriesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort_stories, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SortStoriesHolder holder, int position) {
        holder.sortPosition.setText(String.valueOf(position + 1));
        HelperMedia.loadPhoto(holder.imageView.getContext(), itemMedia.get(position).getPath(), holder.imageView);
    }

    public List<ItemMedia> getItemMedia() {
        return itemMedia;
    }

    @Override
    public int getItemCount() {
        return itemMedia.size();
    }

    class SortStoriesHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView sortPosition;

        SortStoriesHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sort_item_image);
            sortPosition = itemView.findViewById(R.id.sort_item_position);
        }
    }
}
