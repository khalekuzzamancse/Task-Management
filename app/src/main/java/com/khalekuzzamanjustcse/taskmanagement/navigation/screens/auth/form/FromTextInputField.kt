package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.PasswordVisualTransformation


@Preview
@Composable
private fun FormFieldPreview() {
    val containerColor = MaterialTheme.colorScheme.surface
    val inputFieldModifier = Modifier.fillMaxWidth()
    var userNameState by remember {
        mutableStateOf(
            FormTextFieldState(
                label = "User Name",
                containerColor = containerColor,
                leadingIcon = Icons.Filled.Person
            ),
        )
    }
    var passwordState by remember {
        mutableStateOf(
            FormTextFieldState(
                label = "Password",
                containerColor = containerColor,
                leadingIcon = Icons.Filled.Lock,
                trailingIcon = Icons.Filled.Visibility
            ),
        )
    }



    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        FormTextInput(
            modifier = inputFieldModifier,
            state = userNameState,
            onValueChanged = {
                userNameState = userNameState.copy(text = it)
            }

        )
        FormTextInput(
            modifier = inputFieldModifier,
            state = passwordState,
            onValueChanged = {
                passwordState = passwordState.copy(text = it)
            },
            onTrailingIconClick = {
                passwordState =
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

    }

}

@Composable
fun FormTextInput(
    modifier: Modifier,
    state: FormTextFieldState,
    onValueChanged: (String) -> Unit,
    onTrailingIconClick: () -> Unit = {}
) {
    val containerColor = state.containerColor
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text = state.label)
        TextField(
            modifier = modifier,
            singleLine = state.singleLine,
            value = state.text,
            onValueChange = onValueChanged,
            leadingIcon = formInputFieldIcon(icon = state.leadingIcon),

            trailingIcon = formInputFieldIcon(
                icon = state.trailingIcon,
                onClick = onTrailingIconClick,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = state.keyboardType),
            visualTransformation = state.visualTransformation,
            colors = if (containerColor != null)
                TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor
                ) else TextFieldDefaults.colors(),
            isError = state.errorMessage != null,
            supportingText = {
                state.errorMessage?.let {
                    Text(
                        text = it
                    )
                }
            }
        )
    }
}

@Composable
private fun formInputFieldIcon(
    icon: ImageVector?, onClick: (() -> Unit)? = null,
): @Composable (() -> Unit)? {
    if (icon != null)
        return {
            if (onClick != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onClick()
                    }
                )
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                )
            }
        }
    else return null
}



data class FormTextFieldState(
    val singleLine: Boolean = true,
    val text: String = "",
    val errorMessage: String? = null,
    val label: String,
    val leadingIcon: ImageVector? = null,
    val trailingIcon: ImageVector? = null,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val containerColor: Color? = null
)
