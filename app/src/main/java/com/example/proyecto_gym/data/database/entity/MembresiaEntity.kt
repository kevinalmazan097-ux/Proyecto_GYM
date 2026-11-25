package com.Kevin.proyecto_gym.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "membresias")
data class MembresiaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val socio: String,
    val tipo: String,
    val fechaInicio: String,
    val fechaFin: String,
    val costo: Double,
    val estado: String = "Activa"
)
