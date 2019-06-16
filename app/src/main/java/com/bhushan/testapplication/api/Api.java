package com.bhushan.testapplication.api;


import com.bhushan.testapplication.pojo.DefaultResponse;
import com.bhushan.testapplication.pojo.FlightListRes;
import com.bhushan.testapplication.pojo.HistoryResponse;
import com.bhushan.testapplication.pojo.SummaryResponse;

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

    @FormUrlEncoded
    @POST("ft_addEntryInDB.php")
    Call<DefaultResponse> ft_addEntryInDB(
            @Field("uid") String uid,
            @Field("fid") String fid,
            @Field("jsonstr") String jsonstr
    );

    @FormUrlEncoded
    @POST("ft_history.php")
    Call<HistoryResponse> ft_history(
            @Field("uid") String uid
    );

    @FormUrlEncoded
    @POST("getTicketSummary.php")
    Call<SummaryResponse> getTicketSummary(
            @Field("uid") String uid,
            @Field("tcode") String tcode
    );

    @POST("ft_getFlightList.php")
    Call<FlightListRes> flightList();
}
