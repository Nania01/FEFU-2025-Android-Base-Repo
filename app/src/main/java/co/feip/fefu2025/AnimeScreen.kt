package co.feip.fefu2025

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimeScreen(
    image: Painter,
    title: String,
    info: String,
    genres: List<String>,
    episodesInfo: String,
    rating: String,
    description: String
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
                painter = image,
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
                title.length <= 10 -> 30.sp
                title.length <= 15 -> 26.sp
                title.length <= 25 -> 22.sp
                else -> 20.sp
            }

            Text(
                text = title,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 4.dp)
            )

            Text(
                text = info,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )

            GenreTagsFull(genres = genres)

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
                    value = episodesInfo,
                    backgroundColor = Color(0xFFE0F2F1),
                    modifier = Modifier.weight(1f)
                )
                StatBlock(
                    label = "Рейтинг аниме",
                    value = "$rating из 10",
                    backgroundColor = Color(0xFFFFF4E1),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            DescriptionBlock(description = description)

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

@Preview(showBackground = true)
@Composable
fun PreviewAnimeScreen() {
    AnimeScreen(
        image = painterResource(id = R.drawable.monologue_pharmacist),
        title = "Монолог фармацевта",
        info = "2023, OLM Inc. (Oriental Light and Magic)",
        genres = listOf(
            "Драма", "Детектив", "Медицина", "Романтика",
            "История", "Фэнтези", "Повседневность", "Сёнэн"
        ),
        episodesInfo = "1 сезон, 12 серий",
        rating = "8.8",
        description = "Уже полгода прошло с того момента, как 17-летнюю Маомао похитили и заставили трудиться в императорском дворце обычной служанкой. Работа тяжёлая, но девушка решила не сдаваться, не унывать и честно вкалывать, пока её не отпустят на покой. Планы изменились, когда до Маомао дошли вести о том, что детей императора одолел серьёзный недуг. Девушка решила тайком попробовать разобраться и помочь, рассчитывая на свой опыт в фармацевтике, которой она занималась раньше, когда проживала в Квартале красных фонарей.\n\nНесмотря на то, что Маомао не хотела привлекать к себе внимания, её вмешательство и талант не остались незамеченными. Вскоре Маомао оказалась вхожа во внутренние покои и вступила в круг приближённых императора. Благодаря своим знаниям и эксцентричному характеру Маомао произведёт фурор во дворце!"
    )
}