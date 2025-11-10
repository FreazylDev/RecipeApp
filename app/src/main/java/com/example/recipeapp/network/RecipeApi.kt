package com.example.recipeapp.network

import com.example.recipeapp.models.MainResponse
import com.example.recipeapp.models.login.LoginRequest
import com.example.recipeapp.models.login.LoginResponse
import okhttp3.Credentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RecipeApi {
    @GET("api/android/main")
    suspend fun getMainRecipesResponse(
        @Header("Authorization") token: String
    ): Response<MainResponse>

    @POST("api/auth/login")
    suspend fun postLoginUser(
        @Body credentials: LoginRequest
    ): Response<String>
}
