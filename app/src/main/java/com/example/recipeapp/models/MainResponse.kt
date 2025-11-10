package com.example.recipeapp.models

data class MainResponse (
    val recipes: List<Recipe>,
    val appVersion: String
)