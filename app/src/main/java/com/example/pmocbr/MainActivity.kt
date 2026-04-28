package com.example.pmocbr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.pmocbr.auth.AuthManager
import com.example.pmocbr.auth.AuthViewModel
import com.example.pmocbr.data.repository.SolicitacaoRepository
import com.example.pmocbr.ui.navigation.NavGraph
import com.example.pmocbr.ui.theme.PMOCBRTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val authManager = AuthManager(this)
        val authViewModel = AuthViewModel(authManager)
        val solicitacaoRepository = SolicitacaoRepository()

        enableEdgeToEdge()
        setContent {
            PMOCBRTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(authViewModel, authManager, solicitacaoRepository)
                }
            }
        }
    }
}
