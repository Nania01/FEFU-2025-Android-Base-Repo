package co.feip.fefu2025.presentation.detail

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeDetailUseCase
import kotlinx.coroutines.launch

sealed class AnimeDetailUiState {
    object Loading : AnimeDetailUiState()
    data class Success(val anime: Anime) : AnimeDetailUiState()
    data class Error(val message: String) : AnimeDetailUiState()
}

class AnimeDetailViewModel(
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    private val animeId: Int
) : ViewModel() {

    var uiState by mutableStateOf<AnimeDetailUiState>(AnimeDetailUiState.Loading)
        private set

    init {
        loadDetail()
    }

    fun loadDetail() {
        uiState = AnimeDetailUiState.Loading
        viewModelScope.launch {
            try {
                val result = getAnimeDetailUseCase(animeId)
                uiState = if (result != null) {
                    AnimeDetailUiState.Success(result)
                } else {
                    AnimeDetailUiState.Error("Аниме не найдено")
                }
            } catch (e: Exception) {
                uiState = AnimeDetailUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    class Factory(
        private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
        private val animeId: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AnimeDetailViewModel(getAnimeDetailUseCase, animeId) as T
        }
    }
}