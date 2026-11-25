package com.Kevin.proyecto_gym.data.repository

import com.Kevin.proyecto_gym.data.database.dao.SocioDao
import com.Kevin.proyecto_gym.data.mapper.toDomain
import com.Kevin.proyecto_gym.data.mapper.toEntity
import com.Kevin.proyecto_gym.domain.model.Socio
import com.Kevin.proyecto_gym.domain.repository.SocioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocioRepositoryImp @Inject constructor(
    private val socioDao: SocioDao
) : SocioRepository {
    override fun obtenerSocios(): Flow<List<Socio>> =
        socioDao.obtenerSocios().map { lista -> lista.map { it.toDomain() } }

    override fun buscarSocios(nombre: String): Flow<List<Socio>> =
        socioDao.buscarSocios(nombre).map { lista -> lista.map { it.toDomain() } }

    override suspend fun insertarSocio(socio: Socio) =
        socioDao.insertarSocio(socio.toEntity())

    override suspend fun actualizarSocio(socio: Socio) =
        socioDao.actualizarSocio(socio.toEntity())

    override suspend fun eliminarSocio(socio: Socio) =
        socioDao.eliminarSocio(socio.toEntity())
}
