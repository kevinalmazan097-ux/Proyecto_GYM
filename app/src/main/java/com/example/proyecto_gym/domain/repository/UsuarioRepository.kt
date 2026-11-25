package com.Kevin.proyecto_gym.domain.repository

import com.Kevin.proyecto_gym.domain.model.Usuario

interface UsuarioRepository {
    suspend fun buscarUsuario(correo: String): Usuario?
    suspend fun insertarUsuario(usuario: Usuario)
    suspend fun contarUsuarios(): Int
}
