package co.feip.fefu2025.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import co.feip.fefu2025.presentation.common.AnimeUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getAnimeListUseCase: GetAnimeListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnimeUiState>(AnimeUiState.Success(emptyList()))
    val uiState: StateFlow<AnimeUiState> = _uiState

    fun search(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                _uiState.value = AnimeUiState.Success(emptyList())
                return@launch
            }

            _uiState.value = AnimeUiState.Loading
            delay(300)

            val result = getAnimeListUseCase()
            val filtered = result.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.info?.contains(query, ignoreCase = true) == true
            }

            _uiState.value = AnimeUiState.Success(filtered)
        }
    }

    class Factory(private val useCase: GetAnimeListUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(useCase) as T
        }
    }
}