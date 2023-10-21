package com.khalekuzzamanjustcse.taskmanagement

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.khalekuzzamanjustcse.taskmanagement.navigation.NavGraph
import com.khalekuzzamanjustcse.taskmanagement.ui.theme.TaskManagementTheme

class MainActivity : ComponentActivity() {
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var auth: FirebaseAuth
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


        //
        auth = Firebase.auth

        if (auth.currentUser == null) {
            Log.i("CurrentUser", "NUll")
            //  signIn("khalekuzzaman91@gmail.com", "12345678")
        } else
            Log.i("CurrentUser", "${auth.currentUser!!.email}")




    }

    override fun onDestroy() {
        super.onDestroy()
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}


class FirebaseAuth(
    private val onSignInSuccess: () -> Unit = {},
    private val onSignInFailed: () -> Unit = {},
) {
    private val auth = Firebase.auth
    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                } else {
                }
            }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSignInSuccess()
                    Log.i("CurrentUser: ", "signInWithEmail:success")
                } else {
                    onSignInFailed()
                    Log.i("CurrentUser: ", "signInWithEmail:fail")
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }
}

class FirebaseFireStore {
    private val db = Firebase.firestore
    fun readAllDocs(collectionName: String): List<Contact> {
        val users = mutableListOf<Contact>()
        db.collection(collectionName)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                for (doc in value!!) {

                    val name = doc.getString("Name")
                    val phoneNumber = doc.getString("PhoneNumber")
                    if (name != null && phoneNumber != null) {
                        users.add(Contact(name = name, phone = phoneNumber))
                    }

                }
            }
        return users
    }

    fun addUser(email: String, phone: String, name: String) {
        val user = hashMapOf(
            "PhoneNumber" to phone,
            "Email" to email,
            "Name" to name
        )
        db.collection("Users").document(phone)
            .set(user)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }
}



