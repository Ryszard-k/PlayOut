package com.example.clientapp.Basketball;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BasketballAPI {

    @POST("/basketball")
    @Headers("Content-Type: application/json")
    Call<Void> addEvent(@Body Basketball basketball);
}
