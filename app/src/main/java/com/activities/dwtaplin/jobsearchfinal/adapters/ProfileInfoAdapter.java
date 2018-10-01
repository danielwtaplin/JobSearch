package com.activities.dwtaplin.jobsearchfinal.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activities.dwtaplin.jobsearchfinal.R;

import java.util.ArrayList;

public class ProfileInfoAdapter extends RecyclerView.Adapter<ProfileInfoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Pair<String, String>> infoDesc;
    public ProfileInfoAdapter(Context context, ArrayList<Pair<String, String>> infoDesc) {
        this.context = context;
        this.infoDesc = infoDesc;
    }
    @Override
    public ProfileInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pair<String, String> pair = infoDesc.get(position);
        holder.txtTitle.setText(pair.first);
        holder.txtInfo.setText(pair.second);
    }

    @Override
    public int getItemCount() {
        return infoDesc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtInfo;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtInfo = itemView.findViewById(R.id.txtInfo);
        }
    }
}
