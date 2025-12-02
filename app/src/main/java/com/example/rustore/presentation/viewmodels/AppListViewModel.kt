package com.example.rustore.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustore.data.models.App
import com.example.rustore.data.models.AppCategory
import com.example.rustore.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppListViewModel : ViewModel() {

    private val repository = AppRepository()

    private val _apps = MutableStateFlow<List<App>>(emptyList())
    val apps: StateFlow<List<App>> = _apps.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _categoryDisplayName = MutableStateFlow<String?>(null)
    val categoryDisplayName: StateFlow<String?> = _categoryDisplayName.asStateFlow()

    fun loadApps(category: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _selectedCategory.value = category

            _apps.value = if (category != null) {
                // Безопасно конвертируем строку в AppCategory
                try {
                    val appCategory = AppCategory.valueOf(category)
                    // Обновляем отображаемое имя категории
                    _categoryDisplayName.value = getCategoryDisplayName(appCategory)
                    repository.getAppsByCategory(appCategory)
                } catch (e: IllegalArgumentException) {
                    // Если категория не найдена, показываем все приложения
                    _categoryDisplayName.value = null
                    repository.getAllApps()
                }
            } else {
                _categoryDisplayName.value = null
                repository.getAllApps()
            }

            _isLoading.value = false
        }
    }

    fun clearCategoryFilter() {
        viewModelScope.launch {
            _selectedCategory.value = null
            _categoryDisplayName.value = null
            _apps.value = repository.getAllApps()
        }
    }

    // Функция для получения отображаемого имени категории
    private fun getCategoryDisplayName(category: AppCategory): String {
        return when (category) {
            AppCategory.FINANCE -> "Финансы"
            AppCategory.TOOLS -> "Инструменты"
            AppCategory.GAMES -> "Игры"
            AppCategory.GOVERNMENT -> "Государственные"
            AppCategory.TRANSPORT -> "Транспорт"
        }
    }
}