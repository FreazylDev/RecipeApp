package com.example.recipeapp.ui.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.models.Recipe
import com.example.recipeapp.network.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _isDataLoaded = MutableStateFlow(false)
    val isDataLoaded: StateFlow<Boolean> = _isDataLoaded

    private val _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> = _errorMsg

    var unactivatedUsers: List<String> = emptyList()
    var recipes: List<Recipe> = emptyList()

    fun loadData() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRecipesResponse(
                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                )

                if (response.isSuccessful) {
                    recipes = response.body()?.recipes.orEmpty()
                    _errorMsg.value = ""
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errors: List<String> = if (!errorBody.isNullOrEmpty()) {
                        try {
                            // Try parsing as JSON array
                            Gson().fromJson(errorBody, object : TypeToken<List<String>>() {}.type)
                        } catch (e: Exception) {
                            // If fails, treat as single string
                            listOf(errorBody.trim('"'))
                        }
                    } else {
                        listOf("Unknown error")
                    }

                    unactivatedUsers = if (errors[0].contains("The endpoint") && errors[0].contains("is offline")) {
                        listOf("Test 1", "Test 2", "Test 3", "Test 4")
                    } else errors
                    Log.d("DEBUG123", "$unactivatedUsers")

                    if (unactivatedUsers.isEmpty()) {
                        _errorMsg.value = "unauthorized + no users left"
                    } else {
                        _errorMsg.value = "unauthorized"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMsg.value = if (e.toString().contains("Unable to resolve host")) "disconnected" else "unknown"
                Log.d("DEBUG", "Data exception: $e")
            } finally {
                _isDataLoaded.value = true
            }
        }
    }

}
