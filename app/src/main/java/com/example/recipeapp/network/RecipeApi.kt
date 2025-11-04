package com.example.recipeapp.network

import com.example.recipeapp.models.MainResponse
import retrofit2.http.GET

interface RecipeApi {
    @GET("api/android/main")
    suspend fun getRecipes(): MainResponse
}
