package com.example.clientapp.volleyball;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VolleyballAPI {

    @POST("/volleyball")
    @Headers("Content-Type: application/json")
    Call<Void> addEvent(@Body Volleyball volleyball);

    @PUT("/volleyball/join/{eventId}/{username}")
    @Headers("Content-Type: application/json")
    Call<Void> joinToEvent(@Path("eventId") Long eventId, @Path("username") String username);
}
