package com.telematics.demoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.MyViewHolder> {

    List<TrackModel> objects;


    private onRecyclerViewItemClickListener mItemClickListener;

    public void setOnItemClickListener(onRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface onRecyclerViewItemClickListener {
        void onItemClickListener(View view, int position, String trackId);
    }

    public TrackAdapter(List<TrackModel> objects) {
        this.objects = objects;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup textView = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trip, parent, false);
        return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TrackModel item = objects.get(position);
        ((TextView) holder.itemView.findViewById(R.id.departureAddressView)).setText("Start address:\n" + item.getAddressStart());
        ((TextView) holder.itemView.findViewById(R.id.destinationAddressView)).setText("End address:\n$" + item.getAddressEnd());
        ((TextView) holder.itemView.findViewById(R.id.departureDateView)).setText("Date start:\n" + item.getStartDate());
        ((TextView) holder.itemView.findViewById(R.id.destinationDateView)).setText("Date end:\n" + item.getEndDate());
        ((TextView) holder.itemView.findViewById(R.id.mileageView)).setText(String.format("Mileage: %.1f km", item.getDistance()));
        ((TextView) holder.itemView.findViewById(R.id.totalTimeView)).setText(String.format("Total time: %d mins", Math.round(item.getDuration())));
        ((View) holder.itemView).setOnClickListener((View.OnClickListener) ((View.OnClickListener) it -> {
            mItemClickListener.onItemClickListener(it, position, item.getTrackId());
        }));

        ((View) holder.itemView.findViewById(R.id.detailsButton)).setOnClickListener((View.OnClickListener) ((View.OnClickListener) it -> {
            mItemClickListener.onItemClickListener(it, position, item.getTrackId());
        }));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
