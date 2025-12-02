package com.example.rustore.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustore.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScreenshotsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ScreenshotsState())
    val uiState: StateFlow<ScreenshotsState> = _uiState.asStateFlow()

    private val repository = AppRepository()

    fun loadApp(appId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val app = repository.getAppById(appId) // ← Используем правильный метод
                _uiState.value = _uiState.value.copy(
                    screenshots = app?.screenshots ?: emptyList(),
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Ошибка загрузки скриншотов",
                    isLoading = false
                )
            }
        }
    }
}

data class ScreenshotsState(
    val screenshots: List<Int> = emptyList(), // ← Исправлено на List<Int>
    val isLoading: Boolean = false,
    val error: String? = null
)