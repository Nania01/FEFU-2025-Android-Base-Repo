package co.feip.fefu2025

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private lateinit var counterTextView: TextView
    private lateinit var networkReceiver: NetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        counterTextView = findViewById(R.id.counterTextView)

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter", 0)
            counterTextView.text = counter.toString()
        }

        counterTextView.setOnClickListener {
            counter++
            counterTextView.text = counter.toString()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        networkReceiver = NetworkReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)
        Log.d("MainActivity", "BroadcastReceiver зарегистрирован")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter", counter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
        Log.d("MainActivity", "BroadcastReceiver отключен")
    }
}