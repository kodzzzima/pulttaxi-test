package com.example.testapp.data.api

import com.example.testapp.data.model.UserResponse
import com.example.testapp.data.model.SmsCodeResponse
import com.example.testapp.data.model.TokenResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/authenticateClients")
    suspend fun getToken(
        @Field("phone_number") email: String,
        @Field("password") password: String,
    ): TokenResponse

    @GET("api/requestSMSCodeClient")
    suspend fun getSmsCode(
        @Query("phone_number") phone_number: String,
    ): SmsCodeResponse

    @GET("api/client/getInfo")
    suspend fun getUser(
        @Query("token") token: String,
    ): UserResponse
}