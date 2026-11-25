package com.Kevin.proyecto_gym.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Kevin.proyecto_gym.domain.model.Socio
import com.Kevin.proyecto_gym.domain.repository.SocioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SociosViewModel @Inject constructor(
    private val repository: SocioRepository
) : ViewModel() {
    val socios: StateFlow<List<Socio>> = repository.obtenerSocios().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun actualizarSocio(socio: Socio) {
        viewModelScope.launch { repository.actualizarSocio(socio) }
    }

    fun eliminarSocio(socio: Socio) {
        viewModelScope.launch { repository.eliminarSocio(socio) }
    }

    fun agregarSocio(
        nombre: String,
        telefono: String,
        correo: String,
        fecha: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.insertarSocio(
                    Socio(
                        nombre = nombre.trim(),
                        telefono = telefono.trim(),
                        correo = correo.trim(),
                        fechaRegistro = fecha.trim(),
                        estado = "Activa"
                    )
                )
                onResult(true)
            } catch (_: Exception) {
                onResult(false)
            }
        }
    }
}
