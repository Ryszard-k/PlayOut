package com.example.clientapp.Football;

import com.example.clientapp.Football.Model.Comment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CommentAPI {

    @POST("/comments")
    @Headers("Content-Type: application/json")
    Call<Void> addComment(@Body Comment comment);
}
