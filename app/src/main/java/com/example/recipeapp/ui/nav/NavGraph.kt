package com.example.recipeapp.ui.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.recipeapp.ui.screens.disconnected.Disconnected
import com.example.recipeapp.ui.screens.home.HomeScreen
import com.example.recipeapp.ui.screens.login.LoginScreen
import com.example.recipeapp.ui.screens.noUserPicksErr.NoUserPicksErrScreen
import com.example.recipeapp.ui.screens.splash.SplashScreen
import com.example.recipeapp.ui.screens.splash.SplashViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val splashViewModel: SplashViewModel = viewModel()
    NavHost(navController, startDestination = Routes.SPLASH_SCREEN) {
        composable(Routes.SPLASH_SCREEN) {
            SplashScreen(
                viewModel = splashViewModel,
                navController
            )
        }
        composable(Routes.HOME) {
            HomeScreen(recipes = splashViewModel.uiState.value.recipes)
        }
        composable(Routes.LOGIN) {
            LoginScreen(userOptions = splashViewModel.uiState.value.unactivatedUsers)
        }
        composable(Routes.NO_USER_PICKS_ERR) {
            NoUserPicksErrScreen()
        }
        composable(Routes.DISCONNECTED) {
            Disconnected()
        }
    }
}