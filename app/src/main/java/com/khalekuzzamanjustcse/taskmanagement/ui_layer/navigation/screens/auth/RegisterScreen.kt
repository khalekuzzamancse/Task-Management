package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.PasswordVisualTransformation
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form.FieldValidator
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form.FormManager
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form.FormTextFieldState
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form.FormTextFieldStateManager
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form.FormTextInput

class RegistrationFormManager(
    containerColor: Color
) : FormManager() {
    private val validator = FieldValidator()

    val email = FormTextFieldStateManager(
        fieldState = FormTextFieldState(
            label = "Email",
            leadingIcon = Icons.Filled.Email,
            keyboardType = KeyboardType.Email,
            containerColor = containerColor,
            readOnly = false
        ),
        validator = validator::validateEmail
    )
    val name = FormTextFieldStateManager(
        fieldState = FormTextFieldState(
            label = "Name",
            leadingIcon = Icons.Filled.Person,
            containerColor = containerColor,
            readOnly = false
        ),
        validator = validator::validateEmpty
    )
    val phone = FormTextFieldStateManager(
        fieldState = FormTextFieldState(
            label = "Phone Number",
            leadingIcon = Icons.Filled.Phone,
            keyboardType = KeyboardType.Number,
            containerColor = containerColor,
            readOnly = false
        ),
        validator = validator::validatePhoneNo
    )
    val password = FormTextFieldStateManager(
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
    val passwordConfirm = FormTextFieldStateManager(
        fieldState = FormTextFieldState(
            label = "Confirm Password",
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
    override val field: List<FormTextFieldStateManager> =
        listOf(email, name, phone, password, passwordConfirm)

}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun RegisterScreenPreview(
    onBackArrowClicked:()->Unit ={}
) {
    val containerColor = MaterialTheme.colorScheme.surface
    val formManger = remember {
        RegistrationFormManager(containerColor)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Registration Form")
                },
                navigationIcon = {
                    IconButton(onClick =onBackArrowClicked) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Button(onClick = {
                formManger.validate()
                if (formManger.isValid()) {
                    formManger.getData()
                }
            }) {
                Text(text = "Complete Register")
            }

        }
    ) {
        Column(
            modifier =
            Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            VerticalSpacer()
            RegisterScreenInputFields(formManger = formManger)
        }
    }


}

@Composable
fun RegisterScreenInputFields(
    scaffoldPadding: PaddingValues = PaddingValues(0.dp),
    formManger: RegistrationFormManager,
) {
    val inputFieldModifier = Modifier.fillMaxWidth()

    Column(
        modifier = Modifier
            .padding(scaffoldPadding)
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FormTextInput(
            modifier = inputFieldModifier,
            state = formManger.email.state.collectAsState().value,
            onValueChanged = formManger.email::onTextChange

        )
        VerticalSpacer()
        FormTextInput(
            modifier = inputFieldModifier,
            state = formManger.name.state.collectAsState().value,
            onValueChanged = formManger.name::onTextChange

        )
        FormTextInput(
            modifier = inputFieldModifier,
            state = formManger.phone.state.collectAsState().value,
            onValueChanged = formManger.phone::onTextChange

        )
        VerticalSpacer()
        FormTextInput(
            modifier = inputFieldModifier,
            state = formManger.password.state.collectAsState().value,
            onValueChanged = formManger.password::onTextChange,
            onTrailingIconClick = formManger.password::onTrailingIconClick
        )
        VerticalSpacer()
        FormTextInput(
            modifier = inputFieldModifier,
            state = formManger.passwordConfirm.state.collectAsState().value,
            onValueChanged = formManger.passwordConfirm::onTextChange,
            onTrailingIconClick = formManger.passwordConfirm::onTrailingIconClick
        )

    }

}