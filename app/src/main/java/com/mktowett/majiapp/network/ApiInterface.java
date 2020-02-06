package com.mktowett.majiapp.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<String> loginApp(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("meters.php")
    Call<String> recordMeter(@Field("u_id")String clientID,
                             @Field("c_id")String clerkID,
                             @Field("reading")String meterReading,
                             @Field("adminId")String adminId);
}
