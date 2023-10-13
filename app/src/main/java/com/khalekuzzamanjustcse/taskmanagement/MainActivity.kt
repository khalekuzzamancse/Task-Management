package com.khalekuzzamanjustcse.taskmanagement

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.khalekuzzamanjustcse.taskmanagement.navigation.NavGraph
import com.khalekuzzamanjustcse.taskmanagement.ui.theme.LoginScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.theme.TaskManagementTheme

class MainActivity : ComponentActivity() {
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementTheme {
                NavGraph()
            }
        }
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                runOnUiThread {
                    Toast
                        .makeText(
                        this@MainActivity,
                        "Connected",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onLost(network: Network) {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Disconnected",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
    override fun onDestroy() {
        super.onDestroy()
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}


