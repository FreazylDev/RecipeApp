package com.example.recipeapp.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.R
import com.example.recipeapp.ui.screens.login.components.FormPhoneNumber
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.shadow
import com.example.recipeapp.ui.screens.login.components.UsernameForm
import com.example.recipeapp.ui.screens.login.components.form.SubmitBtnForm


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    userOptions: List<String>
) {
    LaunchedEffect(userOptions) {
        loginViewModel.setUserOptions(userOptions)
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Background()
        Contents(loginViewModel)
    }
}

@Composable
fun Background() {
    Image(
        painter = painterResource(R.drawable.fries),
        contentDescription = "Fries background",
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun Contents(loginViewModel: LoginViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth(.8f)
            .widthIn(max = 400.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(10.dp),
                    clip = true
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(35.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greet()
            Spacer(modifier = Modifier.height(32.dp))

            UsernameForm(
                userOptions = loginViewModel.userOptions.collectAsState().value,
                onUserChange = loginViewModel::onUserChange,
                selectedUser = loginViewModel.username.collectAsState().value
            )

            Spacer(modifier = Modifier.height(30.dp))

            FormPhoneNumber(
                phoneNumber = loginViewModel.phoneNumber.collectAsState().value,
                onPhoneNumberChange = loginViewModel::onPhoneNumberChange
            )

            Spacer(modifier = Modifier.height(55.dp))

            SubmitBtnForm() {

            }
        }
    }
}

@Composable
fun Greet() {
    Text(
        text = "Log in",
        fontSize = 50.sp,
        modifier = Modifier
            .padding(10.dp)
    )
}