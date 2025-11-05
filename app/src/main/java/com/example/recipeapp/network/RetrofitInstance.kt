package com.example.recipeapp.network

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val URL = "https://fd7dae96d3f2.ngrok-free.app/"
    private const val BEARER_TOKEN = "eyJhbGciiJIUzI1NiIsInR5IkpXVCJ9.eyJpZCI6IjY5MDNjZTdhODY2YzhhZjFmYzEyZGFjMCIsImlhdCI6MTc2MjI5MzU3MSwiZXhwIjo0MzU0MjkzNTcxfQ.AAPv_HKUY6YE974PUFwfWM6fhwyorfWnJ9fVRADGcTY" // To modify later

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $BEARER_TOKEN")
                .build()
            chain.proceed(request)
        }
        .build()

    val api: RecipeApi by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApi::class.java)
    }
}