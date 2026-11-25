package com.Kevin.proyecto_gym.database

import com.Kevin.proyecto_gym.domain.model.Usuario
import com.Kevin.proyecto_gym.domain.repository.UsuarioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializer @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    suspend fun crearUsuarioInicial() {
        if (usuarioRepository.contarUsuarios() == 0) {
            val salt = PasswordHasher.generarSalt()
            val hash = PasswordHasher.generarHash("Kevin", salt)
            usuarioRepository.insertarUsuario(
                Usuario(
                    correo = "kevin@gmail.com",
                    passwordHash = hash,
                    salt = salt
                )
            )
        }
    }
}
