package com.Kevin.proyecto_gym.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.Kevin.proyecto_gym.R
import com.Kevin.proyecto_gym.ui.SociosViewModel

@Composable
fun RegistroSocioScreen(
    onGuardar: (String, String, String, String) -> Unit,
    onCancelar: () -> Unit,
    viewModel: SociosViewModel = hiltViewModel()
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var fecha by rememberSaveable { mutableStateOf("") }
    var errorRes by remember { mutableIntStateOf(0) }
    var guardando by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(20.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = stringResource(R.string.registro_titulo),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.registro_subtitulo),
            color = MaterialTheme.colorScheme.onBackground
        )
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(stringResource(R.string.campo_nombre_completo)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text(stringResource(R.string.campo_telefono)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text(stringResource(R.string.campo_correo)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text(stringResource(R.string.campo_fecha_alta)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (errorRes != 0) {
            Text(text = stringResource(errorRes), color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                when {
                    nombre.isBlank() || telefono.isBlank() || correo.isBlank() || fecha.isBlank() ->
                        errorRes = R.string.error_campos_obligatorios
                    !correo.contains("@") -> errorRes = R.string.error_correo_invalido
                    else -> {
                        guardando = true
                        errorRes = 0
                        viewModel.agregarSocio(nombre, telefono, correo, fecha) { correcto ->
                            guardando = false
                            if (correcto) onGuardar(nombre, telefono, correo, fecha)
                            else errorRes = R.string.error_guardar_socio
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !guardando
        ) {
            if (guardando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(stringResource(R.string.btn_guardar_socio))
            }
        }
        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth(),
            enabled = !guardando
        ) {
            Text(stringResource(R.string.btn_cancelar))
        }
    }
}
