package com.activities.dwtaplin.jobsearchfinal.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.activities.MainActivity;
import com.activities.dwtaplin.jobsearchfinal.actors.User;
import com.activities.dwtaplin.jobsearchfinal.database.ServerManager;

import java.lang.ref.WeakReference;


public class UploadFragment extends android.support.v4.app.Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static UploadFragment newInstance(String param1, String param2) {
        UploadFragment fragment = new UploadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public UploadFragment() {
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
        ((MainActivity)getActivity()).getToolbar().setSubtitle("Upload Files");
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        Button btnUpload = view.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(v -> {
            openFileSelector();
        });
        return view;
    }

    private void openFileSelector() {
        Intent intent = new Intent();
        intent.setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), 1 );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1 && data != null){
                Uri fileUri = data.getData();
                Toast.makeText(getContext(), fileUri.toString(), Toast.LENGTH_SHORT).show();
                if(fileUri != null){

                }
            }
        }
    }

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
        void onFragmentInteraction(Uri uri);
    }

    private static class UploadFileTask extends AsyncTask{
        private String type, file, name;
        private User user;
        private boolean success = false;
        private WeakReference<MainActivity> mainActivityWeakReference;
        public UploadFileTask(MainActivity activity, String type, String file, String name){
            mainActivityWeakReference = new WeakReference<>(activity);
            this.type = type;
            this.file = file;
            this.name = name;
            user = activity.getUser();


        }
        @Override
        protected Object doInBackground(Object[] objects) {
            success = new ServerManager(mainActivityWeakReference.get()).uploadFile(type, file, name, user);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            MainActivity activity = mainActivityWeakReference.get();
            if(success){
                Toast.makeText(activity, "Your file has been successfully uploaded", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(o);
        }
    }
}
