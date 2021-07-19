package com.example.testapp.util

import android.content.Context
import android.content.SharedPreferences


object UserPreferences {

    private const val NAME_PREF = "preference"
    private const val PHONE_NUMBER = "phoneNumber"
    private const val USER_TOKEN = "userToken"

    private lateinit var preferences: SharedPreferences

    fun getPreference(context: Context): SharedPreferences {
        preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
        return preferences
    }

    fun getUserPhone(): String? {
        return preferences.getString(PHONE_NUMBER, "")
    }

    fun setUserPhone(phoneNumber: String) {
        preferences.edit()
            .putString(PHONE_NUMBER, phoneNumber)
            .apply()
    }

    fun getUserToken(): String? {
        return preferences.getString(USER_TOKEN, "")
    }

    fun setUserToken(token: String) {
        preferences.edit()
            .putString(USER_TOKEN, token)
            .apply()
    }
}