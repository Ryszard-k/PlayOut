package com.example.clientapp.Volleyball;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface VolleyballAPI {

    @POST("/volleyball")
    @Headers("Content-Type: application/json")
    Call<Void> addEvent(@Body Volleyball volleyball);
}
