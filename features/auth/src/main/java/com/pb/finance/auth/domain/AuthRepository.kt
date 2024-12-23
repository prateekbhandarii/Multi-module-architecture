package com.pb.finance.auth.domain

import android.content.SharedPreferences
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val preferences: SharedPreferences
) {
    fun saveAuthToken(token: String) {
        preferences.edit().putString("auth_token", token).apply()
    }

    fun getAuthToken(): String? = preferences.getString("auth_token", null)
}
