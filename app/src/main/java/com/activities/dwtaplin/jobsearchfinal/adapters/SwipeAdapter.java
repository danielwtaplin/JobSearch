package com.activities.dwtaplin.jobsearchfinal.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.activities.ViewDocActivity;
import com.activities.dwtaplin.jobsearchfinal.actors.User;
import com.activities.dwtaplin.jobsearchfinal.components.Job;
import com.activities.dwtaplin.jobsearchfinal.database.ServerManager;

import java.util.ArrayList;

/**
 * Created by Daniel on 30/04/2018.
 */

public class SwipeAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<Job> jobs;
    private User user;
    private TextView txtTitle;
    public SwipeAdapter(Context context, ArrayList<Job> jobs, User user) {
        super(context, R.layout.swipe_card_item, jobs);
        this.context = context;
        this.jobs = jobs;
        this.user = user;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.swipe_card_item, parent, false);
        Job job = jobs.get(position);
        txtTitle =  view.findViewById(R.id.txtJobTitle);
        TextView txtLocation = view.findViewById(R.id.txtLocation);
        TextView txtExp = view.findViewById(R.id.txtExperience);
        TextView txtDesc = view.findViewById(R.id.txtDesc);
        TextView txtCompany = view.findViewById(R.id.txtCompany);
        TextView txtSalary = view.findViewById(R.id.txtSalary);
        TextView txtDistance = view.findViewById(R.id.txtDistance);
        Button btnWatchlist = view.findViewById(R.id.btnWatchlist);
        Button btnApply = view.findViewById(R.id.btnApply);
        btnWatchlist.setOnClickListener(v -> addToWatchlist(btnWatchlist, job));
        btnApply.setTag("apply");
        btnApply.setOnClickListener(v -> {
            if(((String)btnApply.getTag()).equals("view")){
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("What would you like to view?");
                String[] choices = {"Cover letter", "CV", "Other documents"};
                dialog.setItems(choices, ((dialogInterface, i) ->{
                    switch(i){
                        case 0:
                            getContext().startActivity(new Intent(getContext(), ViewDocActivity.class));
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                    }
                }));
                dialog.show();
            }
            else {
                AsyncTask task = new AsyncTask() {
                    boolean applied = false;

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        applied = new ServerManager(getContext()).apply(job, user);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        if (applied) {
                            btnApply.setText("view application");
                        } else {
                            Toast.makeText(getContext(), "Sorry something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                task.execute();
                btnApply.setTag("view");
            }
        });
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


    private void addToWatchlist(Button btn, Job job) {
        AsyncTask asyncTask = new AsyncTask() {
            boolean verdict = false;
            @Override
            protected Object doInBackground(Object[] objects) {
                verdict = new ServerManager(getContext()).updateWatchlist(user.getServerId(), job.getId());
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if(verdict)
                    btn.setText("Stop watching");
                else
                    btn.setText("Watchlist");
            }
        };
        asyncTask.execute();
    }

}
