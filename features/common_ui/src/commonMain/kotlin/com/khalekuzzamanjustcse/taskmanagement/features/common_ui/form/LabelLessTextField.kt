package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.form

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

//using null so that to help garbage collect
@Immutable
data class LabelLessTextFieldProperties(
    val shape: Shape? = null,
    val singleLine: Boolean = true,
    val leadingIcon: ImageVector? = null,
    val trailingIcon: ImageVector? = null,
    val visualTransformation: VisualTransformation? = null,
    val keyboardType: KeyboardType? = null,
    val colors: TextFieldColors? = null,
    val readOnly: Boolean = false
)

@Immutable
data class LabelLessTextFieldState(
    val value: String = "",
    val errorMessage: String? = null
)


@Composable
fun LabelLessTextField(
    modifier: Modifier = Modifier,
    state: LabelLessTextFieldState,
    properties: LabelLessTextFieldProperties,
    onValueChanged: (String) -> Unit,
    onTrailingIconClick: (() -> Unit)?
) {
    LabelLessTextField(
        modifier = modifier,
        value = state.value,
        properties = properties,
        errorMessage = state.errorMessage,
        onValueChanged = onValueChanged,
        onTrailingIconClick = onTrailingIconClick
    )

}


@Composable
fun LabelLessTextField(
    modifier: Modifier = Modifier,
    value: String,
    errorMessage: String?,
    properties: LabelLessTextFieldProperties,
    onValueChanged: (String) -> Unit,
    onTrailingIconClick: (() -> Unit)?,
) {
    LabelLessTextField(
        modifier = modifier,
        value = value,
        singleLine = properties.singleLine,
        readOnly = properties.readOnly,
        leadingIcon = properties.leadingIcon,
        trailingIcon = properties.trailingIcon,
        shape = properties.shape ?: MaterialTheme.shapes.medium,
        color = properties.colors ?: TextFieldDefaults.colors(),
        onValueChanged = onValueChanged,
        keyboardType = properties.keyboardType ?: KeyboardType.Text,
        visualTransformation = properties.visualTransformation ?: VisualTransformation.None,
        errorMessage = errorMessage,
        onTrailingIconClick = onTrailingIconClick
    )

}


@Composable
fun LabelLessTextField(
    modifier: Modifier = Modifier,
    value: String,
    singleLine: Boolean,
    readOnly: Boolean,
    errorMessage: String?,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector?,
    shape: Shape,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation,
    color: TextFieldColors = TextFieldDefaults.colors(),
    onValueChanged: (String) -> Unit,
    onTrailingIconClick: (() -> Unit)?,
) {
    if (errorMessage == null) {
        TextField(
            shape = shape,
            modifier = modifier,
            singleLine = singleLine,
            value = value,
            onValueChange = onValueChanged,
            leadingIcon = formInputFieldIcon(icon = leadingIcon),
            trailingIcon = formInputFieldIcon(trailingIcon, onTrailingIconClick),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            colors = color,
            readOnly = readOnly
        )
    } else {
        TextField(
            shape = shape,
            modifier = modifier,
            singleLine = singleLine,
            value = value,
            onValueChange = onValueChanged,
            leadingIcon = formInputFieldIcon(icon = leadingIcon),
            trailingIcon = formInputFieldIcon(trailingIcon, onTrailingIconClick),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            colors = color,
            isError = true,
            supportingText = {
                Text(
                    text = errorMessage
                )
            },
            readOnly = readOnly
        )
    }
}