package com.activities.dwtaplin.jobsearchfinal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.components.Job;

import java.util.ArrayList;

/**
 * Created by Daniel on 30/04/2018.
 */

public class SwipeAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<Job> jobs;
    public SwipeAdapter(Context context, ArrayList<Job> jobs) {
        super(context, R.layout.swipe_card_item, jobs);
        this.context = context;
        this.jobs = jobs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.swipe_card_item, parent, false);
        Job job = jobs.get(position);
        TextView txtTitle =  view.findViewById(R.id.txtJobTitle);
        TextView txtLocation = view.findViewById(R.id.txtLocation);
        TextView txtExp = view.findViewById(R.id.txtExperience);
        TextView txtDesc = view.findViewById(R.id.txtDesc);
        TextView txtCompany = view.findViewById(R.id.txtCompany);
        TextView txtSalary = view.findViewById(R.id.txtSalary);
        TextView txtDistance = view.findViewById(R.id.txtDistance);
        Button btnWatchlist = view.findViewById(R.id.btnWatchlist);
        Button btnApply = view.findViewById(R.id.btnApply);
        btnWatchlist.setOnClickListener(view1 -> addToWatchlist());
        btnApply.setOnClickListener(view12 -> apply());
        txtTitle.setText(job.getTitle());
        txtLocation.setText(job.getCity());
        txtDesc.setText(job.getDesc());
        txtExp.setText(job.getQual());
        txtCompany.setText(job.getCompany());
        if(job.getWage() != null){
            txtSalary.setText("$" + String.valueOf(job.getWage()) + " hourly");
        }
        else {
            txtSalary.setText(String.valueOf(job.getSalaryMin()) + " - " + String.valueOf(job.getSalaryMin()) + "K yearly");
        }
        if(job.getLatLng() != null) {

        }
        return view;
    }

    private void apply() {

    }

    private void addToWatchlist() {

    }

}
