package com.Kevin.proyecto_gym.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.Kevin.proyecto_gym.R
import com.Kevin.proyecto_gym.domain.model.Socio
import com.Kevin.proyecto_gym.ui.SociosViewModel

@Composable
fun SociosScreen(onRegistrarClick:()->Unit,onBack:()->Unit,viewModel:SociosViewModel= hiltViewModel()) {
    val socios by viewModel.socios.collectAsStateWithLifecycle()
    var busqueda by rememberSaveable { mutableStateOf("") }
    var socioEditar by remember { mutableStateOf<Socio?>(null) }
    val filtrados=socios.filter { it.nombre.contains(busqueda,true) || it.correo.contains(busqueda,true) || it.telefono.contains(busqueda,true) }
    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(18.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(10.dp)) {
            OutlinedButton(onClick=onBack) { Text(stringResource(R.string.btn_regresar)) }
            Column {
                Text(stringResource(R.string.socios_titulo),color=MaterialTheme.colorScheme.primary,fontSize=28.sp,fontWeight=FontWeight.ExtraBold)
                Text(stringResource(R.string.socios_subtitulo),color=MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(busqueda,{busqueda=it},modifier=Modifier.fillMaxWidth(),placeholder={Text(stringResource(R.string.socios_buscar_placeholder))},singleLine=true)
        Spacer(Modifier.height(10.dp))
        Button(onClick=onRegistrarClick,modifier=Modifier.fillMaxWidth()){Text(stringResource(R.string.btn_registrar_socio))}
        Spacer(Modifier.height(10.dp))
        if(filtrados.isEmpty()) {
            Text(stringResource(if(busqueda.isEmpty()) R.string.socios_vacio else R.string.socios_sin_resultados),color=MaterialTheme.colorScheme.onSurfaceVariant)
        } else {
            LazyColumn(verticalArrangement=Arrangement.spacedBy(10.dp)) {
                items(filtrados,key={it.id}) { socio ->
                    Card(Modifier.fillMaxWidth(),shape=RoundedCornerShape(12.dp),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.surface)) {
                        Column(Modifier.padding(14.dp),verticalArrangement=Arrangement.spacedBy(4.dp)) {
                            Text(socio.nombre,color=MaterialTheme.colorScheme.onSurface,fontWeight=FontWeight.Bold,fontSize=18.sp)
                            Text(stringResource(R.string.socio_telefono_formato,socio.telefono),color=MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(stringResource(R.string.socio_correo_formato,socio.correo),color=MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(stringResource(R.string.socio_estado_formato,socio.estado),color=MaterialTheme.colorScheme.tertiary)
                            Text(stringResource(R.string.socio_registro_formato,socio.fechaRegistro),color=MaterialTheme.colorScheme.onSurfaceVariant)
                            Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                                TextButton(onClick={socioEditar=socio}){Text(stringResource(R.string.btn_editar))}
                                TextButton(onClick={viewModel.eliminarSocio(socio)}){Text(stringResource(R.string.btn_eliminar),color=MaterialTheme.colorScheme.error)}
                            }
                        }
                    }
                }
            }
        }
    }
    socioEditar?.let { actual ->
        var nombre by rememberSaveable(actual.id) { mutableStateOf(actual.nombre) }
        var telefono by rememberSaveable(actual.id) { mutableStateOf(actual.telefono) }
        var correo by rememberSaveable(actual.id) { mutableStateOf(actual.correo) }
        var fecha by rememberSaveable(actual.id) { mutableStateOf(actual.fechaRegistro) }
        AlertDialog(
            onDismissRequest={socioEditar=null},
            title={Text(stringResource(R.string.editar_socio))},
            text={
                Column(verticalArrangement=Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(nombre,{nombre=it},label={Text(stringResource(R.string.campo_nombre_completo))})
                    OutlinedTextField(telefono,{telefono=it},label={Text(stringResource(R.string.campo_telefono))})
                    OutlinedTextField(correo,{correo=it},label={Text(stringResource(R.string.campo_correo))})
                    OutlinedTextField(fecha,{fecha=it},label={Text(stringResource(R.string.campo_fecha_alta))})
                }
            },
            confirmButton={TextButton(onClick={
                if(nombre.isNotBlank() && telefono.isNotBlank() && correo.contains("@") && fecha.isNotBlank()) {
                    viewModel.actualizarSocio(actual.copy(nombre=nombre,telefono=telefono,correo=correo,fechaRegistro=fecha))
                    socioEditar=null
                }
            }){Text(stringResource(R.string.btn_guardar))}},
            dismissButton={TextButton(onClick={socioEditar=null}){Text(stringResource(R.string.btn_cancelar))}}
        )
    }
}
