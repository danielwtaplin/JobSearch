package com.activities.dwtaplin.jobsearchfinal.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.activities.MainActivity;
import com.activities.dwtaplin.jobsearchfinal.adapters.WatchlistAdapter;
import com.activities.dwtaplin.jobsearchfinal.components.Job;
import com.activities.dwtaplin.jobsearchfinal.database.ServerManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class WatchlistFragment extends android.support.v4.app.Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;

    public WatchlistFragment() {

    }

    public static WatchlistFragment newInstance(String param1, String param2) {
        WatchlistFragment fragment = new WatchlistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).getToolbar().setSubtitle("Watchlist");
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        watchlistTask watchlistTask = new watchlistTask((MainActivity) getActivity());
        watchlistTask.execute();
        return view;
    }

    public void showWatchlist(ArrayList<Job> jobs){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        WatchlistAdapter listAdapter = new WatchlistAdapter(getContext(), jobs);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(listAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class watchlistTask extends AsyncTask {
        private WeakReference<MainActivity> mainActivityWeakReference;
        private ArrayList<Job> jobs;
        public watchlistTask(MainActivity activity){
            this.mainActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            this.jobs = new ServerManager(mainActivityWeakReference.get().getApplicationContext())
                    .getWatchList(mainActivityWeakReference.get().getUser());
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            MainActivity activity = mainActivityWeakReference.get();
            WatchlistFragment.this.showWatchlist(this.jobs);


        }


    }
}
