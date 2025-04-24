package co.feip.fefu2025

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.presentation.detail.AnimeDetailViewModel
import co.feip.fefu2025.presentation.detail.AnimeDetailUiState
import co.feip.fefu2025.RatingBarChart
import co.feip.fefu2025.AnimeCard
import co.feip.fefu2025.domain.model.Anime

@Composable
fun AnimeScreen(
    animeId: Int,
    viewModelFactory: AnimeDetailViewModel.Factory,
    onAnimeClick: (Int) -> Unit,
    onRecommendationsClick: () -> Unit
) {
    val viewModel: AnimeDetailViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.uiState

    when (state) {
        is AnimeDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF4CAF50))
            }
        }

        is AnimeDetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Ошибка: не удалось загрузить детали аниме", color = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.loadDetail() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Повторить")
                    }
                }
            }
        }

        is AnimeDetailUiState.Success -> {
            AnimeScreenContent(
                anime = state.anime,
                onAnimeClick = onAnimeClick,
                onRecommendationsClick = onRecommendationsClick
            )
        }
    }
}

@Composable
fun AnimeScreenContent(
    anime: Anime,
    onAnimeClick: (Int) -> Unit,
    onRecommendationsClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        ) {
            Image(
                painter = painterResource(id = anime.imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val fontSize: TextUnit = when {
                anime.title.length <= 10 -> 30.sp
                anime.title.length <= 15 -> 26.sp
                anime.title.length <= 25 -> 22.sp
                else -> 20.sp
            }

            Text(
                text = anime.title,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 4.dp)
            )

            anime.info?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
            }

            GenreTagsFull(genres = anime.genres)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatBlock(
                    label = "Сезоны и серии",
                    value = anime.episodesInfo ?: "Неизвестно",
                    backgroundColor = Color(0xFFE0F2F1),
                    modifier = Modifier.weight(1f)
                )
                StatBlock(
                    label = "Рейтинг аниме",
                    value = "${anime.rating} из 10",
                    backgroundColor = Color(0xFFFFF4E1),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            anime.description?.let { DescriptionBlock(it) }

            Spacer(modifier = Modifier.height(16.dp))

            anime.ratings?.let {
                Text(
                    text = "Распределение оценок",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 4.dp),
                    color = Color.Black
                )
                RatingBarChart(ratings = it)
            }

            Spacer(modifier = Modifier.height(4.dp))

            anime.recommendations?.let { recommendations ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Вам может понравиться:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true, color = Color(0x22000000)),
                                onClick = onRecommendationsClick
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Открыть все рекомендации",
                            tint = Color.Black
                        )
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(start = 4.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(recommendations) { rec ->
                        AnimeCard(
                            title = rec.title,
                            rating = rec.rating,
                            genres = rec.genres,
                            image = painterResource(id = rec.imageResId),
                            modifier = Modifier
                                .width(200.dp)
                                .clickable { onAnimeClick(rec.id) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun StatBlock(
    label: String,
    value: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun GenreTagsFull(genres: List<String>) {
    val rowWidth = 300.dp
    val genreChunks = mutableListOf<List<String>>()
    var currentRow = mutableListOf<String>()
    var currentWidth = 0.dp

    genres.forEach { genre ->
        val genreWidth = (genre.length * 7.5).dp + 12.dp

        if (currentWidth + genreWidth > rowWidth) {
            genreChunks.add(currentRow)
            currentRow = mutableListOf()
            currentWidth = 0.dp
        }

        currentRow.add(genre)
        currentWidth += genreWidth
    }

    if (currentRow.isNotEmpty()) {
        genreChunks.add(currentRow)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        genreChunks.forEach { rowGenres ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                rowGenres.forEach { genre ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFE0E0E0))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = genre,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DescriptionBlock(description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {
        Text(
            text = "Описание",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = description,
            fontSize = 14.sp,
            color = Color.DarkGray,
            lineHeight = 20.sp
        )
    }
}