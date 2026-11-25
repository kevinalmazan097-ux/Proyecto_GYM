package com.Kevin.proyecto_gym.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Kevin.proyecto_gym.database.DatabaseInitializer
import com.Kevin.proyecto_gym.database.PasswordHasher
import com.Kevin.proyecto_gym.domain.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class LoginError { NONE, EMPTY_FIELDS, INVALID_CREDENTIALS, DATABASE_ERROR }

data class LoginUiState(
    val loading: Boolean = false,
    val error: LoginError = LoginError.NONE,
    val loggedIn: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UsuarioRepository,
    private val databaseInitializer: DatabaseInitializer
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun iniciarSesion(correo: String, password: String) {
        if (correo.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState(error = LoginError.EMPTY_FIELDS)
            return
        }
        viewModelScope.launch {
            _uiState.value = LoginUiState(loading = true)
            try {
                databaseInitializer.crearUsuarioInicial()
                val usuario = repository.buscarUsuario(correo.trim().lowercase())
                val valido = usuario != null &&
                    PasswordHasher.verificarPassword(password, usuario.salt, usuario.passwordHash)
                _uiState.value = if (valido) {
                    LoginUiState(loggedIn = true)
                } else {
                    LoginUiState(error = LoginError.INVALID_CREDENTIALS)
                }
            } catch (_: Exception) {
                _uiState.value = LoginUiState(error = LoginError.DATABASE_ERROR)
            }
        }
    }

    fun limpiarError() {
        _uiState.value = _uiState.value.copy(error = LoginError.NONE)
    }
}
