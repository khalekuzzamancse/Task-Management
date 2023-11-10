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
import com.khalekuzzamanjustcse.taskmanagement.ui.PasswordVisualTransformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow




class FormTextFieldStateManager(
    fieldState: FormTextFieldState,
    private val observeTrailingIconClick: ((FormTextFieldState) -> FormTextFieldState)? = null,
    var validator: ((String) -> String?)? = null
) {
    private val _state = MutableStateFlow(fieldState)
    val state = _state.asStateFlow()
    fun onTextChange(text: String) {
        _state.value = _state.value.copy(text = text)
    }


    fun validate() {
        validator?.let {
            val errorMessage = it(_state.value.text)
            _state.value = _state.value.copy(errorMessage = errorMessage)
        }
    }

    fun onTrailingIconClick() {
        observeTrailingIconClick?.let {
            _state.value = it(_state.value)
        }
    }
}