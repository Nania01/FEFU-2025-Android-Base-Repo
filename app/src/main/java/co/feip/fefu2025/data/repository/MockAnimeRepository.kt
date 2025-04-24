package co.feip.fefu2025.data.repository

import co.feip.fefu2025.R
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

class MockAnimeRepository : AnimeRepository {

    private val globalRecommendationIds = listOf(2, 3, 4, 5, 6, 7, 8, 9)

    fun getGlobalRecommendations(): List<Anime> {
        return animeList.filter { it.id in globalRecommendationIds }
    }

    private val animeList = listOf(
        Anime(
            id = 1,
            title = "Монолог фармацевта",
            rating = "8.8",
            genres = listOf("Драма", "Детектив", "Медицина", "Романтика", "История", "Повседневность", "Сёнэн"),
            imageResId = R.drawable.monologue_pharmacist,
            info = "2023, OLM Inc. (Oriental Light and Magic)",
            episodesInfo = "1 сезон, 12 серий",
            description = "Уже полгода прошло с того момента, как 17-летнюю Маомао похитили и заставили трудиться в императорском дворце обычной служанкой. Работа тяжёлая, но девушка решила не сдаваться, не унывать и честно вкалывать, пока её не отпустят на покой. Планы изменились, когда до Маомао дошли вести о том, что детей императора одолел серьёзный недуг. Девушка решила тайком попробовать разобраться и помочь, рассчитывая на свой опыт в фармацевтике, которой она занималась раньше, когда проживала в Квартале красных фонарей. Несмотря на то, что Маомао не хотела привлекать к себе внимания, её вмешательство и талант не остались незамеченными. Вскоре Маомао оказалась вхожа во внутренние покои и вступила в круг приближённых императора. Благодаря своим знаниям и эксцентричному характеру Маомао произведёт фурор во дворце!",
            ratings = mapOf(1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 300, 6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 500),
        ),
        Anime(
            id = 2,
            title = "Атака титанов",
            rating = "8.6",
            genres = listOf("Экшен", "Драма", "Сёнен", "Триллер", "Военное"),
            imageResId = R.drawable.attack_titan,
            info = "2013-2023, Wit Studio",
            episodesInfo = "4 сезона, 75 серий",
            description = "Уже многие годы человечество ведёт борьбу с титанами — огромными существами, которые не обладают особым интеллектом, зато едят людей и получают от этого удовольствие. После продолжительной борьбы остатки человечества построили высокую стену, окружившую страну людей, через которую титаны пройти не могли. С тех пор прошло сто лет, люди мирно живут под защитой стены. Но однажды подростки Эрен и Микаса становятся свидетелями страшного события — участок стены разрушается супертитаном, появившимся прямо из воздуха. Титаны нападают на город, и дети в ужасе видят, как один из монстров заживо съедает мать Эрена. Мальчик клянётся, что убьёт всех титанов и отомстит за человечество.",
            ratings = mapOf(1 to 5, 2 to 3, 3 to 8, 4 to 12, 5 to 25, 6 to 40, 7 to 75, 8 to 120, 9 to 180, 10 to 230),
        ),
        Anime(
            id = 3,
            title = "Клинок, рассекающий демонов",
            rating = "8.4",
            genres = listOf("Экшен", "Сёнен", "Фэнтези", "Сверхестественное"),
            imageResId = R.drawable.demon_slayer,
            info = "2019, ufotable",
            episodesInfo = "3 сезона",
            description = "Описание...",
        ),
        Anime(
            id = 4,
            title = "Магическая битва",
            rating = "8.6",
            genres = listOf("Экшен", "Сёнен", "Школа", "Сверхестественное"),
            imageResId = R.drawable.jujutsu_kaisen,
            info = "2020, MAPPA",
            episodesInfo = "2 сезона",
            description = "Описание...",
        ),
        Anime(
            id = 5,
            title = "Восемьдесят шесть",
            rating = "8.3",
            genres = listOf("Экшен", "Военное", "Меха", "Фантастика", "Драма"),
            imageResId = R.drawable.eighty_six,
            info = "2021, A-1 Pictures",
            episodesInfo = "2 сезона",
            description = "Описание...",
        ),
        Anime(
            id = 6,
            title = "Мастера меча онлайн",
            rating = "7.2",
            genres = listOf("Экшен", "Видеоигры", "Фэнтези", "Романтика", "Приключения", "ОПАААААА"),
            imageResId = R.drawable.sword_online,
            info = "2012, A-1 Pictures",
            episodesInfo = "много сезонов",
            description = "Описание...",
        ),
        Anime(
            id = 7,
            title = "Человек-бензопила",
            rating = "8.5",
            genres = listOf("Экшен", "Сёнен", "Фэнтези", "Фантастика", "Сверхестественное"),
            imageResId = R.drawable.chainsaw_man,
            info = "2022, MAPPA",
            episodesInfo = "1 сезон",
            description = "Описание...",
        ),
        Anime(
            id = 8,
            title = "Иллюзия рая",
            rating = "8.2",
            genres = listOf("Сёйнен", "Детектив", "Фантастика", "Приключения"),
            imageResId = R.drawable.tengoku_daimakyou,
            info = "2023, Production I.G",
            episodesInfo = "1 сезон",
            description = "Описание...",
        ),
        Anime(
            id = 9,
            title = "Токийские мстители",
            rating = "7.9",
            genres = listOf("Экшен", "Драма", "Сёнен", "Мужики дерутся"),
            imageResId = R.drawable.tokyo_revengers,
            info = "2021, LIDENFILMS",
            episodesInfo = "3 сезона",
            description = "Описание...",
        ),
        Anime(
            id = 10,
            title = "Бездомный бог",
            rating = "7.9",
            genres = listOf("Экшен", "Демоны", "Сёнен", "Сверхестественное"),
            imageResId = R.drawable.noragami,
            info = "2014, Bones",
            episodesInfo = "2 сезона",
            description = "Описание...",
        ),
        Anime(
            id = 11,
            title = "Ветролом",
            rating = "7.3",
            genres = listOf("Экшен", "Драма", "Сёнен", "Мужики дерутся"),
            imageResId = R.drawable.wind_breaker,
            info = "2014, Bones",
            episodesInfo = "2 сезона",
            description = "Описание...",
        ),
        Anime(
            id = 12,
            title = "Вайолет Эвергарден",
            rating = "8.7",
            genres = listOf("Драма", "Военное", "Романтика"),
            imageResId = R.drawable.violet_evergarden,
            info = "2018, Kyoto Animation",
            episodesInfo = "1 сезон + фильм",
            description = "Описание...",
        )
    )

    override suspend fun getAnimeList(): List<Anime> {
        delay(3000)
        if (Random.nextBoolean()) {
            throw RuntimeException("Ошибка загрузки списка аниме")
        }
        return animeList
    }

    override suspend fun getAnimeById(id: Int): Anime? {
        delay(2000)
        val anime = animeList.find { it.id == id }

        val recommendations = globalRecommendationIds
            .filter { it != id }
            .mapNotNull { rid -> animeList.find { it.id == rid } }
            .take(10)

        if (Random.nextBoolean()) {
            throw RuntimeException("Ошибка загрузки деталей аниме")
        }

        return anime?.copy(recommendations = recommendations)
    }
}