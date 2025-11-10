package com.example.recipeapp.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import com.example.recipeapp.models.Recipe


@Composable
fun HomeScreen(recipes: List<Recipe>) {
    LazyColumn {
        items(recipes) { recipe ->
            Text(recipe.body.title)
        }
    }
    Text("Hellooo")
}