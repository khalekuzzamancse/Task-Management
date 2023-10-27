package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.PasswordVisualTransformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@Preview
@Composable
private fun FormStatePreview() {
    val containerColor = MaterialTheme.colorScheme.surface
    val inputFieldModifier = Modifier.fillMaxWidth()
    val userName = remember {
        FormTextFieldStateManager(
            fieldState = FormTextFieldState(
                label = "User Name",
                containerColor = containerColor,
                leadingIcon = Icons.Filled.Person
            ),
        )
    }
    val password = remember {
        FormTextFieldStateManager(
            fieldState = FormTextFieldState(
                label = "Password",
                containerColor = containerColor,
                leadingIcon = Icons.Filled.Lock,
                trailingIcon = Icons.Filled.Visibility
            ),
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
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        FormTextInput(
            modifier = inputFieldModifier,
            state = userName.state.collectAsState().value,
            onValueChanged = userName::onTextChange

        )
        FormTextInput(
            modifier = inputFieldModifier,
            state = password.state.collectAsState().value,
            onValueChanged = password::onTextChange,
            onTrailingIconClick = password::onTrailingIconClick
        )
        Button(onClick = {
            userName.validate {text->
            if (text.trim().isEmpty()) "Can not be empty" else null
            }
            password.validate {text->
                if (text.trim().length<6) "Length at least 6" else null
            }
        }) {
            Text(text = "Validate")
        }

    }
}

class FormTextFieldStateManager(
    fieldState: FormTextFieldState,
    private val observeTrailingIconClick: ((FormTextFieldState) -> FormTextFieldState)? = null,
) {
    private val _state = MutableStateFlow(fieldState)
    val state = _state.asStateFlow()
    fun onTextChange(text: String) {
        _state.value = _state.value.copy(text = text)
    }

    fun validate(isValid: (String) -> String?){
        val errorMessage = isValid(_state.value.text)
        _state.value=_state.value.copy(errorMessage=errorMessage)
    }

    fun onTrailingIconClick() {
        observeTrailingIconClick?.let {
            _state.value = it(_state.value)
        }
    }
}