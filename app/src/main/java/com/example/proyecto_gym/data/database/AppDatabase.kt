package com.Kevin.proyecto_gym.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.Kevin.proyecto_gym.data.database.dao.*
import com.Kevin.proyecto_gym.data.database.entity.*

@Database(
    entities = [UsuarioEntity::class, SocioEntity::class, MembresiaEntity::class, PagoEntity::class, AsistenciaEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun socioDao(): SocioDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun gymDao(): GymDao
}
