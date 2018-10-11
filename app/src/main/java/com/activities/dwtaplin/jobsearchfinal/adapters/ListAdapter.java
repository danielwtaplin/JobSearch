package com.activities.dwtaplin.jobsearchfinal.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.components.Job;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Daniel on 1/05/2018.
 */

public class ListAdapter  extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Job> jobs;
    public ListAdapter(Context context, ArrayList<Job> jobs) {
        this.context = context;
        this.jobs = jobs;
    }
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        Job job = jobs.get(position);
        holder.txtCompany.setText("Trademe");
        holder.txtJobTitle.setText(job.getTitle());
        holder.txtLocation.setText(job.getCity());
        Date date = new Date();
        String sDate = date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
        holder.txtDateListed.setText(sDate);
        holder.txtDesc.setText(job.getDesc());
        holder.btnWatchlist.setOnClickListener(v -> {
            holder.btnWatchlist.setText("remove");
        });
    }


    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCompany, txtJobTitle, txtDateListed, txtLocation, txtDesc;
        Button btnWatchlist, btnApply;
        public ViewHolder(View itemView) {
            super(itemView);
            txtCompany = itemView.findViewById(R.id.txtCompany);
            txtJobTitle = itemView.findViewById(R.id.txtJobTitle);
            txtDateListed = itemView.findViewById(R.id.txtDateListed);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            btnWatchlist = itemView.findViewById(R.id.btnWatchlist);
            btnApply = itemView.findViewById(R.id.btnApply);

        }
    }
}
