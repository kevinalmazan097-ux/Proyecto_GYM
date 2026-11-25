package com.Kevin.proyecto_gym.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asistencias")
data class AsistenciaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val socio: String,
    val fecha: String,
    val hora: String,
    val observaciones: String = ""
)
