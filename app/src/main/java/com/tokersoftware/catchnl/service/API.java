package com.tokersoftware.catchnl.service;

import com.tokersoftware.catchnl.model.response.Complaint;
import com.tokersoftware.catchnl.model.response.ErrorMessage;
import com.tokersoftware.catchnl.model.response.Login;
import com.tokersoftware.catchnl.model.response.Register;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    String BASE_URL = "https://catchnl.tokersoftware.nl";

    @FormUrlEncoded
    @POST("/api/register.php")
    Call<Register> sendRegisterRequest(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("licencePlate") String licencePlate,
            @Field("licenceFile") String image
    );

    @FormUrlEncoded
    @POST("/api/login.php")
    Call<Login> sendLoginRequest(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/addComplaint.php")
    Call<ErrorMessage> sendComplaint(
            @Field("licencePlate") String licencePlate,
            @Field("incident") String incident,
            @Field("userID") String userID,
            @Field("place") String place,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("/api/updateUser.php")
    Call<ErrorMessage> updateUser(
        @Field("email") String email,
        @Field("name") String name,
        @Field("licencePlate") String licencePlate,
        @Field("userID") String userID
    );

    @GET("/api/getComplaints.php")
    Call<List<Complaint>> getComplaints();

    @POST("/api/resetPassword.php")
    @FormUrlEncoded
    Call<ErrorMessage> resetPassword(
            @Field("email") String email
    );
}
