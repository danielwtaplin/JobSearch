package com.activities.dwtaplin.jobsearchfinal.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.components.Job;

import java.util.ArrayList;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Job> jobs;
    public WatchlistAdapter(Context context, ArrayList<Job> jobs) {
        this.context = context;
        this.jobs = jobs;
    }
    @Override
    public WatchlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watchlist_card_item, parent, false);
        return new WatchlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WatchlistAdapter.ViewHolder holder, int position) {
        Job job = jobs.get(position);
        holder.txtCompany.setText(job.getCompany());
        holder.txtTitle.setText(job.getTitle());
        holder.txtLocation.setText(job.getCity());
        holder.txtDesc.setText(job.getDesc());
        if(job.isApplied()){
            holder.watchListLayout.setVisibility(View.GONE);
            holder.appliedLayout.setVisibility(View.VISIBLE);
        } else {
            holder.appliedLayout.setVisibility(View.GONE);
            holder.watchListLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCompany, txtTitle, txtDateListed, txtLocation, txtDesc;
        LinearLayout appliedLayout, watchListLayout;
        Button btnApply, btnDiscard;
        public ViewHolder(View itemView) {
            super(itemView);
            txtCompany = itemView.findViewById(R.id.txtCompany);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            btnApply = itemView.findViewById(R.id.btnApply);
            btnDiscard = itemView.findViewById(R.id.btnDiscard);
            appliedLayout = itemView.findViewById(R.id.appliedLayout);
            watchListLayout = itemView.findViewById(R.id.watchListLayout);

        }
    }
}
