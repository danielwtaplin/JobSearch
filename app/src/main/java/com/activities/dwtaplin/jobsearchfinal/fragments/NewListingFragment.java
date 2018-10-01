package com.activities.dwtaplin.jobsearchfinal.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.activities.MainActivity;
import com.activities.dwtaplin.jobsearchfinal.components.City;
import com.activities.dwtaplin.jobsearchfinal.components.Job;
import com.activities.dwtaplin.jobsearchfinal.database.ServerManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;

public class NewListingFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText editJobTitle, editCompany, editDesc;
    private Spinner jobTypeSpinner, locationSpinner, qualificationsSpinner;
    private CalendarView calenderView;
    private Date closeDate;
    private String mParam1;
    private String mParam2;
    private String city;
    private String qualification;
    private String jobType;
    private Integer wage = null, salaryMin = null, salaryMax = null;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng latLng;

    public NewListingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        checkLocation();
    }

    private void checkLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},1);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Sorry bruh", Toast.LENGTH_SHORT).show();
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        Toast.makeText(getContext(), latLng.toString(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), "Location is null", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getToolbar().setSubtitle("Add listing");
        View view = inflater.inflate(R.layout.fragment_new_listing, container, false);
        Button btnAddListing = view.findViewById(R.id.btnAddListing);
        editJobTitle = view.findViewById(R.id.editJobTitle);
        editCompany = view.findViewById(R.id.editCompany);
        editDesc = view.findViewById(R.id.editDesc);
        jobTypeSpinner = view.findViewById(R.id.jobTypeSpinner);
        locationSpinner = view.findViewById(R.id.jobLocationSpinner);
        Button btnCalender = view.findViewById(R.id.btnCalender);
        qualificationsSpinner = view.findViewById(R.id.qualificationsSpinner);
        btnAddListing.setOnClickListener(view1 -> addListing());
        btnCalender.setOnClickListener(view2 -> showCalender());
        ArrayList cities = new ArrayList();
        for( City c : EnumSet.allOf(City.class)){
            cities.add(String.valueOf(c));
        }
        locationSpinner.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.location, R.layout.spinner_resource));
        qualificationsSpinner.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.qualification, R.layout.spinner_resource));
        jobTypeSpinner.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.jobType, R.layout.spinner_resource));
        Button btnWage = view.findViewById(R.id.btnWage);
        Button btnSalary = view.findViewById(R.id.btnAddSalary);
        btnWage.setOnClickListener(view12 -> showWagePopup());
        btnSalary.setOnClickListener(view13 -> showSalaryPopup());
        return view;
    }

    private void addListing() {
        if(editJobTitle.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Must enter job title", Toast.LENGTH_SHORT).show();
            return;
        }
        if(editCompany.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Must enter company", Toast.LENGTH_SHORT).show();
            return;
        }
        if(editDesc.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Must enter job description", Toast.LENGTH_SHORT).show();
            return;
        }
        if(wage == null && salaryMax == null && salaryMin == null){
            Toast.makeText(getContext(), "Must select either wage or salary", Toast.LENGTH_SHORT).show();
            return;
        }
        if(wage == null && (salaryMax == null || salaryMin == null)){
            Toast.makeText(getContext(), "Must select both minimum and maximum salary", Toast.LENGTH_SHORT).show();
            return;
        }
        if(calenderView == null){
            Toast.makeText(getContext(), "Must select a closing date", Toast.LENGTH_SHORT).show();
            return;
        }
        String jobTitle = editJobTitle.getText().toString();
        String company = editCompany.getText().toString();
        String city = locationSpinner.getSelectedItem().toString();
        String desc = editDesc.getText().toString();
        String qual = qualificationsSpinner.getSelectedItem().toString();
        jobType = jobTypeSpinner.getSelectedItem().toString();
        editJobTitle.setText("");
        editCompany.setText("");
        editDesc.setText("");
        if(calenderView != null) {
            if (closeDate.before(new Date()) || closeDate == new Date()) {
                Toast.makeText(getContext(), "Must select a date past today", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Job job;
        if(wage != null)
            job = new Job(jobTitle, city, desc, qual, company, closeDate, new Double(wage), jobType);
        else
            job = new Job(jobTitle, city, desc, qual, company, closeDate, salaryMin, salaryMax, jobType);
        BackgroundWorker backgroundWorker = new BackgroundWorker(job);
        backgroundWorker.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showCalender() {
        AlertDialog.Builder alertAdd = new AlertDialog.Builder(getContext());
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.calender_popup, null);
        calenderView =  view.findViewById(R.id.calender);
        calenderView.setOnDateChangeListener((calendarView, year, month, day) -> closeDate = new Date(year, month, day));
        alertAdd.setView(view);
        alertAdd.show();
    }

    private void showWagePopup() {
        AlertDialog.Builder alertAdd = new AlertDialog.Builder(getContext());
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.wage_layout, null);
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                wage = value;
            }
        });
        alertAdd.setView(view);
        alertAdd.show();
    }

    private void showSalaryPopup() {
        AlertDialog.Builder alertAdd = new AlertDialog.Builder(getContext());
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.salary_layout, null);
        SeekBar seekBarMin = view.findViewById(R.id.seekBarMin);
        SeekBar seekBarMax = view.findViewById(R.id.seekBarMax);
        seekBarMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                salaryMin = value;
            }
        });
        seekBarMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                salaryMax = value;
            }
        });
        alertAdd.setView(view);
        alertAdd.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).getToolbar().setSubtitle("");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner gSpinner = (Spinner) adapterView;
        TextView spinnerText = (TextView) view;
        if(gSpinner.getId() == R.id.jobLocationSpinner)
            city = spinnerText.getText().toString();
        else if(gSpinner.getId() == R.id.qualificationsSpinner)
            qualification = spinnerText.getText().toString();
        else if(gSpinner.getId() == R.id.jobTypeSpinner)
            jobType = spinnerText.getText().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class BackgroundWorker extends AsyncTask{
        Job job;
        Pair<Boolean, String> results;
        public BackgroundWorker(Job job){
            this.job = job;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            ServerManager serverManager = new ServerManager(getContext());
            results = serverManager.addListing(((MainActivity)getActivity()).getUser().getServerId(),job);
            if(latLng != null && results.first){
                 int id = Integer.valueOf(results.second);
                 serverManager.addLatLngToListing(id, latLng);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(results.first) {
                Toast.makeText(getContext(), "Listing has been added", Toast.LENGTH_SHORT).show();
                Fragment fragment = new MainFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
            }
            else
                Toast.makeText(getContext(), results.second, Toast.LENGTH_SHORT).show();


        }
    }
}
