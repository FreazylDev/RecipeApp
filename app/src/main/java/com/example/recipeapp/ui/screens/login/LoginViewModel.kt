package com.example.recipeapp.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.logic.ErrorParser
import com.example.recipeapp.models.login.LoginRequest
import com.example.recipeapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginFormState(
    val username: String = "",
    val usernameError: String = "",
    val email: String = "",
    val emailError: String = "",
    val unknownError: String = ""
)

data class LoginUiState(
    val userOptions: List<String> = emptyList(),
    val isSuccessful: Boolean = false,
    val form: LoginFormState = LoginFormState()
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState


    fun setUserOptions(users: List<String>) {
        if (_uiState.value.userOptions.isEmpty()) {
            _uiState.value = _uiState.value.copy(userOptions = users)
        }
    }

    fun onUserChange(user: String) {
        _uiState.value = _uiState.value.copy(
            form = _uiState.value.form.copy(username = user)
        )
    }

    fun onEmailChange(it: String) {
        _uiState.value = _uiState.value.copy(
            form = _uiState.value.form.copy(email = it)
        )
    }

    private fun checkErr(err: String) {
        if (err.contains("naam")) {
            _uiState.update { it.copy(form = it.form.copy(usernameError = err)) }
        } else if (err.contains("email")) {
            _uiState.update { it.copy(form = it.form.copy(emailError = err)) }
        } else {
            _uiState.update { it.copy(form = it.form.copy(unknownError = err)) }
        }
    }

    fun clearErrors() {
        _uiState.update { it.copy(form = it.form.copy(
            usernameError = "",
            emailError = ""
        )) }
    }

    fun onSubmit() {
        clearErrors()
        val credentials = LoginRequest(
            username = _uiState.value.form.username,
            email = _uiState.value.form.email
        )

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.postLoginUser(credentials)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        isSuccessful = true,
                    )
                    Log.d("DEBUG123", "$response")
                    
                } else {
                    val errors = ErrorParser.parse(response.errorBody()?.string() ?: "")
                    checkErr(errors[0])
                    Log.d("DEBUG123", "Error: ${errors[0]}")

                }
            } catch (e: Exception) {
                Log.d("DEBUG123", "Catch err: $e")
            }
        }
    }
}