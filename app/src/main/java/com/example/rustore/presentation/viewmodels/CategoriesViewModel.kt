package com.example.rustore.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustore.data.models.AppCategory
import com.example.rustore.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesState())
    val uiState: StateFlow<CategoriesState> = _uiState.asStateFlow()

    private val repository = AppRepository()

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val categories = repository.getCategories()
                _uiState.value = _uiState.value.copy(
                    categories = categories,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Ошибка загрузки категорий",
                    isLoading = false
                )
            }
        }
    }
}

data class CategoriesState(
    val categories: Map<AppCategory, Int> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)