package com.example.pmocbr.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pmocbr.auth.AuthViewModel

@Composable
fun HomeScreen(viewModel: AuthViewModel, onLogout: () -> Unit) {
    val user = viewModel.currentUser

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Olá, ${user?.displayName ?: "Usuário"}!")
        Text(text = user?.email ?: "")
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { 
            viewModel.signOut()
            onLogout()
        }) {
            Text(text = "Sair")
        }
    }
}
