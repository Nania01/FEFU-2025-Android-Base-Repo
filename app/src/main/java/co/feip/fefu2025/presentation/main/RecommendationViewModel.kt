package co.feip.fefu2025.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.repository.MockAnimeRepository
import co.feip.fefu2025.presentation.common.AnimeUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecommendationViewModel : ViewModel() {
    private val repository = MockAnimeRepository()

    private val _uiState = MutableStateFlow<AnimeUiState>(AnimeUiState.Loading)
    val uiState: StateFlow<AnimeUiState> = _uiState

    init {
        loadRecommendations()
    }

    fun loadRecommendations() {
        viewModelScope.launch {
            _uiState.value = AnimeUiState.Loading
            try {
                delay(2000)
                if ((0..1).random() == 0) throw RuntimeException("Ошибка")

                val recommendations = repository.getGlobalRecommendations()
                _uiState.value = AnimeUiState.Success(recommendations)
            } catch (e: Exception) {
                _uiState.value = AnimeUiState.Error("не удалось загрузить рекомендации")
            }
        }
    }
}