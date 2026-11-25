package com.Kevin.proyecto_gym.database

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordHasher {

    private const val ITERACIONES = 120000
    private const val LONGITUD_HASH = 256

    fun generarSalt(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)

        return Base64.encodeToString(
            bytes,
            Base64.NO_WRAP
        )
    }

    fun generarHash(password: String, salt: String): String {
        val saltBytes = Base64.decode(salt, Base64.NO_WRAP)

        val spec = PBEKeySpec(
            password.toCharArray(),
            saltBytes,
            ITERACIONES,
            LONGITUD_HASH
        )

        val factory = SecretKeyFactory.getInstance(
            "PBKDF2WithHmacSHA256"
        )

        val hash = factory.generateSecret(spec).encoded

        spec.clearPassword()

        return Base64.encodeToString(
            hash,
            Base64.NO_WRAP
        )
    }

    fun verificarPassword(
        password: String,
        salt: String,
        hashGuardado: String
    ): Boolean {
        val hashIngresado = generarHash(password, salt)

        return hashIngresado == hashGuardado
    }
}