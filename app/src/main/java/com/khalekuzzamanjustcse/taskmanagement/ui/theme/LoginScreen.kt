package com.khalekuzzamanjustcse.taskmanagement.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.PasswordVisualTransformation
import com.khalekuzzamanjustcse.taskmanagement.R


/*


Make the Login screen as:
https://tinyurl.com/yx39k9u5

 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginButtonClicked:()->Unit ={},
    onNavIconClicked:()->Unit ={},
) {
    var showPassword by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("User Login")},
                navigationIcon = {
                    IconButton(onClick = onNavIconClicked) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription =null )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),

                )
        }
    ) {innterPadding->
        Column(
            modifier = Modifier
                .padding(innterPadding)
                .fillMaxSize()
                .padding(start=16.dp,end=16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoginInputField(
                label = "Phone Number",
                leadingIcon = Icons.Default.Person,
                keyboardType = KeyboardType.Number,
                onValueChange = {

                })
            VerticalSpacer()
            LoginInputField(
                label = "Password",
                leadingIcon = Icons.Default.Lock,
                trailingIcon = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                keyboardType = KeyboardType.Password,
                onValueChange = {
                },
                onTrailingIconClick = { showPassword = !showPassword },
                visualTransformation = if (!showPassword) PasswordVisualTransformation else null
            )
            VerticalSpacer()
            ForgetPassword()
            LoginButton(Modifier.padding(8.dp)){
                onLoginButtonClicked()
            }
            Spacer(modifier = Modifier.height(16.dp))
            OtherSignInOptions()


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
private fun LoginButton(modifier: Modifier = Modifier,onClick: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick =onClick) {
        Text(text = "Login".uppercase())
    }
}

@Composable
private fun VerticalSpacer() {
    Spacer(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun LoginInputField(
    modifier: Modifier = Modifier,
    label: String,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector? = null,
    visualTransformation: VisualTransformation? = null,
    keyboardType: KeyboardType=KeyboardType.Text,
    onTrailingIconClick: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text = label)

        TextField(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
            leadingIcon = {
                Icon(imageVector = leadingIcon, contentDescription = null)
            },
            trailingIcon = {
                trailingIcon?.let {
                    Icon(
                        modifier = Modifier.clickable {
                            onTrailingIconClick()
                        },
                        imageVector = trailingIcon,
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType=keyboardType),
            visualTransformation = visualTransformation ?: VisualTransformation.None,
        )
    }
}
