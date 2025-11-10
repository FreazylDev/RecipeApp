package com.example.recipeapp.ui.screens.splash

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recipeapp.ui.nav.Routes
import com.example.recipeapp.ui.screens.disconnected.Disconnected
import com.example.recipeapp.ui.screens.home.HomeScreen
import com.example.recipeapp.ui.screens.login.LoginScreen

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = viewModel(),
    navController: NavController
) {
    val isLoading = viewModel.uiState.collectAsState().value.isLoading
    val errorMsg = viewModel.uiState.collectAsState().value.errorMsg

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    LaunchedEffect(!isLoading) {
        if (!isLoading) {
            when (errorMsg) {
                "unauthorized" -> navController.navigate(Routes.LOGIN) {
                    popUpTo(Routes.SPLASH_SCREEN) { inclusive = true }
                }
                "unauthorized + no users left" -> navController.navigate((Routes.NO_USER_PICKS_ERR)) {
                    popUpTo(Routes.SPLASH_SCREEN) { inclusive = true }
                }
                "404" -> navController.navigate(Routes.DISCONNECTED) {
                    Log.d("DEBUG", "Screen Disconnected")
                    popUpTo(Routes.SPLASH_SCREEN) { inclusive = true }
                }
                else -> navController.navigate(Routes.HOME) {
                    Log.d("DEBUG", "Screen Home")
                    popUpTo(Routes.SPLASH_SCREEN) { inclusive = true }
                }
            }
        }
    }
}
