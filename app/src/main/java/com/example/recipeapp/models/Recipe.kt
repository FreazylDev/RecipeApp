package com.example.recipeapp.models

import java.util.Date

data class Recipe (
    val id: String,
    val author: String,
    val body: Body,
    val createdAt: Date,
    val updatedAt: Date,
    val __v: Int
)

data class Body (
    val title: String,
    val frontImage: String,
    val ingredients: List<String>,
    val content: List<ContentItem>,
    val tags: List<String>
)

data class ContentItem (
    val type: String,
    val content: String,
    val id: String
)