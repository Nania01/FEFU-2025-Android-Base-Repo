package co.feip.fefu2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FEFU2025AndroidBaseRepoTheme {
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
        }
    }
}
