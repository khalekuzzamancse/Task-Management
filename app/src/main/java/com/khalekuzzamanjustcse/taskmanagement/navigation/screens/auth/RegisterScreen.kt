package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.PasswordVisualTransformation
import com.khalekuzzamanjustcse.taskmanagement.data.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data.FirebaseFireStore

class RegisterData {
    var phoneNumber = ""
        private set
    var email = ""
        private set
    var password = ""
        private set
    var name = ""
        private set

    fun onEmailChange(email: String) {
        this.email = email
    }

    fun onPasswordChange(password: String) {
        this.password = password
    }

    fun phoneNumberChange(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    fun onNameChanged(name: String) {
        this.name = name
    }


}


@Composable
fun RegisterScreen(
    scaffoldPadding: PaddingValues,
    onDone:()->Unit={},
) {
    Column(
        modifier = Modifier
            .padding(scaffoldPadding)
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var showPassword by remember {
            mutableStateOf(false)
        }
        val data = remember {
            RegisterData()
        }

        LoginInputField(
            label = "Phone Number",
            leadingIcon = Icons.Default.Person,
            keyboardType = KeyboardType.Number,
            onValueChange = data::phoneNumberChange
        )
        LoginInputField(
            label = "Email",
            leadingIcon = Icons.Default.Person,
            keyboardType = KeyboardType.Email,
            onValueChange = data::onEmailChange
        )
        VerticalSpacer()
        LoginInputField(
            label = "Name",
            leadingIcon = Icons.Default.Person,
            keyboardType = KeyboardType.Text,
            onValueChange = data::onNameChanged
        )
        VerticalSpacer()
        LoginInputField(
            label = "Password",
            leadingIcon = Icons.Default.Lock,
            trailingIcon = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            keyboardType = KeyboardType.Password,
            onValueChange = data::onPasswordChange,
            onTrailingIconClick = { showPassword = !showPassword },
            visualTransformation = if (!showPassword) PasswordVisualTransformation else null
        )
        VerticalSpacer()
        LoginInputField(
            label = "Confirm Password",
            leadingIcon = Icons.Default.Lock,
            trailingIcon = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            keyboardType = KeyboardType.Password,
            onValueChange = {
            },
            onTrailingIconClick = { showPassword = !showPassword },
            visualTransformation = if (!showPassword) PasswordVisualTransformation else null
        )
        LoginButton(Modifier.padding(8.dp)) {
            val auth = AuthManager()
            auth.createAccount(data.email, data.password)
            FirebaseFireStore().addUser(data.email, data.phoneNumber, data.name)
            onDone()
        }
    }


}