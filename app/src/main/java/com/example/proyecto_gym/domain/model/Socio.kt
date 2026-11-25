package com.Kevin.proyecto_gym.domain.model

data class Socio(
    val id: Int = 0,
    val nombre: String,
    val telefono: String,
    val correo: String,
    val fechaRegistro: String,
    val estado: String
)
