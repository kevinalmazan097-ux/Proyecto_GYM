package com.Kevin.proyecto_gym.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val correo: String,
    val passwordHash: String,
    val salt: String
)
