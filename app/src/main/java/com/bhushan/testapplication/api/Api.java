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

    @FormUrlEncoded
    @POST("ft_changeUsername.php")
    Call<DefaultResponse> ft_changeUsername(
            @Field("uid") String uid,
            @Field("newusername") String newusername
    );

    @FormUrlEncoded
    @POST("ft_updatePass.php")
    Call<DefaultResponse> ft_updatePass(
            @Field("uid") String uid,
            @Field("pass") String pass,
            @Field("oldpass") String oldpass
    );

    @POST("ft_getFlightList.php")
    Call<FlightListRes> flightList();
}
