package co.feip.fefu2025

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class AnimeGenreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val tvGenreName: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_anime_genre_item, this, true)
        tvGenreName = findViewById(R.id.tvGenreName)
    }

    fun setGenreName(name: String) {
        tvGenreName.text = name
    }

    fun setGenreBackgroundColor(colorInt: Int) {
        val drawable = ContextCompat.getDrawable(context, R.drawable.bg_genre_item)?.mutate() as? GradientDrawable
        drawable?.setColor(colorInt)
        background = drawable
        tvGenreName.setTextColor(Color.WHITE)
    }
}
