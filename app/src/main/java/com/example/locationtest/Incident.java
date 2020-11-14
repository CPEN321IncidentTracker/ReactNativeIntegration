package com.example.locationtest;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

// Describes an incident with title, severity, and location and (latitude, longitude)
public class Incident {

    private String title;
    private double latitude;
    private double longitude;
    private int severity;

    public Incident(String title, double latitude, double longitude, int severity){
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.severity = severity;
    }

    public LatLng getLocation(){
        return new LatLng(latitude, longitude);
    }

    public String getTitle(){
        return title;
    }

    public int getSeverity(){
        return severity;
    }

    // Calculates great-circle distance (km) between two (lat, long) points using Haversine formula
    public double distanceFrom(LatLng someLocation){
        double p = Math.PI / 180;
        double lat1 = someLocation.latitude;
        double long1 = someLocation.longitude;
        double lat2 = latitude;
        double long2 = longitude;
        double a = 0.5 - Math.cos((lat2 - lat1)*p)/2 + Math.cos(lat1*p) * Math.cos(lat2*p) * (1-Math.cos((long2-long1)*p))/2;
        return 12742 * Math.asin(Math.sqrt(a));
    }

    public boolean sameLocality(LatLng someLocation, Geocoder coder) throws IOException {
        List<Address> incidentAddress = coder.getFromLocation(this.getLocation().latitude, this.getLocation().longitude, 1);
        String incidentLocality = incidentAddress.get(0).getLocality();
        List<Address> myAddress = coder.getFromLocation(someLocation.latitude, someLocation.longitude, 1);
        String myLocality = myAddress.get(0).getLocality();
        return(myLocality != null && myLocality.equals(incidentLocality));
    }
}
