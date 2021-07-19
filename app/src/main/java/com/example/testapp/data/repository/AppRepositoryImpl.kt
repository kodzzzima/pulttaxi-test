package com.example.testapp.data.repository

import com.example.testapp.data.api.ApiService
import com.example.testapp.data.api.SafeApiCall
import com.example.testapp.data.model.UserResponse
import com.example.testapp.util.Resource
import com.example.testapp.util.UserPreferences

class AppRepositoryImpl(
    private val apiService: ApiService,
) : SafeApiCall, AppRepository {

    override suspend fun getSmsCode(phoneNumber: String) =
        safeApiCall { apiService.getSmsCode(phoneNumber) }

    override suspend fun getToken(phone_number: String, password: String) =
        safeApiCall { apiService.getToken(phone_number, password) }

    override suspend fun getUser(token: String): Resource<UserResponse> =
        safeApiCall { apiService.getUser(token) }

    override suspend fun saveUserPhone(phone_number: String) {
        UserPreferences.setUserPhone(phone_number)
    }

    override suspend fun loadUserPhone(): String? {
        return UserPreferences.getUserPhone()
    }

    override suspend fun saveUserToken(token: String) {
        UserPreferences.setUserToken(token)
    }

    override suspend fun loadUserToken(): String? {
        return UserPreferences.getUserToken()
    }
}