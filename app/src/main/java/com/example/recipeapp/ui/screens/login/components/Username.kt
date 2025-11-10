package com.example.recipeapp.ui.screens.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.R
import kotlin.text.ifEmpty

private val fontSize = 20.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameForm(
    userOptions: List<String>,
    usernameErr: String,
    onUserChange: (String) -> Unit,
    selectedUser: String
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Naam", fontSize = fontSize)
        Spacer(modifier = Modifier.height(7.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {

            Display(selectedUser, pExpanded = expanded) {
                expanded = true
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                ListAllOptions(
                    userOptions,
                    onUserChange = onUserChange,
                    onClose = { expanded = false }
                )
            }
        }
        if (usernameErr.isNotEmpty()) {
            Text(
                text = usernameErr,
                fontSize = 16.sp,
                color = Color.Red
            )
        }
    }
}



@Composable
fun Display(
    selectedUser: String,
    pExpanded: Boolean,
    onOpen: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF9F8F6),
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
            .clickable(
                onClick = { onOpen() }
            )
    ) {
        Text(selectedUser.ifEmpty { "Selecteer je naam" })
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(
                if (!pExpanded) R.drawable.dropdown
                else R.drawable.dropup
            ),
            contentDescription = "Drop"
        )
    }
}

@Composable
fun ListAllOptions(
    userOptions: List<String>,
    onUserChange: (String) -> Unit,
    onClose: () -> Unit
) {
    userOptions.forEach { user ->
        DropdownMenuItem(
            text = { Text(user) },
            onClick = {
                onUserChange(user)
                onClose()
            }
        )
    }
}