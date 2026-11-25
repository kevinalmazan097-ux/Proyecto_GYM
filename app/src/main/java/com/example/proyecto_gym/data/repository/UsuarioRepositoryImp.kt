package com.Kevin.proyecto_gym.data.repository

import com.Kevin.proyecto_gym.data.database.dao.UsuarioDao
import com.Kevin.proyecto_gym.data.database.entity.UsuarioEntity
import com.Kevin.proyecto_gym.data.mapper.toDomain
import com.Kevin.proyecto_gym.data.mapper.toEntity
import com.Kevin.proyecto_gym.domain.model.Usuario
import com.Kevin.proyecto_gym.domain.repository.UsuarioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepositoryImp @Inject constructor(
    private val usuarioDao: UsuarioDao
) : UsuarioRepository {
    override suspend fun buscarUsuario(correo: String): Usuario? =
        usuarioDao.buscarUsuario(correo)?.toDomain()

    override suspend fun insertarUsuario(usuario: Usuario) {
        usuarioDao.insertarUsuario(usuario.toEntity())
    }

    override suspend fun contarUsuarios(): Int = usuarioDao.contarUsuarios()
}
