package co.feip.fefu2025.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import co.feip.fefu2025.presentation.common.AnimeUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val getAnimeListUseCase: GetAnimeListUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _uiState = MutableStateFlow<AnimeUiState>(AnimeUiState.Success(emptyList()))
    val uiState: StateFlow<AnimeUiState> = _uiState

    init {
        viewModelScope.launch {
            _query
                .debounce(1200)
                .distinctUntilChanged()
                .collectLatest { newQuery ->
                    if (newQuery.isBlank()) {
                        _uiState.value = AnimeUiState.Success(emptyList())
                        return@collectLatest
                    }

                    _uiState.value = AnimeUiState.Loading

                    try {
                        val result = getAnimeListUseCase()
                        val filtered = result.filter {
                            it.title.contains(newQuery, ignoreCase = true) ||
                                    (it.info?.contains(newQuery, ignoreCase = true) == true)
                        }
                        _uiState.value = AnimeUiState.Success(filtered)
                    } catch (e: Exception) {
                        _uiState.value = AnimeUiState.Error("не удалось загрузить результаты поиска")
                    }
                }
        }
    }

    fun updateQuery(value: String) {
        _query.value = value
    }

    class Factory(private val useCase: GetAnimeListUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(useCase) as T
        }
    }
}