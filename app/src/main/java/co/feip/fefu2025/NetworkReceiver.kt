package co.feip.fefu2025

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

class NetworkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            val isConnected =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false

            if (isConnected) {
                Log.d("NetworkReceiver", "Интернет подключен")
            } else {
                Log.d("NetworkReceiver", "Интернет отключен")
            }
        }
    }
}