package com.bts.android.drewsproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * @author ZIaDo on 6/23/18.
 */
class TrackViewHolder extends RecyclerView.ViewHolder {
    public TextView trackTitle;

    public TrackViewHolder(View itemView) {
        super(itemView);
        trackTitle = itemView.findViewById(R.id.tv_name);
    }
}
