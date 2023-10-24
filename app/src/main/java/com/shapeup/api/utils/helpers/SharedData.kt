package com.shapeup.api.utils.helpers

import android.content.SharedPreferences

const val SHARED_PREFIX = "shapeup"

class SharedData(
    private val sharedData: SharedPreferences
) {
    fun get(key: String, defaultValue: String? = null): String? {
        return sharedData.getString("$SHARED_PREFIX-$key", defaultValue)
    }

    fun has(key: String): Boolean {
        return sharedData.contains("$SHARED_PREFIX-$key")
    }

    fun save(key: String, value: String) {
        sharedData.edit().putString("$SHARED_PREFIX-$key", value).apply()
    }

    fun delete(key: String) {
        sharedData.edit().remove("$SHARED_PREFIX-$key").apply()
    }
}