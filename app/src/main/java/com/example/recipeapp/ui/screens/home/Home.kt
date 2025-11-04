package com.example.recipeapp.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val recipes = viewModel.recipes.value
    val resMsg = viewModel.resMsg.value

    Scaffold { padding ->
        Box(modifier = Modifier.padding(padding).padding(16.dp)) {
            if (!resMsg.isNullOrBlank()) {
                Text(resMsg)
            } else {
                Column {
                    Text("Recipes:")
                    recipes.forEach { recipe ->
                        Card(
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Text(recipe.body.title)
                        }
                    }
                }
            }
        }
    }
}