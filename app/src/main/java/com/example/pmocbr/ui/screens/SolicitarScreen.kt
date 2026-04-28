package com.example.pmocbr.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pmocbr.ui.screens.SolicitarUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitarScreen(
    viewModel: SolicitarViewModel,
    onBack: () -> Unit
) {
    val uiState = viewModel.uiState
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Solicitar ART") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (uiState is SolicitarUiState.Success) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Enviado com Sucesso!") },
                text = {
                    Column {
                        Text("Sua solicitação de ART foi registrada.")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("ID: ${uiState.id}", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                        Text("Data: ${uiState.dataHora}")
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.resetState()
                        onBack()
                    }) {
                        Text("OK")
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = viewModel.nomeResponsavel,
                onValueChange = { viewModel.nomeResponsavel = it },
                label = { Text("Nome do Responsável *") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = viewModel.cpf,
                    onValueChange = { viewModel.cpf = it },
                    label = { Text("CPF *") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = viewModel.telefone,
                    onValueChange = { viewModel.telefone = it },
                    label = { Text("Telefone (DDD) *") },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = viewModel.razaoSocial,
                onValueChange = { viewModel.razaoSocial = it },
                label = { Text("Razão Social *") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.cnpj,
                onValueChange = { viewModel.cnpj = it },
                label = { Text("CNPJ *") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.cep,
                onValueChange = { viewModel.cep = it },
                label = { Text("CEP *") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.enderecoCompleto,
                onValueChange = { viewModel.enderecoCompleto = it },
                label = { Text("Endereço Completo *") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = viewModel.dataInicio,
                    onValueChange = { viewModel.dataInicio = it },
                    label = { Text("Data Início *") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = viewModel.dataTermino,
                    onValueChange = { viewModel.dataTermino = it },
                    label = { Text("Data Término *") },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = viewModel.tipoServico,
                onValueChange = { viewModel.tipoServico = it },
                label = { Text("Tipo de Serviço *") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.observacoes,
                onValueChange = { viewModel.observacoes = it },
                label = { Text("Observações") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Text("Itens Adicionais", style = MaterialTheme.typography.titleSmall)
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = viewModel.checkItem1, onCheckedChange = { viewModel.checkItem1 = it })
                Text("Serviço em Altura")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = viewModel.checkItem2, onCheckedChange = { viewModel.checkItem2 = it })
                Text("Uso de Equipamentos Especiais")
            }

            if (uiState is SolicitarUiState.Error) {
                Text(text = uiState.message, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = { viewModel.enviarSolicitacao() },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                enabled = uiState !is SolicitarUiState.Loading && uiState !is SolicitarUiState.Success
            ) {
                if (uiState is SolicitarUiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Enviar Solicitação")
                }
            }
        }
    }
}
