package com.activities.dwtaplin.jobsearchfinal.database;

import android.content.Context;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.actors.User;
import com.activities.dwtaplin.jobsearchfinal.components.Job;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonManager {
    private Context context;

    public JsonManager(Context context) {
        this.context = context;
    }
    public User getUser(String json){
        JSONArray jArray;
        try {
            jArray = new JSONArray(json);
            JSONObject jObject = jArray.getJSONObject(0);
            String fName = (String) jObject.get(context.getString(R.string.fName));
            String lName = (String) jObject.get(context.getString(R.string.lName));
            String uName = (String) jObject.get(context.getString(R.string.uName));
            String email = (String) jObject.get(context.getString(R.string.email));
            String qual = (String) jObject.get(context.getString(R.string.qual));
            String desc = (String) jObject.get(context.getString(R.string.bio));
            String location = (String) jObject.get(context.getString(R.string.location));
            String filePath = "";
            Double lat, lng;
            int id =  jObject.getInt(context.getString(R.string.id));
            User user;
            try {
                filePath = (String) jObject.get(context.getString(R.string.img_file_path));
            }catch(Exception e){
                e.printStackTrace();
            }
            user = new User(fName, lName, uName, location, email, qual, id, filePath, desc);
            try{
                lat = jObject.getDouble(context.getString(R.string.lat));
                lng = jObject.getDouble(context.getString(R.string.lng));
                user = new User(fName, lName, uName, location, email, qual, id, filePath, desc, lat, lng);
            }catch (Exception e){
                e.printStackTrace();
            }
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Job> decodeJobs(String json) {
        JSONArray jArray = null;
        ArrayList jobs = new ArrayList<Job>();
        try{
            jArray = new JSONArray(json);
            for( int i = 0; i <jArray.length(); i++){
                JSONObject jObject = jArray.getJSONObject(i);
                int id = jObject.getInt("id");
                String title = (String) jObject.get("title");
                String desc = (String) jObject.get("desc");
                String qual = (String) jObject.get("qual");
                String company = (String) jObject.get("company");
                String location = (String) jObject.get("location");
                Job job;
                try{
                    LatLng latLng = new LatLng(jObject.getDouble("lat"),jObject.getDouble("lng"));
                    job = new Job(id, title, desc, qual, company, location, latLng);
                } catch (Exception e){
                    job = new Job(id, title, desc, qual, company, location);
                }
                try{
                   double wage = jObject.getDouble("wage");
                   job.setWage(wage);
                } catch (Exception e){
                    int salMAx = jObject.getInt("salMax");
                    int salMin = jObject.getInt("salMin");
                    job.setSalaryMax(salMAx);
                    job.setSalaryMin(salMin);
                }
                jobs.add(job);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return jobs;
    }
}
