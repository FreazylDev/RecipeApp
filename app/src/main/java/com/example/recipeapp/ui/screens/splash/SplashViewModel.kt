package com.example.recipeapp.ui.screens.splash

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.logic.ErrorParser
import com.example.recipeapp.models.Recipe
import com.example.recipeapp.network.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SplashUiState(
    val isLoading: Boolean = true,
    val recipes: List<Recipe> = emptyList(),
    val unactivatedUsers: List<String> = emptyList(),
    val errorMsg: String? = null
)

class SplashViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> get() = _uiState


    fun loadData() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMainRecipesResponse(
                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                )
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        recipes = response.body()?.recipes.orEmpty(),
                        errorMsg = null
                    )
                } else {
                    val errors = ErrorParser.parse(response.errorBody()?.string() ?: "")
                    Log.d("DEBUG123", "$errors")
                    val unactivatedUsers = if (
                        errors.isNotEmpty() &&
                        errors[0].contains("The endpoint") &&
                        errors[0].contains("is offline")
                    ) {
                        listOf("Offline:", "User1", "User2", "User3")
                    } else {
                        errors
                    }

                    _uiState.value = _uiState.value.copy(
                        unactivatedUsers = unactivatedUsers,
                        errorMsg = "unauthorized"
                    )
                    Log.d("DEBUG123", "Unauthorized")
                }

            } catch (e: Exception) {
                Log.d("DEBUG123", "$e")
            } finally {
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            }
        }
    }
}