package com.activities.dwtaplin.jobsearchfinal.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.activities.MainActivity;
import com.activities.dwtaplin.jobsearchfinal.actors.User;
import com.activities.dwtaplin.jobsearchfinal.adapters.ProfileInfoAdapter;

import java.util.ArrayList;

public class ProfileFragment extends android.support.v4.app.Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private User user;
    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbar = ((MainActivity) getActivity()).getToolbar();
        user = ((MainActivity)getActivity()).getUser();
        toolbar.setTitle("Your profile");
        toolbar.setSubtitle(user.getFullName());
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtCity = view.findViewById(R.id.txtCity);
        TextView txtCareer = view.findViewById(R.id.txtCareer);
        TextView txtAbout = view.findViewById(R.id.txtAbout);
        txtName.setText(user.getFullName());
        txtCity.setText(user.getLocation());
        txtAbout.setText(user.getDesc());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ArrayList<Pair<String, String>> infoDesc = new ArrayList<Pair<String, String>>();
        Pair<String, String> pair = new Pair<>("Qualifications", user.getQual());
        infoDesc.add(pair);
        pair = new Pair<>("Last employer", "Village Cafe, Martinborough");
        infoDesc.add(pair);
        pair = new Pair<>("Location", user.getLocation());
        infoDesc.add(pair);
        pair = new Pair<>("Studied at", "Wellington Institute of Technology");
        infoDesc.add(pair);
        pair = new Pair<>("Your files", "View your files here");
        infoDesc.add(pair);
        pair = new Pair<>("Member since", "May 2018");
        infoDesc.add(pair);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        ProfileInfoAdapter profileInfoAdapter = new ProfileInfoAdapter(getContext(), infoDesc);
        recyclerView.setAdapter(profileInfoAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        return view;
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

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).getToolbar().setTitle("Job Search");
        ((MainActivity) getActivity()).getToolbar().setSubtitle("");
    }
}
