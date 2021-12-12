package com.example.clientapp.Football;

import com.example.clientapp.Football.Model.Comment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentAPI {

    @POST("/comments")
    @Headers("Content-Type: application/json")
    Call<Long> addComment(@Body Comment comment);

    @DELETE("/comments/{commentId}")
    @Headers("Content-Type: application/json")
    Call<Void> deleteComment(@Path("commentId") Long commentId);
}
