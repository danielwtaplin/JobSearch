package com.activities.dwtaplin.jobsearchfinal.tools;

import com.google.android.gms.maps.model.LatLng;

public class Toolkit {

    public static double calculateDistanceKM(LatLng loc1, LatLng loc2){
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(loc2.latitude - loc1.latitude);  // deg2rad below
        double dLon = deg2rad(loc2.longitude-loc1.longitude);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(loc1.latitude)) * Math.cos(deg2rad(loc2.latitude)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return d;
    }

    private static double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }
}
