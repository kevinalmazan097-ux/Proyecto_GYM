
package com.Kevin.proyecto_gym.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val GymColorScheme = darkColorScheme(
    primary = RojoPrincipal,
    onPrimary = Blanco,

    secondary = RojoOscuro,
    onSecondary = Blanco,

    background = NegroFondo,
    onBackground = Blanco,

    surface = NegroTarjeta,
    onSurface = Blanco,

    tertiary = VerdeEstado,
    error = RojoPrincipal,
    onError = Blanco
)

@Composable
fun Proyecto_gymTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = GymColorScheme,
        typography = Typography,
        content = content
    )
}
