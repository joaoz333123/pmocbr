package com.example.pmocbr.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmocbr.auth.AuthManager
import com.example.pmocbr.data.model.Solicitacao
import com.example.pmocbr.data.repository.SolicitacaoRepository
import kotlinx.coroutines.launch

class SolicitacoesViewModel(
    private val repository: SolicitacaoRepository,
    private val authManager: AuthManager
) : ViewModel() {

    var uiState by mutableStateOf<SolicitacoesUiState>(SolicitacoesUiState.Loading)
        private set

    init {
        carregarSolicitacoes()
    }

    fun carregarSolicitacoes() {
        val user = authManager.currentUser
        if (user == null) {
            uiState = SolicitacoesUiState.Error("Usuário não autenticado")
            return
        }

        uiState = SolicitacoesUiState.Loading
        viewModelScope.launch {
            repository.buscarSolicitacoesPorUsuario(user.uid)
                .onSuccess { lista ->
                    uiState = if (lista.isEmpty()) {
                        SolicitacoesUiState.Empty
                    } else {
                        SolicitacoesUiState.Success(lista)
                    }
                }
                .onFailure { error ->
                    uiState = SolicitacoesUiState.Error(error.message ?: "Erro ao carregar dados")
                }
        }
    }
}

sealed class SolicitacoesUiState {
    object Loading : SolicitacoesUiState()
    object Empty : SolicitacoesUiState()
    data class Success(val solicitacoes: List<Solicitacao>) : SolicitacoesUiState()
    data class Error(val message: String) : SolicitacoesUiState()
}
