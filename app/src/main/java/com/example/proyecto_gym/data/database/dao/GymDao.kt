package com.Kevin.proyecto_gym.data.database.dao

import androidx.room.*
import com.Kevin.proyecto_gym.data.database.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GymDao {
    @Query("SELECT * FROM membresias ORDER BY id DESC")
    fun obtenerMembresias(): Flow<List<MembresiaEntity>>
    @Insert
    suspend fun insertarMembresia(item: MembresiaEntity)
    @Update
    suspend fun actualizarMembresia(item: MembresiaEntity)
    @Delete
    suspend fun eliminarMembresia(item: MembresiaEntity)

    @Query("SELECT * FROM pagos ORDER BY id DESC")
    fun obtenerPagos(): Flow<List<PagoEntity>>
    @Insert
    suspend fun insertarPago(item: PagoEntity)
    @Update
    suspend fun actualizarPago(item: PagoEntity)
    @Delete
    suspend fun eliminarPago(item: PagoEntity)

    @Query("SELECT * FROM asistencias ORDER BY id DESC")
    fun obtenerAsistencias(): Flow<List<AsistenciaEntity>>
    @Insert
    suspend fun insertarAsistencia(item: AsistenciaEntity)
    @Update
    suspend fun actualizarAsistencia(item: AsistenciaEntity)
    @Delete
    suspend fun eliminarAsistencia(item: AsistenciaEntity)

    @Query("SELECT COUNT(*) FROM socios")
    fun contarSocios(): Flow<Int>
    @Query("SELECT COUNT(*) FROM membresias")
    fun contarMembresias(): Flow<Int>
    @Query("SELECT COUNT(*) FROM pagos")
    fun contarPagos(): Flow<Int>
    @Query("SELECT COUNT(*) FROM asistencias")
    fun contarAsistencias(): Flow<Int>
}
