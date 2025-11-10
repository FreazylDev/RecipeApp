package com.example.recipeapp.ui.screens.login.components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SubmitBtnForm(
    onSubmit: () -> Unit
) {
    TextButton(
        onClick = { onSubmit },
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFFEEE91)),
    ) {
        Text(
            text = "Ga verder",
            fontSize = 20.sp,
            color = Color.DarkGray
        )
    }
}