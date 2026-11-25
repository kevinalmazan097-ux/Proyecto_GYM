package com.Kevin.proyecto_gym.domain.model

data class Usuario(
    val id: Int = 0,
    val correo: String,
    val passwordHash: String,
    val salt: String
)
