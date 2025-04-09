package co.feip.fefu2025

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(animeList: List<AnimeItem>) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    text = "Поиск",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .shadow(4.dp, shape = RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFF0F0F0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(animeList) { anime ->
                AnimeCard(
                    title = anime.title,
                    rating = anime.rating,
                    genres = anime.genres,
                    image = anime.image
                )
            }
        }
    }
}

data class AnimeItem(
    val title: String,
    val rating: String,
    val genres: List<String>,
    val image: Painter
)

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(
        animeList = listOf(
            AnimeItem("Атака титанов", "8.6", listOf("Экшен", "Драма", "Сёнен", "Триллер", "Военное"), painterResource(id = R.drawable.attack_titan)),
            AnimeItem("Клинок, рассекающий демонов", "8.4", listOf("Экшен", "Сёнен", "Фэнтези", "Сверхестественное"), painterResource(id = R.drawable.demon_slayer)),
            AnimeItem("Магическая битва", "8.6", listOf("Экшен", "Сёнен", "Школа", "Сверхестественное"), painterResource(id = R.drawable.jujutsu_kaisen)),
            AnimeItem("Восемьдесят шесть", "8.3", listOf("Экшен", "Военное", "Меха", "Фантастика", "Драма"), painterResource(id = R.drawable.eighty_six)),
            AnimeItem("Мастера меча онлайн", "7.2", listOf("Экшен", "Видеоигры", "Фэнтези", "Романтика", "Приключения"), painterResource(id = R.drawable.sword_online)),
            AnimeItem("Человек-бензопила", "8.5", listOf("Экшен", "Сёнен", "Фэнтези", "Фантастика", "Сверхестественное"), painterResource(id = R.drawable.chainsaw_man)),
            AnimeItem("Иллюзия рая", "8.2", listOf("Сёйнен", "Детектив", "Фантастика", "Приключения"), painterResource(id = R.drawable.tengoku_daimakyou)),
            AnimeItem("Токийские мстители", "7.9", listOf("Экшен", "Драма", "Сёнен", "Мужики дерутся"), painterResource(id = R.drawable.tokyo_revengers)),
            AnimeItem("Бездомный бог", "7.9", listOf("Экшен", "Демоны", "Сёнен", "Сверхестественное"), painterResource(id = R.drawable.noragami)),
            AnimeItem("Вайолет Эвергарден", "8.7", listOf("Драма", "Военное", "Романтика"), painterResource(id = R.drawable.violet_evergarden))
        )
    )
}
