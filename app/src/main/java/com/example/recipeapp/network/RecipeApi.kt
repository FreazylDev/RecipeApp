package com.example.recipeapp.network

import com.example.recipeapp.models.MainResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RecipeApi {
    @GET("api/android/main")
    suspend fun getRecipesResponse(
        @Header("Authorization") token: String
    ): Response<MainResponse>
}
