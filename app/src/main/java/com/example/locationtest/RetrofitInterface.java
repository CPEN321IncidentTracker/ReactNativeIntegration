package com.example.locationtest;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/incident")
    Call<Void> executeIncident(@Body HashMap<String, String> map);

    @GET("/incident")
    Call<List<Incident>> getIncidents();

}
