package com.atech.domain.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

@Suppress("unused")
class SessionHelper constructor(context: Context, private val gson: Gson) {

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveString(key: String, string: String) =
        pref.edit().putString(key, string).apply()

    fun getString(key: String) = pref.getString(key, "")

    fun saveInt(key: String, int: Int) =
        pref.edit().putInt(key, int).apply()

    fun getInt(key: String) = pref.getInt(key, 0)

    fun saveFloat(key: String, int: Float) =
        pref.edit().putFloat(key, int).apply()

    fun getFloat(key: String) = pref.getFloat(key, 0F)

    fun saveBoolean(key: String, boolean: Boolean) =
        pref.edit().putBoolean(key, boolean).apply()

    fun getBoolean(key: String) = pref.getBoolean(key, false)

    fun remove(key: String) = pref.edit().remove(key).apply()

    fun <T> saveObject(key: String, any: T) {
        pref.edit().putString(key, gson.toJson(any)).apply()
    }

    fun <T> getObject(key: String, classType: Class<T>? = null): T? {
        return try {
            gson.fromJson(pref.getString(key, null), classType)
        } catch (exception: Exception) {
            null
        }
    }

    fun clear() = pref.edit().clear().apply()

    companion object {

        private const val PREF_NAME: String = "AtechApp"
        const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        const val LOGIN_RESPONSE = "LOGIN_RESPONSE"
        const val USER_PROFILE = "USER_PROFILE"
    }
}