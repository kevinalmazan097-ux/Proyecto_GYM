package com.Kevin.proyecto_gym.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pagos")
data class PagoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val socio: String,
    val concepto: String,
    val monto: Double,
    val fecha: String,
    val metodo: String
)
