package com.Kevin.proyecto_gym.data.mapper

import com.Kevin.proyecto_gym.data.database.entity.SocioEntity
import com.Kevin.proyecto_gym.data.database.entity.UsuarioEntity
import com.Kevin.proyecto_gym.domain.model.Socio
import com.Kevin.proyecto_gym.domain.model.Usuario

fun SocioEntity.toDomain() = Socio(id, nombre, telefono, correo, fechaRegistro, estado)
fun Socio.toEntity() = SocioEntity(id, nombre, telefono, correo, fechaRegistro, estado)

fun UsuarioEntity.toDomain() = Usuario(id, correo, passwordHash, salt)
fun Usuario.toEntity() = UsuarioEntity(id, correo, passwordHash, salt)
