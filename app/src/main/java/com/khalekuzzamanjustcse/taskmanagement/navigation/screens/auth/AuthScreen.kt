package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.CommonScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen() {
    val viewModel = remember {
        AuthViewModel()
    }
    val registrationMode = viewModel.openRegistrationFrom.collectAsState().value
    if (!registrationMode) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "User Login") },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.Menu, null)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                )
            }
        ) { scaffoldPadding ->
            LoginScreen(
                scaffoldPadding = scaffoldPadding,
                onRegisterButtonClicked = viewModel::onRegistrationRequest
            )
        }
    } else {
        CommonScreen(
            title = "Registration Form",
            onBackArrowClick = viewModel::onRegistrationDone,
            false
        ) {
            RegisterScreen(
                scaffoldPadding = it,
                onDone = viewModel::onRegistrationDone
            )
        }
    }
}