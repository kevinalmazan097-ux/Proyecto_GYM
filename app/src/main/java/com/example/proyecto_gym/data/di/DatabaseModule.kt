package com.Kevin.proyecto_gym.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.Kevin.proyecto_gym.data.database.AppDatabase
import com.Kevin.proyecto_gym.data.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""CREATE TABLE IF NOT EXISTS membresias (
            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            socio TEXT NOT NULL, tipo TEXT NOT NULL, fechaInicio TEXT NOT NULL,
            fechaFin TEXT NOT NULL, costo REAL NOT NULL, estado TEXT NOT NULL)""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS pagos (
            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            socio TEXT NOT NULL, concepto TEXT NOT NULL, monto REAL NOT NULL,
            fecha TEXT NOT NULL, metodo TEXT NOT NULL)""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS asistencias (
            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            socio TEXT NOT NULL, fecha TEXT NOT NULL, hora TEXT NOT NULL,
            observaciones TEXT NOT NULL)""")
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "gym_kevin_database")
            .addMigrations(MIGRATION_2_3)
            .build()

    @Provides fun provideSocioDao(database: AppDatabase): SocioDao = database.socioDao()
    @Provides fun provideUsuarioDao(database: AppDatabase): UsuarioDao = database.usuarioDao()
    @Provides fun provideGymDao(database: AppDatabase): GymDao = database.gymDao()
}
