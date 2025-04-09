package co.feip.fefu2025

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.runtime.*

@Composable
fun AnimeCard(
    title: String,
    rating: String,
    genres: List<String>,
    image: Painter,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.9f)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = image,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                )

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .wrapContentSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = rating,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = "Star Icon",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(4.dp))

            GenreTags(
                genres = genres,
                maxWidth = 180.dp
            )
        }
    }
}

@Composable
fun GenreTags(genres: List<String>, maxWidth: Dp, maxLines: Int = 2) {
    val genreChunks = mutableListOf<List<String>>()
    var currentRow = mutableListOf<String>()
    var currentWidth = 0.dp

    genres.forEach { genre ->
        val genreWidth = (genre.length * 7.5).dp + 12.dp
        if (currentWidth + genreWidth > maxWidth) {
            genreChunks.add(currentRow)
            currentRow = mutableListOf()
            currentWidth = 0.dp
            if (genreChunks.size >= maxLines) return@forEach
        }
        currentRow.add(genre)
        currentWidth += genreWidth
    }

    if (currentRow.isNotEmpty() && genreChunks.size < maxLines) {
        genreChunks.add(currentRow)
    }

    Column(
        modifier = Modifier.width(maxWidth),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        genreChunks.forEach { rowGenres ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                rowGenres.forEach { genre ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 2.dp, vertical = 2.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.LightGray)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = genre,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAnimeCard() {
    AnimeCard(
        title = "Монолог фармацевта, который немного слишком длинный",
        rating = "8.8",
        genres = listOf("Драма", "Детектив", "История", "Медицина", "Фэнтези", "Сёдзё", "Сэйнэн"),
        image = painterResource(id = R.drawable.monologue_pharmacist),
        modifier = Modifier.width(200.dp)
    )
}
