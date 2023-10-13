package com.khalekuzzamanjustcse.taskmanagement

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.khalekuzzamanjustcse.taskmanagement.ui.theme.Login
import com.khalekuzzamanjustcse.taskmanagement.ui.theme.TaskManagementTheme

class MainActivity : ComponentActivity() {
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementTheme {
                Login()
               // ContactGet()
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


