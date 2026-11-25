package com.Kevin.proyecto_gym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.Kevin.proyecto_gym.navigation.AppNavigation
import com.Kevin.proyecto_gym.ui.theme.Proyecto_gymTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Proyecto_gymTheme {
                AppNavigation()
            }
        }
    }
}
