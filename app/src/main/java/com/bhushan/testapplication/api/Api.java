package com.bhushan.testapplication.api;


import com.bhushan.testapplication.pojo.DefaultResponse;
import com.bhushan.testapplication.pojo.FlightListRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    //to login
    @FormUrlEncoded
    @POST("login.php")
    Call<DefaultResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("ft_getFlightList.php")
    Call<FlightListRes> flightList();
}
