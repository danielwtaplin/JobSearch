package com.activities.dwtaplin.jobsearchfinal.components;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Daniel on 30/04/2018.
 */

public class Job {
    private LatLng latLng;
    private String title, city, desc, qual, company, type;
    private Date dateListed, closeDate;
    private boolean applied;
    private Double wage = null;
    private int salaryMin, salaryMax;
    private int id;
    public Job(String title, String city, String desc, String qual, String company) {
        this.title = title;
        this.city = city;
        this.desc = desc;
        this.qual = qual;
        this.company = company;
        dateListed = new Date();
        applied = false;
        //delete this constructor later

    }

    public Job(String title, String city, String desc, String qual, String company, Date closeDate, Double wage, String type) {
        this.title = title;
        this.city = city;
        this.desc = desc;
        this.qual = qual;
        this.company = company;
        dateListed = new Date();
        applied = false;
        this.closeDate = closeDate;
        this.wage = wage;
        this.type = type;
    }
    public Job(String title, String city, String desc, String qual, String company, Date closeDate, int salaryMin, int salaryMax, String type) {
        this.title = title;
        this.city = city;
        this.desc = desc;
        this.qual = qual;
        this.company = company;
        dateListed = new Date();
        applied = false;
        this.closeDate = closeDate;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.type = type;
    }

    public Job(String title, String city, String desc, String qual, String company, boolean applied) {
        this.title = title;
        this.city = city;
        this.desc = desc;
        this.qual = qual;
        this.company = company;
        dateListed = new Date();
        this.applied = applied;

    }

    public Job(int id, String title, String desc, String qual, String company, String location){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.qual = qual;
        this.company = company;
        this.city = location;
    }
    public Job(int id, String title, String desc, String qual, String company, String location, LatLng latLng){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.qual = qual;
        this.company = company;
        this.city = location;
        this.latLng = latLng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDateListed() {
        return dateListed;
    }

    public String getQual() {
        return qual;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }

    public Double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public int getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(int salaryMin) {
        this.salaryMin = salaryMin;
    }

    public int getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(int salaryMax) {
        this.salaryMax = salaryMax;
    }


    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getCloseDateAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String selectedDate = sdf.format(closeDate);
        return selectedDate;
    }
    public String getListedDateAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String selectedDate = sdf.format(dateListed);
        return selectedDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
