package com.Kevin.proyecto_gym.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.Kevin.proyecto_gym.R
import com.Kevin.proyecto_gym.ui.GymViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun DashboardScreen(
    onModuloClick: (String) -> Unit,
    viewModel: GymViewModel = hiltViewModel()
) {
    val socios by viewModel.sociosCount.collectAsStateWithLifecycle()
    val membresias by viewModel.membresiasCount.collectAsStateWithLifecycle()
    val pagos by viewModel.pagosCount.collectAsStateWithLifecycle()
    val asistencias by viewModel.asistenciasCount.collectAsStateWithLifecycle()
    val modulos = listOf(
        Triple(R.string.modulo_socios, "socios", "S"),
        Triple(R.string.modulo_membresias, "membresias", "M"),
        Triple(R.string.modulo_pagos, "pagos", "P"),
        Triple(R.string.modulo_asistencia, "asistencia", "A"),
        Triple(R.string.modulo_rutinas, "rutinas", "R"),
        Triple(R.string.modulo_reportes, "reportes", "I")
    )
    Column(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(stringResource(R.string.app_name), color=MaterialTheme.colorScheme.primary,
            fontSize=28.sp,fontWeight=FontWeight.ExtraBold)
        Text(stringResource(R.string.dashboard_subtitulo),color=MaterialTheme.colorScheme.onSurfaceVariant)
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val horizontal = maxWidth >= 650.dp
            Column(Modifier.fillMaxSize(), verticalArrangement=Arrangement.spacedBy(10.dp)) {
                Text(stringResource(R.string.dashboard_resumen),style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold)
                val cards = listOf(
                    R.string.dashboard_socios_activos to socios.toString(),
                    R.string.reporte_membresias to membresias.toString(),
                    R.string.reporte_pagos to pagos.toString(),
                    R.string.dashboard_asistencias_hoy to asistencias.toString()
                )
                if(horizontal) {
                    Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                        cards.forEach { (label,value) -> ResumenCard(label,value,Modifier.weight(1f)) }
                    }
                } else {
                    cards.chunked(2).forEach { row ->
                        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(10.dp)) {
                            row.forEach { (label,value) -> ResumenCard(label,value,Modifier.weight(1f)) }
                        }
                    }
                }
                Text(stringResource(R.string.dashboard_modulos),style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold)
                val columnas = if(horizontal) 3 else 2
                Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(8.dp)) {
                    modulos.chunked(columnas).forEach { fila ->
                        Row(Modifier.fillMaxWidth().weight(1f),horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                            fila.forEach { (label,route,letter) ->
                                Card(
                                    onClick={ onModuloClick(route) },
                                    modifier=Modifier.weight(1f).fillMaxHeight(),
                                    shape=RoundedCornerShape(14.dp),
                                    colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.surface)
                                ) {
                                    Column(Modifier.fillMaxSize().padding(12.dp),verticalArrangement=Arrangement.Center) {
                                        Text(letter,color=MaterialTheme.colorScheme.primary,fontSize=23.sp,fontWeight=FontWeight.Black)
                                        Text(stringResource(label),color=MaterialTheme.colorScheme.onSurface,fontWeight=FontWeight.Bold)
                                        Text(stringResource(R.string.dashboard_abrir_modulo),color=MaterialTheme.colorScheme.primary,fontSize=12.sp)
                                    }
                                }
                            }
                            repeat(columnas-fila.size) { Spacer(Modifier.weight(1f)) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResumenCard(tituloRes:Int,cantidad:String,modifier:Modifier=Modifier) {
    Card(modifier,shape=RoundedCornerShape(12.dp),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.surface)) {
        Column(Modifier.fillMaxWidth().padding(12.dp)) {
            Text(stringResource(tituloRes),color=MaterialTheme.colorScheme.onSurfaceVariant,fontSize=12.sp)
            Text(cantidad,color=MaterialTheme.colorScheme.primary,fontSize=26.sp,fontWeight=FontWeight.Bold)
        }
    }
}
