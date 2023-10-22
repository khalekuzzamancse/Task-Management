package com.khalekuzzamanjustcse.taskmanagement.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.khalekuzzamanjustcse.taskmanagement.data.AuthManager

@Composable
fun HomePage() {
    var phone by remember {
        mutableStateOf("NULL")
    }
    LaunchedEffect(Unit) {
        phone = AuthManager().signedInUserPhone().toString()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val email = AuthManager().singedInUserEmail()

        Text("Email: ${email.toString()}")
        Text("Phone Number: $phone")

    }
}
