package com.example.clientapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface EventAPI {

    @GET("/events/active/all")
    @Headers("Content-Type: application/json")
    Call<EventsWrapper> findAllActiveEvent();

    @GET("/events/activeEvent/{username}")
    @Headers("Content-Type: application/json")
    Call<EventsWrapper> getMyActiveEvent(@Path("username") String username);

    @GET("/events/historyEvent/{username}")
    @Headers("Content-Type: application/json")
    Call<EventsWrapper> getMyHistoryEvent(@Path("username") String username);
}
