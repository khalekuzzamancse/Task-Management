package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

data class FormTextFieldState(
    val value: String = "",
    val errorMessage: String? = null,
)


@Immutable
data class FormTextFieldProperties(
    val shape: Shape?=null,
    val singleLine: Boolean = true,
    val label: String,
    val leadingIcon: ImageVector? = null,
    val trailingIcon: ImageVector? = null,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val colors: TextFieldColors?=null,
    val readOnly: Boolean = false
)


@Composable
fun FormTextInputPreview() {
    val properties = FormTextFieldProperties(
        singleLine = true,
        label = "Username",
        leadingIcon = Icons.Default.Person,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Unspecified,
            unfocusedIndicatorColor =Color.Unspecified,
        )
    )

    FormTextInput(
        properties = properties,
        value = "JohnDoe",
        onValueChanged = {},
        labelFieldLayout = {label,field->
            Column (
                modifier = Modifier.padding(4.dp)
            ){
                label(Modifier)
                Spacer(Modifier.height(2.dp))
                field(Modifier)
            }
        },
        onTrailingIconClick = {}
    )
}


@Composable
fun FormTextInput(
    modifier: Modifier=Modifier,
    properties: FormTextFieldProperties,
    value: String,
    errorMessage: String? = null,
    containerColor: Color? = null,
    onTrailingIconClick: () -> Unit = {},
    onValueChanged: (String) -> Unit,
) {

    FormTextInput(
        modifier = modifier,
        label = properties.label,
        value = value,
        singleLine = properties.singleLine,
        readOnly = properties.readOnly,
        errorMessage = errorMessage,
        leadingIcon = null,
        trailingIcon = null,
        containerColor = containerColor,
        keyboardType = properties.keyboardType,
        visualTransformation = properties.visualTransformation,
        onTrailingIconClick = onTrailingIconClick,
        onValueChanged = onValueChanged,
    )
}

@Composable
fun FormTextInput(
    properties: FormTextFieldProperties,
    value: String,
    errorMessage: String? = null,
    onTrailingIconClick: () -> Unit = {},
    onValueChanged: (String) -> Unit,
    labelFieldLayout: @Composable (@Composable (Modifier) -> Unit, @Composable (Modifier) -> Unit) -> Unit
) {
    FormTextInput(
        shape = properties.shape?:MaterialTheme.shapes.medium,
        color = properties.colors?:TextFieldDefaults.colors(),
        label = properties.label,
        value = value,
        singleLine = properties.singleLine,
        readOnly = properties.readOnly,
        errorMessage = errorMessage,
        leadingIcon = properties.leadingIcon,
        trailingIcon = properties.trailingIcon,
        keyboardType = properties.keyboardType,
        visualTransformation = properties.visualTransformation,
        onTrailingIconClick = onTrailingIconClick,
        onValueChanged = onValueChanged,
        labelFieldLayout=labelFieldLayout
    )
}






@Composable
fun FormTextInput(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    containerColor: Color? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTrailingIconClick: () -> Unit = {},
    onValueChanged: (String) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(text = label)
        TextField(
            modifier = modifier,
            singleLine = singleLine,
            value = value,
            onValueChange = onValueChanged,
            leadingIcon = formInputFieldIcon(icon = leadingIcon),

            trailingIcon = formInputFieldIcon(
                icon = trailingIcon,
                onClick = onTrailingIconClick,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            colors = if (containerColor != null)
                TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor
                ) else TextFieldDefaults.colors(),
            isError = errorMessage != null,
            supportingText = {
                errorMessage?.let {
                    Text(
                        text = it
                    )
                }
            },
            readOnly = readOnly
        )
    }


}


@Composable
fun FormTextInput(
    label: String,
    value: String,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    shape: Shape =MaterialTheme.shapes.medium,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    color:TextFieldColors=TextFieldDefaults.colors(),
    onTrailingIconClick: () -> Unit = {},
    onValueChanged: (String) -> Unit,
    labelFieldLayout: @Composable (@Composable (Modifier) -> Unit, @Composable (Modifier) -> Unit) -> Unit
) {
    labelFieldLayout(
        { mod->
            Text(text = label,modifier = mod)
        },
        {mod->
            if(errorMessage==null){
                TextField(
                    shape=shape,
                    modifier = mod,
                    singleLine = singleLine,
                    value = value,
                    onValueChange = onValueChanged,
                    leadingIcon = formInputFieldIcon(icon = leadingIcon),
                    trailingIcon = formInputFieldIcon(
                        icon = trailingIcon,
                        onClick = onTrailingIconClick,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                    visualTransformation = visualTransformation,
                    colors =color,
                    readOnly = readOnly
                )
            }
            else{
                TextField(
                    shape=shape,
                    modifier = mod,
                    singleLine = singleLine,
                    value = value,
                    onValueChange = onValueChanged,
                    leadingIcon = formInputFieldIcon(icon = leadingIcon),
                    trailingIcon = formInputFieldIcon(
                        icon = trailingIcon,
                        onClick = onTrailingIconClick,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                    visualTransformation = visualTransformation,
                    colors =color,
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
    )
}







@Composable
fun formInputFieldIcon(
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



