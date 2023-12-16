package com.khalekuzzanman.just.cse.firebase

import android.app.Application
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.FirebaseApp

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Log.d("Database:Firebase","app")
        //DatabaseCRUD().read()
    }
}