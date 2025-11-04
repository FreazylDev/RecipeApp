package com.example.recipeapp.ui.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.models.MainResponse
import com.example.recipeapp.models.Recipe
import com.example.recipeapp.network.RetrofitInstance
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _res = mutableStateOf<MainResponse?>(null)
    val res: State<MainResponse?> = _res

    private val _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes

    private val _resMsg = mutableStateOf<String?>(null)
    val resMsg: State<String?> = _resMsg

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            _resMsg.value = "Loading"
            try {
                val result = RetrofitInstance.api.getRecipes()
                _res.value = result
                _recipes.value = _res.value?.recipes.orEmpty()
                _resMsg.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("Debug", "Reached Error: $e")
                _resMsg.value = "Connection failed"
            }
        }
    }
}