package com.Kevin.proyecto_gym.domain.repository

import com.Kevin.proyecto_gym.domain.model.Socio
import kotlinx.coroutines.flow.Flow

interface SocioRepository {
    fun obtenerSocios(): Flow<List<Socio>>
    fun buscarSocios(nombre: String): Flow<List<Socio>>
    suspend fun insertarSocio(socio: Socio)
    suspend fun actualizarSocio(socio: Socio)
    suspend fun eliminarSocio(socio: Socio)
}
