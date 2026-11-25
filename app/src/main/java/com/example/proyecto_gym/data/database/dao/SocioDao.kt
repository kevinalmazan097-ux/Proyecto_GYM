package com.Kevin.proyecto_gym.data.database.dao

import androidx.room.*
import com.Kevin.proyecto_gym.data.database.entity.SocioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SocioDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarSocio(socio: SocioEntity)

    @Update
    suspend fun actualizarSocio(socio: SocioEntity)

    @Delete
    suspend fun eliminarSocio(socio: SocioEntity)

    @Query("SELECT * FROM socios WHERE id = :id LIMIT 1")
    fun obtenerSocio(id: Int): Flow<SocioEntity?>

    @Query("SELECT * FROM socios ORDER BY nombre ASC")
    fun obtenerSocios(): Flow<List<SocioEntity>>

    @Query("SELECT * FROM socios WHERE nombre LIKE '%' || :nombre || '%' ORDER BY nombre ASC")
    fun buscarSocios(nombre: String): Flow<List<SocioEntity>>
}
