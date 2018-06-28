package com.bts.android.drewsproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TracksAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private List<Track> tracks;
    private ItemClickListener onItemClickListener;

    public TracksAdapter(List<Track> tracks, ItemClickListener onItemClickListener) {
        this.tracks = tracks;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item_content,
                parent, false);
        final TrackViewHolder mViewHolder = new TrackViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, mViewHolder.getAdapterPosition());
            }
        });
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final TrackViewHolder holder, int position) {
        holder.trackTitle.setText(tracks.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public interface ItemClickListener {
        void onItemClick(View v, int position);
    }
}