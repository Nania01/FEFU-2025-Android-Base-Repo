package co.feip.fefu2025

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.presentation.main.RecommendationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationListScreen(
    viewModel: RecommendationViewModel = viewModel(),
    onBackClick: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
    val recommendations = viewModel.recommendations

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            title = {
                Text("Может понравиться", fontSize = 20.sp)
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                }
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(recommendations) { anime ->
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

