package com.Kevin.proyecto_gym.data.di

import com.Kevin.proyecto_gym.data.repository.SocioRepositoryImp
import com.Kevin.proyecto_gym.data.repository.UsuarioRepositoryImp
import com.Kevin.proyecto_gym.domain.repository.SocioRepository
import com.Kevin.proyecto_gym.domain.repository.UsuarioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSocioRepository(implementation: SocioRepositoryImp): SocioRepository

    @Binds
    @Singleton
    abstract fun bindUsuarioRepository(implementation: UsuarioRepositoryImp): UsuarioRepository
}
