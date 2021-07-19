package com.example.testapp.data.api

import com.example.testapp.data.model.UserResponse
import com.example.testapp.data.model.SmsCodeResponse
import com.example.testapp.data.model.TokenResponse
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("api/authenticateClients")
    suspend fun getToken(
        @Field("phone_number") phone_number: String,
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