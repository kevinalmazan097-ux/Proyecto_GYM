package com.Kevin.proyecto_gym.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.Kevin.proyecto_gym.R
import com.Kevin.proyecto_gym.ui.LoginError
import com.Kevin.proyecto_gym.ui.LoginViewModel

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var correo by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val rojo = MaterialTheme.colorScheme.primary
    val blanco = MaterialTheme.colorScheme.onBackground

    LaunchedEffect(state.loggedIn) {
        if (state.loggedIn) onLogin()
    }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).verticalScroll(rememberScrollState()).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = rojo,
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.login_bienvenido),
            color = blanco,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.login_subtitulo),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(35.dp))
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it; viewModel.limpiarError() },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.campo_correo)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it; viewModel.limpiarError() },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.campo_contrasena)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        val errorText = when (state.error) {
            LoginError.EMPTY_FIELDS -> stringResource(R.string.error_campos_obligatorios)
            LoginError.INVALID_CREDENTIALS -> stringResource(R.string.error_credenciales)
            LoginError.DATABASE_ERROR -> stringResource(R.string.error_consulta_bd)
            LoginError.NONE -> null
        }
        if (errorText != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = errorText, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = { viewModel.iniciarSesion(correo, password) },
            modifier = Modifier.fillMaxWidth().height(55.dp),
            enabled = !state.loading,
            shape = RoundedCornerShape(12.dp)
        ) {
            if (state.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = stringResource(R.string.btn_iniciar_sesion),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.login_pie),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )
    }
}
