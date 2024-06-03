package com.juanjosetrujillocardozo.timetonicbookapp.network;

import com.juanjosetrujillocardozo.timetonicbookapp.model.Book;
import com.juanjosetrujillocardozo.timetonicbookapp.model.LoginRequest;
import com.juanjosetrujillocardozo.timetonicbookapp.model.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TimetonicApiService {
    @POST("/createAppkey")
    Call<LoginResponse> createAppKey(@Body LoginRequest loginRequest);

    @POST("/createOauthkey")
    Call<LoginResponse> createOauthKey(@Body LoginRequest loginRequest);

    @POST("/createSesskey")
    Call<LoginResponse> createSessKey(@Body LoginRequest loginRequest);

    @GET("/getAllBooks")
    Call<List<Book>> getBooks(@Header("Authorization") String authHeader);

}
