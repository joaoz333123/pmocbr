package com.example.pmocbr.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pmocbr.ui.components.SolicitacaoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcompanharScreen(
    viewModel: SolicitacoesViewModel,
    onBack: () -> Unit
) {
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Acompanhar Status") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when (uiState) {
                is SolicitacoesUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is SolicitacoesUiState.Empty -> {
                    Text(
                        text = "Você ainda não tem solicitações em andamento.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is SolicitacoesUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = uiState.message, color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.carregarSolicitacoes() }) {
                            Text("Tentar Novamente")
                        }
                    }
                }
                is SolicitacoesUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.solicitacoes) { solicitacao ->
                            SolicitacaoCard(solicitacao = solicitacao)
                        }
                    }
                }
            }
        }
    }
}
