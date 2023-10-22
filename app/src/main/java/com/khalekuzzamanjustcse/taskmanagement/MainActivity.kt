package com.khalekuzzamanjustcse.taskmanagement

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.khalekuzzamanjustcse.taskmanagement.data.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data.FirebaseFireStore
import com.khalekuzzamanjustcse.taskmanagement.data.UserCollections
import com.khalekuzzamanjustcse.taskmanagement.navigation.navgraph.NavGraph
import com.khalekuzzamanjustcse.taskmanagement.ui.theme.TaskManagementTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementTheme {
                val context = LocalContext.current
                createNotification(context, message = "Hello,The App is running")
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


        //

        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val email=AuthManager().singedInUserEmail()
            Log.i("PhoneNumberFound:Email",email.toString())
            val phone= FirebaseFireStore().getUserPhoneNumber(email)
            Log.i("PhoneNumberFound:Phone", phone.toString())
            Log.i("AllUserList","${UserCollections().allUsers()}")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
