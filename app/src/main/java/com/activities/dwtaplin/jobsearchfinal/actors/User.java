package com.activities.dwtaplin.jobsearchfinal.actors;

import com.activities.dwtaplin.jobsearchfinal.components.Job;

import java.util.ArrayList;

/**
 * Created by Daniel on 3/05/2018.
 */

public class User {
    private String fullName, userName, location, firstName, lastName, email, desc, imgFilePath, token;
    private Double lat, lng;
    private int serverId;
    private ArrayList<String> qualifications;
    private ArrayList<Job> watchList, applied;
    public User(String firstName, String lastName, String userName, String location, ArrayList qualifications){

    }
    public User(String firstName, String lastName, String userName, String location,
                ArrayList<String> qualifications, String email, String desc) {
        fullName = firstName + " " + lastName;
        this.userName = userName;
        this.location = location;
        this.email = email;
        this.qualifications = qualifications;
        this.firstName = firstName;
        this.lastName = lastName;
        this.desc = desc;
        
    }

    public User(String firstName, String lastName, String userName, String location,
                String email, String token, String qual, int serverId, String imgFilePath, String desc) {
        this.firstName = firstName;
        this.lastName = lastName;
        fullName = firstName + " " + lastName;
        this.userName = userName;
        this.location = location;
        this.qualifications = new ArrayList<>();
        this.email = email;
        this.serverId = serverId;
        this.imgFilePath = imgFilePath;
        this.token = token;
        this.desc = desc;
        qualifications.add(qual);

    }

    public User(String firstName, String lastName, String userName, String location,
                String email, String qual, int serverId, String imgFilePath, String desc) {
        this.firstName = firstName;
        this.lastName = lastName;
        fullName = firstName + " " + lastName;
        this.userName = userName;
        this.location = location;
        this.qualifications = new ArrayList<>();
        this.email = email;
        this.serverId = serverId;
        this.imgFilePath = imgFilePath;
        this.desc = desc;
        qualifications.add(qual);
    }

    public User(String fName, String lName, String uName, String location, String email, String qual, int id, String filePath, String desc, Double lat, Double lng) {
        this.firstName = fName;
        this.lastName = lName;
        fullName = firstName + " " + lastName;
        this.userName = uName;
        this.location = location;
        this.qualifications = new ArrayList<>();
        this.email = email;
        this.serverId = id;
        this.imgFilePath = filePath;
        this.desc = desc;
        this.lat = lat;
        this.lng = lng;
        qualifications.add(qual);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public int getServerId() {
        return serverId;
    }

    public String getImgFilePath() {
        return imgFilePath;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
    public String getQual(){
        return qualifications.get(0);
    }
}
