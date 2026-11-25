package com.Kevin.proyecto_gym.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Kevin.proyecto_gym.data.database.dao.GymDao
import com.Kevin.proyecto_gym.data.database.entity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class GymViewModel @Inject constructor(private val dao: GymDao) : ViewModel() {
    val membresias = dao.obtenerMembresias().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val pagos = dao.obtenerPagos().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val asistencias = dao.obtenerAsistencias().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val sociosCount = dao.contarSocios().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val membresiasCount = dao.contarMembresias().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val pagosCount = dao.contarPagos().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val asistenciasCount = dao.contarAsistencias().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun agregarMembresia(socio: String, tipo: String, inicio: String, fin: String, costo: Double) = viewModelScope.launch {
        dao.insertarMembresia(MembresiaEntity(socio=socio, tipo=tipo, fechaInicio=inicio, fechaFin=fin, costo=costo))
    }
    fun eliminarMembresia(item: MembresiaEntity) = viewModelScope.launch { dao.eliminarMembresia(item) }
    fun agregarPago(socio: String, concepto: String, monto: Double, fecha: String, metodo: String) = viewModelScope.launch {
        dao.insertarPago(PagoEntity(socio=socio, concepto=concepto, monto=monto, fecha=fecha, metodo=metodo))
    }
    fun eliminarPago(item: PagoEntity) = viewModelScope.launch { dao.eliminarPago(item) }
    fun agregarAsistencia(socio: String, fecha: String, hora: String, observaciones: String) = viewModelScope.launch {
        dao.insertarAsistencia(AsistenciaEntity(socio=socio, fecha=fecha, hora=hora, observaciones=observaciones))
    }
    fun eliminarAsistencia(item: AsistenciaEntity) = viewModelScope.launch { dao.eliminarAsistencia(item) }
}
