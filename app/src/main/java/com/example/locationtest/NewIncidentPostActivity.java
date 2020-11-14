package com.example.locationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewIncidentPostActivity extends AppCompatActivity {

    private RetrofitInterface retrofitInterface;
    private static final String BASE_URL = "http://52.149.135.175:80";
    private LatLng location;
    private EditText incidentTitleEntry;
    private EditText incidentSeverityEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_incident_post);

        //Get lat and long from map
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Double latitude = extras.getDouble("latitude");
            Double longitude = extras.getDouble("longitude");

            location = new LatLng(latitude, longitude);
        }

        // Instantiate retrofit objects
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Instantiate text entries and buttons
        incidentTitleEntry = findViewById(R.id.incidentTitleEntry);
        incidentSeverityEntry = findViewById(R.id.incidentSeverityEntry);
        Button submitButton = findViewById(R.id.submitButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int incidentSeverity = Integer.parseInt(incidentSeverityEntry.getText().toString());
                String incidentTitle = incidentTitleEntry.getText().toString();

                // Hashmap to be sent to server
                HashMap<String, String> map = new HashMap<>();
                map.put("title", incidentTitle);
                map.put("severity", incidentSeverityEntry.getText().toString());
                map.put("latitude", Double.toString(location.latitude));
                map.put("longitude", Double.toString(location.longitude));

                // Send map to server
                Call<Void> call = retrofitInterface.executeIncident(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(NewIncidentPostActivity.this, "Success", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(NewIncidentPostActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

                // Return to map activity and display new incident
                Intent mapIntent = new Intent(NewIncidentPostActivity.this, MapsActivity.class);
                mapIntent.putExtra("severity", incidentSeverity);
                mapIntent.putExtra("title", incidentTitle);
                mapIntent.putExtra("latitude", location.latitude);
                mapIntent.putExtra("longitude", location.longitude);
                startActivity(mapIntent);
            }
        });

        // Cancels submission
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent2 = new Intent(NewIncidentPostActivity.this, MapsActivity.class);
                startActivity(mapIntent2);
            }
        });






    }
}