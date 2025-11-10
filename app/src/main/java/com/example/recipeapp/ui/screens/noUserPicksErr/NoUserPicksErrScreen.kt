package com.example.recipeapp.ui.screens.noUserPicksErr

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NoUserPicksErrScreen () {
    Scaffold { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Text("No user left to log in from")
        }
    }
}