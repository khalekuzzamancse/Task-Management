package com.khalekuzzamanjustcse.taskmanagement.features.task.create_task

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.form.LabelLessTextField
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.form.LabelLessTextFieldProperties
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.form.LabelLessTextFieldState

@Composable
fun TaskInputField(
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector? = null,
    visualTransformation: VisualTransformation? = null,
    onTrailingIconClicked: (() -> Unit)? = null,
    state: LabelLessTextFieldState,
    leadingIcon: ImageVector?=null,
    onValueChanged: (String) -> Unit,
) {
    LabelLessTextField(
        modifier = modifier,
        properties = LabelLessTextFieldProperties(
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Unspecified,
                unfocusedIndicatorColor = Color.Unspecified
            )
        ),
        state = state,
        onValueChanged = onValueChanged,
        onTrailingIconClick = onTrailingIconClicked
    )
}
