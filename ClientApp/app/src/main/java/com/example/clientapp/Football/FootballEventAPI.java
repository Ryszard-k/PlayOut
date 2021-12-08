package com.example.clientapp.Football;

import com.example.clientapp.Football.Model.FootballEvent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FootballEventAPI {

    String BASE_URL = "http://10.0.2.2:8080";

    @POST("/footballEvent")
    @Headers("Content-Type: application/json")
    Call<Void> addEvent(@Body FootballEvent footballEvent);

    @PUT("/footballEvent/join/{eventId}/{username}")
    @Headers("Content-Type: application/json")
    Call<Void> joinToEvent(@Path("eventId") Long eventId, @Path("username") String username);

    @DELETE("/footballEvent/{id}")
    Call<Void> deleteEvent(@Path("id") Long id);

}
