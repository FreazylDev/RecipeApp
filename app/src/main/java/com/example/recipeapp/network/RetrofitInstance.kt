package com.example.recipeapp.network

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val URL = "https://c47d30637d1d.ngrok-free.app/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val token = "1234" // Get token later
            val request: Request = chain.request().newBuilder()
                .apply {
                    token?.let {
                        addHeader("Authorization", "Bearer $token")
                    }
                }
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