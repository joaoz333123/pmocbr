package com.example.pmocbr.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(private val authManager: AuthManager) : ViewModel() {

    private val _uiState = mutableStateOf<AuthState>(AuthState.Idle)
    val uiState: State<AuthState> = _uiState

    val currentUser get() = authManager.currentUser

    fun signIn() {
        _uiState.value = AuthState.Loading
        viewModelScope.launch {
            val result = authManager.signInWithGoogle()
            result.onSuccess {
                _uiState.value = AuthState.Success
            }.onFailure { error ->
                _uiState.value = AuthState.Error(error.message ?: "Falha na autenticação")
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthState.Idle
    }

    fun signOut() {
        viewModelScope.launch {
            authManager.signOut()
            _uiState.value = AuthState.Idle
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
