
package com.Kevin.proyecto_gym.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.Kevin.proyecto_gym.R
import com.Kevin.proyecto_gym.ui.GymViewModel

@Composable
fun ModuloScreen(
    modulo: String,
    onBack: () -> Unit,
    viewModel: GymViewModel = hiltViewModel()
) {
    val membresias by viewModel.membresias.collectAsStateWithLifecycle()
    val pagos by viewModel.pagos.collectAsStateWithLifecycle()
    val asistencias by viewModel.asistencias.collectAsStateWithLifecycle()

    val sociosCount by viewModel.sociosCount.collectAsStateWithLifecycle()
    val membresiasCount by viewModel.membresiasCount.collectAsStateWithLifecycle()
    val pagosCount by viewModel.pagosCount.collectAsStateWithLifecycle()
    val asistenciasCount by viewModel.asistenciasCount.collectAsStateWithLifecycle()

    var socio by rememberSaveable { mutableStateOf("") }
    var tipo by rememberSaveable { mutableStateOf("") }
    var inicio by rememberSaveable { mutableStateOf("") }
    var fin by rememberSaveable { mutableStateOf("") }
    var costo by rememberSaveable { mutableStateOf("") }
    var concepto by rememberSaveable { mutableStateOf("") }
    var monto by rememberSaveable { mutableStateOf("") }
    var fecha by rememberSaveable { mutableStateOf("") }
    var hora by rememberSaveable { mutableStateOf("") }
    var metodo by rememberSaveable { mutableStateOf("") }
    var observaciones by rememberSaveable { mutableStateOf("") }
    var mensaje by rememberSaveable { mutableStateOf("") }

    // Textos de error fuera de los eventos onClick
    val errorMembresia = stringResource(R.string.error_modulo_membresia)
    val errorPago = stringResource(R.string.error_modulo_pago)
    val errorAsistencia = stringResource(R.string.error_modulo_asistencia)

    val titulo = when (modulo) {
        "membresias" -> stringResource(R.string.modulo_membresias)
        "pagos" -> stringResource(R.string.modulo_pagos)
        "asistencia" -> stringResource(R.string.modulo_asistencia)
        "rutinas" -> stringResource(R.string.modulo_rutinas)
        else -> stringResource(R.string.modulo_reportes)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(onClick = onBack) {
                Text(stringResource(R.string.btn_regresar))
            }

            Text(
                titulo,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        BoxWithConstraints(Modifier.fillMaxSize()) {
            val horizontal = maxWidth >= 650.dp

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (modulo) {

                    // =========================
                    // MODULO DE MEMBRESIAS
                    // =========================
                    "membresias" -> {
                        Text(
                            stringResource(R.string.membresias_descripcion),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (horizontal) {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                CampoGym(socio, { socio = it }, R.string.campo_socio, Modifier.weight(1f))
                                CampoGym(tipo, { tipo = it }, R.string.campo_tipo_membresia, Modifier.weight(1f))
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                CampoGym(inicio, { inicio = it }, R.string.campo_fecha_inicio, Modifier.weight(1f))
                                CampoGym(fin, { fin = it }, R.string.campo_fecha_fin, Modifier.weight(1f))
                                CampoGym(costo, { costo = it }, R.string.campo_costo, Modifier.weight(1f))
                            }
                        } else {
                            CampoGym(socio, { socio = it }, R.string.campo_socio)
                            CampoGym(tipo, { tipo = it }, R.string.campo_tipo_membresia)
                            CampoGym(inicio, { inicio = it }, R.string.campo_fecha_inicio)
                            CampoGym(fin, { fin = it }, R.string.campo_fecha_fin)
                            CampoGym(costo, { costo = it }, R.string.campo_costo)
                        }

                        Button(
                            onClick = {
                                val cantidad = costo.toDoubleOrNull()

                                if (
                                    socio.isNotBlank() &&
                                    tipo.isNotBlank() &&
                                    inicio.isNotBlank() &&
                                    fin.isNotBlank() &&
                                    cantidad != null &&
                                    cantidad >= 0
                                ) {
                                    viewModel.agregarMembresia(
                                        socio, tipo, inicio, fin, cantidad
                                    )

                                    socio = ""
                                    tipo = ""
                                    inicio = ""
                                    fin = ""
                                    costo = ""
                                    mensaje = ""
                                } else {
                                    mensaje = errorMembresia
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.btn_guardar))
                        }

                        if (mensaje.isNotEmpty()) {
                            Text(mensaje, color = MaterialTheme.colorScheme.error)
                        }

                        Text(
                            stringResource(R.string.registros_guardados, membresias.size),
                            style = MaterialTheme.typography.titleMedium
                        )

                        membresias.forEach { item ->
                            RegistroCard(
                                "${item.socio} · ${item.tipo}",
                                "${item.fechaInicio} - ${item.fechaFin} · $${item.costo} · ${item.estado}"
                            ) {
                                viewModel.eliminarMembresia(item)
                            }
                        }
                    }

                    // =========================
                    // MODULO DE PAGOS
                    // =========================
                    "pagos" -> {
                        Text(
                            stringResource(R.string.pagos_descripcion),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (horizontal) {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                CampoGym(socio, { socio = it }, R.string.campo_socio, Modifier.weight(1f))
                                CampoGym(concepto, { concepto = it }, R.string.campo_concepto, Modifier.weight(1f))
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                CampoGym(monto, { monto = it }, R.string.campo_monto, Modifier.weight(1f))
                                CampoGym(fecha, { fecha = it }, R.string.campo_fecha, Modifier.weight(1f))
                                CampoGym(metodo, { metodo = it }, R.string.campo_metodo_pago, Modifier.weight(1f))
                            }
                        } else {
                            CampoGym(socio, { socio = it }, R.string.campo_socio)
                            CampoGym(concepto, { concepto = it }, R.string.campo_concepto)
                            CampoGym(monto, { monto = it }, R.string.campo_monto)
                            CampoGym(fecha, { fecha = it }, R.string.campo_fecha)
                            CampoGym(metodo, { metodo = it }, R.string.campo_metodo_pago)
                        }

                        Button(
                            onClick = {
                                val cantidad = monto.toDoubleOrNull()

                                if (
                                    socio.isNotBlank() &&
                                    concepto.isNotBlank() &&
                                    cantidad != null &&
                                    cantidad >= 0 &&
                                    fecha.isNotBlank() &&
                                    metodo.isNotBlank()
                                ) {
                                    viewModel.agregarPago(
                                        socio, concepto, cantidad, fecha, metodo
                                    )

                                    socio = ""
                                    concepto = ""
                                    monto = ""
                                    fecha = ""
                                    metodo = ""
                                    mensaje = ""
                                } else {
                                    mensaje = errorPago
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.btn_registrar_pago))
                        }

                        if (mensaje.isNotEmpty()) {
                            Text(mensaje, color = MaterialTheme.colorScheme.error)
                        }

                        Text(
                            stringResource(R.string.registros_guardados, pagos.size),
                            style = MaterialTheme.typography.titleMedium
                        )

                        pagos.forEach { item ->
                            RegistroCard(
                                "${item.socio} · ${item.concepto}",
                                "$${item.monto} · ${item.fecha} · ${item.metodo}"
                            ) {
                                viewModel.eliminarPago(item)
                            }
                        }
                    }

                    // =========================
                    // MODULO DE ASISTENCIAS
                    // =========================
                    "asistencia" -> {
                        Text(
                            stringResource(R.string.asistencia_descripcion),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (horizontal) {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                CampoGym(socio, { socio = it }, R.string.campo_socio, Modifier.weight(1f))
                                CampoGym(fecha, { fecha = it }, R.string.campo_fecha, Modifier.weight(1f))
                                CampoGym(hora, { hora = it }, R.string.campo_hora, Modifier.weight(1f))
                            }
                        } else {
                            CampoGym(socio, { socio = it }, R.string.campo_socio)
                            CampoGym(fecha, { fecha = it }, R.string.campo_fecha)
                            CampoGym(hora, { hora = it }, R.string.campo_hora)
                        }

                        CampoGym(
                            observaciones,
                            { observaciones = it },
                            R.string.campo_observaciones
                        )

                        Button(
                            onClick = {
                                if (
                                    socio.isNotBlank() &&
                                    fecha.isNotBlank() &&
                                    hora.isNotBlank()
                                ) {
                                    viewModel.agregarAsistencia(
                                        socio, fecha, hora, observaciones
                                    )

                                    socio = ""
                                    fecha = ""
                                    hora = ""
                                    observaciones = ""
                                    mensaje = ""
                                } else {
                                    mensaje = errorAsistencia
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.btn_registrar_asistencia))
                        }

                        if (mensaje.isNotEmpty()) {
                            Text(mensaje, color = MaterialTheme.colorScheme.error)
                        }

                        Text(
                            stringResource(R.string.registros_guardados, asistencias.size),
                            style = MaterialTheme.typography.titleMedium
                        )

                        asistencias.forEach { item ->
                            RegistroCard(
                                item.socio,
                                "${item.fecha} · ${item.hora} · ${item.observaciones}"
                            ) {
                                viewModel.eliminarAsistencia(item)
                            }
                        }
                    }

                    // =========================
                    // MODULO DE RUTINAS
                    // =========================
                    "rutinas" -> {
                        Text(
                            stringResource(R.string.rutinas_descripcion),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        RutinaCard(
                            stringResource(R.string.rutina_a),
                            stringResource(R.string.rutina_a_detalle)
                        )

                        RutinaCard(
                            stringResource(R.string.rutina_b),
                            stringResource(R.string.rutina_b_detalle)
                        )

                        RutinaCard(
                            stringResource(R.string.rutina_c),
                            stringResource(R.string.rutina_c_detalle)
                        )

                        RutinaCard(
                            stringResource(R.string.rutina_d),
                            stringResource(R.string.rutina_d_detalle)
                        )
                    }

                    // =========================
                    // MODULO DE REPORTES
                    // =========================
                    else -> {
                        Text(
                            stringResource(R.string.reportes_descripcion),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            ReporteCard(
                                stringResource(R.string.reporte_socios),
                                sociosCount.toString(),
                                Modifier.weight(1f)
                            )

                            ReporteCard(
                                stringResource(R.string.reporte_membresias),
                                membresiasCount.toString(),
                                Modifier.weight(1f)
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            ReporteCard(
                                stringResource(R.string.reporte_pagos),
                                pagosCount.toString(),
                                Modifier.weight(1f)
                            )

                            ReporteCard(
                                stringResource(R.string.reporte_asistencias),
                                asistenciasCount.toString(),
                                Modifier.weight(1f)
                            )
                        }

                        Text(
                            stringResource(R.string.reportes_nota),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

// =========================
// CAMPO REUTILIZABLE
// =========================
@Composable
private fun CampoGym(
    value: String,
    onValueChange: (String) -> Unit,
    labelRes: Int,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(labelRes)) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true
    )
}

// =========================
// TARJETA DE REGISTROS
// =========================
@Composable
private fun RegistroCard(
    titulo: String,
    detalle: String,
    onDelete: () -> Unit
) {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                titulo,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                detalle,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            TextButton(onClick = onDelete) {
                Text(
                    stringResource(R.string.btn_eliminar),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

// =========================
// TARJETA DE RUTINAS
// =========================
@Composable
private fun RutinaCard(
    titulo: String,
    detalle: String
) {
    Card(
        Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                titulo,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                detalle,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// =========================
// TARJETA DE REPORTES
// =========================
@Composable
private fun ReporteCard(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                titulo,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                valor,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
