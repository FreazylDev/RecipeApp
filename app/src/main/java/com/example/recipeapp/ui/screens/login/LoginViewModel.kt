package com.example.recipeapp.ui.screens.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {
    private val _userOptions = MutableStateFlow<List<String>>(emptyList())
    val userOptions: StateFlow<List<String>> = _userOptions

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    fun setUserOptions(users: List<String>) {
        if (userOptions.value.isEmpty()) {
            _userOptions.value = users
        }
    }

    fun onUserChange(user: String) {
        _username.value = user
    }

    fun onPhoneNumberChange(it: String) {
        val isPhoneNumber = _phoneNumber.value.length < 8 && it.all { it.isDigit() } ||
                it.length < _phoneNumber.value.length
        if (isPhoneNumber) _phoneNumber.value = it
    }

    fun onSubmit() {}
}