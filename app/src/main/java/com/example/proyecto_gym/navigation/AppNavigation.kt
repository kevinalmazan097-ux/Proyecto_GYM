package com.Kevin.proyecto_gym.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Kevin.proyecto_gym.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLogin = {
                navController.navigate("dashboard") { popUpTo("login") { inclusive = true } }
            })
        }
        composable("dashboard") {
            DashboardScreen(onModuloClick = { modulo -> navController.navigate(modulo) })
        }
        composable("socios") {
            SociosScreen(onRegistrarClick = { navController.navigate("registroSocio") }, onBack = { navController.popBackStack() })
        }
        composable("registroSocio") {
            RegistroSocioScreen(
                onGuardar = { _, _, _, _ -> navController.popBackStack() },
                onCancelar = { navController.popBackStack() }
            )
        }
        listOf("membresias", "pagos", "asistencia", "rutinas", "reportes").forEach { route ->
            composable(route) {
                ModuloScreen(modulo = route, onBack = { navController.popBackStack() })
            }
        }
    }
}
