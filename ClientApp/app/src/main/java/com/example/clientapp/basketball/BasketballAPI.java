package com.example.clientapp.basketball;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BasketballAPI {

    @POST("/basketball")
    @Headers("Content-Type: application/json")
    Call<Void> addEvent(@Body Basketball basketball);

    @PUT("/basketball/join/{eventId}/{username}")
    @Headers("Content-Type: application/json")
    Call<Void> joinToEvent(@Path("eventId") Long eventId, @Path("username") String username);
}
