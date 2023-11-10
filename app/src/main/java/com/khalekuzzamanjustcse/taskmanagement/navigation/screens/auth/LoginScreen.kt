package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui.PasswordVisualTransformation
import com.khalekuzzamanjustcse.taskmanagement.R
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.form.FieldValidator
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.form.FormManager
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.form.FormTextFieldState
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.form.FormTextFieldStateManager
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.form.FormTextInput


class LoginFormManager(
    containerColor: Color
) : FormManager() {
    private val validator = FieldValidator()
    override val field: List<FormTextFieldStateManager> = listOf(
        FormTextFieldStateManager(
            fieldState = FormTextFieldState(
                label = "User Name",
                leadingIcon = Icons.Filled.Person,
                keyboardType = KeyboardType.Email,
                containerColor = containerColor,
                readOnly = false
            ),
            validator = validator::validateEmail
        ),
        FormTextFieldStateManager(
            fieldState = FormTextFieldState(
                label = "Password",
                leadingIcon = Icons.Filled.Lock,
                trailingIcon = Icons.Filled.Visibility,
                keyboardType = KeyboardType.Password,
                containerColor = containerColor,
                readOnly = false
            ),
            validator = validator::validatePassword,
            observeTrailingIconClick = { passwordState ->
                if (passwordState.visualTransformation == VisualTransformation.None) {
                    passwordState
                        .copy(
                            visualTransformation = PasswordVisualTransformation,
                            trailingIcon = Icons.Filled.Visibility
                        )
                } else {
                    passwordState
                        .copy(
                            visualTransformation = VisualTransformation.None,
                            trailingIcon = Icons.Filled.VisibilityOff
                        )
                }
            }
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun LoginContentPreview(
    onRegisterButtonClicked: () -> Unit,
    onLoginSuccess: () -> Unit={}
) {
    val containerColor = MaterialTheme.colorScheme.surface
    val formManger = remember {
        LoginFormManager(containerColor)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Login") },
                navigationIcon = {
                    IconButton(onClick ={}) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },

                )
        }
    ) {
        LoginScreen(
            scaffoldPadding = it,
            formManger = formManger,
            onLoginButtonClicked = {
                formManger.validate()
                onLoginSuccess()
            },
            onRegisterButtonClicked=onRegisterButtonClicked
        )
    }


}


@Composable
fun LoginScreen(
    scaffoldPadding: PaddingValues = PaddingValues(0.dp),
    formManger: LoginFormManager,
    onLoginButtonClicked: () -> Unit = {},
    onRegisterButtonClicked: () -> Unit,
) {

    val userName = formManger.field[0]
    val password = formManger.field[1]

    val inputFieldModifier = Modifier.fillMaxWidth()
    Column(
        modifier = Modifier
            .padding(scaffoldPadding)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        FormTextInput(
            modifier = inputFieldModifier,
            state = userName.state.collectAsState().value,
            onValueChanged = userName::onTextChange
        )
        VerticalSpacer()
        FormTextInput(
            modifier = inputFieldModifier,
            state = password.state.collectAsState().value,
            onValueChanged = password::onTextChange,
            onTrailingIconClick = password::onTrailingIconClick
        )
        VerticalSpacer()

        ForgetPassword()
        LoginButton(Modifier.padding(8.dp)) {
            onLoginButtonClicked()
        }
        Spacer(modifier = Modifier.height(16.dp))
        OtherSignInOptions()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Or Signup using"
        )
        TextButton(onClick = onRegisterButtonClicked) {
            Text(
                text = "Register"
            )
        }

    }

}


@Composable
private fun OtherSignInOptions(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Or Sign in using"
        )
        VerticalSpacer()
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(

                    painter = painterResource(id = R.drawable.twitter),
                    contentDescription = null,
                    tint = Color.Unspecified

                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }

        }
    }

}


@Composable
private fun ForgetPassword(modifier: Modifier = Modifier) {
    Row {
        Spacer(modifier = modifier.weight(1f))
        Text(text = "Forget Password ?")
    }
}

@Composable
fun LoginButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Text(text = "Login".uppercase())
    }
}

@Composable
fun VerticalSpacer() {
    Spacer(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
    )
}

