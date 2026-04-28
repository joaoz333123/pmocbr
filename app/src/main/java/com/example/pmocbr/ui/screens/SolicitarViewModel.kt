package com.example.pmocbr.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmocbr.auth.AuthManager
import com.example.pmocbr.data.model.Solicitacao
import com.example.pmocbr.data.repository.SolicitacaoRepository
import com.example.pmocbr.util.IdGenerator
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SolicitarViewModel(
    private val repository: SolicitacaoRepository,
    private val authManager: AuthManager
) : ViewModel() {

    var uiState by mutableStateOf<SolicitarUiState>(SolicitarUiState.Idle)
        private set

    // Form fields
    var nomeResponsavel by mutableStateOf("")
    var cpf by mutableStateOf("")
    var telefone by mutableStateOf("")
    var razaoSocial by mutableStateOf("")
    var cnpj by mutableStateOf("")
    var cep by mutableStateOf("")
    var enderecoCompleto by mutableStateOf("")
    var dataInicio by mutableStateOf("")
    var dataTermino by mutableStateOf("")
    var tipoServico by mutableStateOf("")
    var observacoes by mutableStateOf("")
    
    // Checkboxes
    var checkItem1 by mutableStateOf(false)
    var checkItem2 by mutableStateOf(false)

    fun enviarSolicitacao() {
        if (!validarCampos()) {
            uiState = SolicitarUiState.Error("Preencha todos os campos obrigatórios")
            return
        }

        uiState = SolicitarUiState.Loading
        val user = authManager.currentUser
        val generatedId = IdGenerator.generateId()

        val formData = mapOf(
            "nomeResponsavel" to nomeResponsavel,
            "cpf" to cpf,
            "telefone" to telefone,
            "razaoSocial" to razaoSocial,
            "cnpj" to cnpj,
            "cep" to cep,
            "enderecoCompleto" to enderecoCompleto,
            "dataInicio" to dataInicio,
            "dataTermino" to dataTermino,
            "tipoServico" to tipoServico,
            "observacoes" to observacoes,
            "checkItem1" to checkItem1,
            "checkItem2" to checkItem2
        )

        val solicitacao = Solicitacao(
            id = generatedId,
            userId = user?.uid ?: "",
            userEmail = user?.email,
            formData = formData
        )

        viewModelScope.launch {
            repository.salvarSolicitacao(solicitacao)
                .onSuccess {
                    val dataHora = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
                    uiState = SolicitarUiState.Success(generatedId, dataHora)
                }
                .onFailure {
                    uiState = SolicitarUiState.Error("Erro ao salvar: ${it.message}")
                }
        }
    }

    private fun validarCampos(): Boolean {
        return nomeResponsavel.isNotBlank() && 
               cpf.isNotBlank() && 
               telefone.isNotBlank() &&
               razaoSocial.isNotBlank() &&
               cnpj.isNotBlank() &&
               cep.isNotBlank() &&
               enderecoCompleto.isNotBlank() &&
               dataInicio.isNotBlank() &&
               dataTermino.isNotBlank() &&
               tipoServico.isNotBlank()
    }

    fun resetState() {
        uiState = SolicitarUiState.Idle
    }
}

sealed class SolicitarUiState {
    object Idle : SolicitarUiState()
    object Loading : SolicitarUiState()
    data class Success(val id: String, val dataHora: String) : SolicitarUiState()
    data class Error(val message: String) : SolicitarUiState()
}
