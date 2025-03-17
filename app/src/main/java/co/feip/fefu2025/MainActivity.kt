package co.feip.fefu2025

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val genres = listOf(
        "Комедия" to Color.parseColor("#F44336"),
        "Повседневность" to Color.parseColor("#E91E63"),
        "Музыка" to Color.parseColor("#9C27B0"),
        "Школа" to Color.parseColor("#3F51B5"),
        "Дружба" to Color.parseColor("#2196F3"),
        "Фэнтези" to Color.parseColor("#009688"),
        "История" to Color.parseColor("#4CAF50"),
        "Приключения" to Color.parseColor("#FF9800"),
        "Романтика" to Color.parseColor("#FF5722"),
        "Ужасы" to Color.parseColor("#795548")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addButton = findViewById<Button>(R.id.addButton)
        val flexLayout = findViewById<FlexBoxLayout>(R.id.flexLayout)

        addButton.setOnClickListener {
            val (name, color) = genres.random()
            val genreView = AnimeGenreView(this)
            genreView.setGenreName(name)
            genreView.setGenreBackgroundColor(color)
            flexLayout.addView(genreView)
        }
    }
}