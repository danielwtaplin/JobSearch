package com.activities.dwtaplin.jobsearchfinal.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.activities.MainActivity;
import com.activities.dwtaplin.jobsearchfinal.actors.User;
import com.activities.dwtaplin.jobsearchfinal.adapters.SwipeAdapter;
import com.activities.dwtaplin.jobsearchfinal.components.Job;
import com.activities.dwtaplin.jobsearchfinal.database.ServerManager;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SwipeFragment extends android.support.v4.app.Fragment {

    private OnFragmentInteractionListener mListener;
    private SwipeFlingAdapterView swipeView;
    public SwipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_swipe, container, false);
        swipeView = (SwipeFlingAdapterView) view.findViewById(R.id.swipeView);
        if(((MainActivity)getActivity()).getJobArrayList() == null){
            BackGroundWorker bgWorker = new BackGroundWorker();
            bgWorker.execute();
        }
        else {
            SwipeAdapter adapter = new SwipeAdapter(getContext(), ((MainActivity)getActivity()).getJobArrayList(), ((MainActivity)getActivity()).getUser() );
            swipeView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            swipeView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
                @Override
                public void removeFirstObjectInAdapter() {
                    // this is the simplest way to delete an object from the Adapter (/AdapterView)
                    Log.d("LIST", "removed object!");
                    MainActivity activity = (MainActivity) getActivity();
                    activity.getJobArrayList().remove(0);
                    adapter.notifyDataSetChanged();
                    Task task = new Task(activity, activity.getJobArrayList().get(0));

                }

                @Override
                public void onLeftCardExit(Object dataObject) {
                    // makeToast(getContext(), "No");
                }

                @Override
                public void onRightCardExit(Object dataObject) {
                    //makeToast(getContext(), "Yes");
                }

                @Override
                public void onAdapterAboutToEmpty(int itemsInAdapter) {
                    // Ask for more data here
//                jobs.add(new User());
//                adapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
                }

                @Override
                public void onScroll(float scrollProgressPercent) {
                    View view = swipeView.getSelectedView();
                    // view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    // view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                }
            });
        }
        return view;
    }

    private static class Task extends AsyncTask{
        private WeakReference<MainActivity> mainActivityWeakReference;
        private Job job;
        private User user;
        public Task(MainActivity activity, Job job){
            mainActivityWeakReference = new WeakReference<>(activity);
            this.job = job;
            user = activity.getUser();
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            new ServerManager(mainActivityWeakReference.get()).updateWatchlist(user.getServerId(), job.getId());
            return null;
        }
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

    private class BackGroundWorker extends AsyncTask {
        ArrayList<Job> jobs;
        @Override
        protected Object doInBackground(Object[] objects) {
            ServerManager serverManager = new ServerManager(getContext());
            jobs = serverManager.getListings();
            ((MainActivity) getActivity()).setJobArrayList(jobs);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            SwipeAdapter adapter = new SwipeAdapter(getContext(), jobs, ((MainActivity)getActivity()).getUser());
            swipeView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            swipeView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
                @Override
                public void removeFirstObjectInAdapter() {
                    // this is the simplest way to delete an object from the Adapter (/AdapterView)
                    Log.d("LIST", "removed object!");
                    jobs.remove(0);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onLeftCardExit(Object dataObject) {
                    // makeToast(getContext(), "No");
                }

                @Override
                public void onRightCardExit(Object dataObject) {
                    //makeToast(getContext(), "Yes");
                }

                @Override
                public void onAdapterAboutToEmpty(int itemsInAdapter) {
                    // Ask for more data here
//                jobs.add(new User());
//                adapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
                }

                @Override
                public void onScroll(float scrollProgressPercent) {
                    View view = swipeView.getSelectedView();
                    // view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    // view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                }
            });
            super.onPostExecute(o);
        }
    }
}
