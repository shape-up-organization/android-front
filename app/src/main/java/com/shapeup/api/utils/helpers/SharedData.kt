package com.shapeup.api.utils.helpers

import android.content.SharedPreferences

class SharedData(
    private val sharedData: SharedPreferences
) {
    fun get(key: String, defaultValue: String? = null): String? {
        return sharedData.getString(key, defaultValue)
    }

    fun has(key: String): Boolean {
        return sharedData.contains(key)
    }

    fun save(key: String, value: String) {
        sharedData.edit().putString(key, value).apply()
    }

    fun delete(key: String) {
        sharedData.edit().remove(key).apply()
    }
}