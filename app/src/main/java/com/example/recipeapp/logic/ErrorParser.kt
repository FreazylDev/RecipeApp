package com.example.recipeapp.logic

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ErrorParser {
    fun parse(errorBody: String?): List<String> {
        if (errorBody.isNullOrBlank()) return listOf("Unknown error")
        return try {
            Gson().fromJson(errorBody, object : TypeToken<List<String>>() {}.type)
        } catch (_: Exception) {
            listOf(errorBody.trim('"'))
        }
    }
}