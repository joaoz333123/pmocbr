package com.example.pmocbr.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pmocbr.auth.AuthViewModel
import com.example.pmocbr.ui.screens.*

@Composable
fun NavGraph(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val startDestination = if (authViewModel.currentUser != null) "home" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(
                viewModel = authViewModel,
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable("solicitar") {
            SolicitarScreen(onBack = { navController.popBackStack() })
        }
        composable("acompanhar") {
            AcompanharScreen(onBack = { navController.popBackStack() })
        }
        composable("historico") {
            HistoricoScreen(onBack = { navController.popBackStack() })
        }
        composable("configuracoes") {
            ConfiguracoesScreen(
                viewModel = authViewModel,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
