package com.example.rustore.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustore.data.models.App
import com.example.rustore.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AppDetailUiState(
    val app: App? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class AppDetailViewModel : ViewModel() {

    private val repository = AppRepository()

    private val _uiState = MutableStateFlow(AppDetailUiState(isLoading = false))
    val uiState: StateFlow<AppDetailUiState> = _uiState.asStateFlow()

    fun loadApp(appId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = AppDetailUiState(isLoading = true)
                val app = repository.getAppById(appId)
                if (app != null) {
                    _uiState.value = AppDetailUiState(app = app)
                } else {
                    _uiState.value = AppDetailUiState(error = "Приложение не найдено")
                }
            } catch (e: Exception) {
                _uiState.value = AppDetailUiState(error = "Ошибка загрузки: ${e.message}")
            }
        }
    }
}