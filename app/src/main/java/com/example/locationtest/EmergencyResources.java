package com.example.locationtest;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class EmergencyResources extends AppCompatActivity {
    //private final static String TAG = "EmergencyResourcesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_resources);

        TextView vpdHomeLink = (TextView) findViewById(R.id.VPD_homepage);
        vpdHomeLink.setMovementMethod(LinkMovementMethod.getInstance());

        TextView vpdReportLink = (TextView) findViewById(R.id.VPD_crime_report);
        vpdReportLink.setMovementMethod(LinkMovementMethod.getInstance());

        TextView CrimeStopLink= (TextView) findViewById(R.id.crime_stoppers_website);
        CrimeStopLink.setMovementMethod(LinkMovementMethod.getInstance());

    }
}