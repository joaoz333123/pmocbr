package com.example.pmocbr.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pmocbr.data.model.Solicitacao
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SolicitacaoCard(
    solicitacao: Solicitacao,
    showDetailedStatus: Boolean = false
) {
    val date = solicitacao.createdAt?.toDate()
    val formattedDate = if (date != null) {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(date)
    } else {
        "Data indisponível"
    }

    val tipoServico = solicitacao.formData["tipoServico"]?.toString() ?: "N/A"
    val empresa = solicitacao.formData["razaoSocial"]?.toString() ?: "N/A"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = solicitacao.id,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                StatusBadge(status = solicitacao.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(text = "Serviço: $tipoServico", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Empresa: $empresa", style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Enviado em: $formattedDate",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val containerColor = when (status) {
        "Recebida" -> MaterialTheme.colorScheme.secondaryContainer
        "Em análise" -> MaterialTheme.colorScheme.tertiaryContainer
        "Concluída" -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Surface(
        color = containerColor,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}
