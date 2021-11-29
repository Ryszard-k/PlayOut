package com.example.clientapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface EventAPI {

    @GET("/events/active/all")
    @Headers("Content-Type: application/json")
    Call<EventsWrapper> findAllActiveEvent();
}
