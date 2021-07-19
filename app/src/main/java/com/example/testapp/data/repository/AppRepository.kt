package com.example.testapp.data.repository

import com.example.testapp.data.model.SmsCodeResponse
import com.example.testapp.data.model.TokenResponse
import com.example.testapp.data.model.UserResponse
import com.example.testapp.util.Resource

interface AppRepository {

    suspend fun getSmsCode(phoneNumber: String): Resource<SmsCodeResponse>

    suspend fun getToken(phone_number: String, password: String): Resource<TokenResponse>

    suspend fun getUser(token: String): Resource<UserResponse>

    suspend fun saveUserPhone(phone_number: String) {}

    suspend fun loadUserPhone(): String?

    suspend fun saveUserToken(token: String)

    suspend fun loadUserToken(): String?
}