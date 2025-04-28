package co.feip.fefu2025

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.presentation.common.AnimeUiState
import co.feip.fefu2025.presentation.search.SearchViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModelFactory: SearchViewModel.Factory,
    onBackClick: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
    val viewModel: SearchViewModel = viewModel(factory = viewModelFactory)

    var query by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    var searchJob by remember { mutableStateOf<Job?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            title = { Text("Поиск", fontSize = 20.sp) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                }
            }
        )

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it

                searchJob?.cancel()

                searchJob = coroutineScope.launch {
                    delay(600)
                    viewModel.search(query)
                }
            },
            placeholder = { Text("Введите название или студию", fontSize = 14.sp) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF0F0F0),
                unfocusedContainerColor = Color(0xFFF0F0F0),
                disabledContainerColor = Color(0xFFF0F0F0),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(52.dp)
                .shadow(4.dp, shape = RoundedCornerShape(24.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (state) {
            is AnimeUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF4CAF50))
                }
            }

            is AnimeUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Ошибка: не удалось загрузить результаты поиска",
                        color = Color.Red
                    )
                }
            }

            is AnimeUiState.Success -> {
                val results = (state as AnimeUiState.Success).animeList
                if (results.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Ничего не найдено", color = Color.Gray)
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(results) { anime ->
                            AnimeCard(
                                title = anime.title,
                                rating = anime.rating,
                                genres = anime.genres,
                                image = painterResource(id = anime.imageResId),
                                modifier = Modifier.clickable {
                                    onAnimeClick(anime.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}