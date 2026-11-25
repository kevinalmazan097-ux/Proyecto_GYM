package com.Kevin.proyecto_gym.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "socios")
data class SocioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val telefono: String,
    val correo: String,
    val fechaRegistro: String,
    val estado: String
)
