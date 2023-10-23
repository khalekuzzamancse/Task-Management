package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.create_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun TaskScreen() {

    val screenState = remember {
        TaskScreenViewModel()
    }
    val showUser = screenState.userSelectedMode.collectAsState().value
    Box(modifier = Modifier.fillMaxSize()) {
        if (showUser) {
            AssignUser(
                modifier = Modifier.matchParentSize(),
                onLongClick = screenState::onLongClick,
                users = screenState.users.collectAsState().value,
                onCrossClick = { screenState.onUserSelectedModeChanged(false) },
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Button(onClick = {
                    screenState.onUserSelectedModeChanged(true)
                }) {
                    Text(text = "Choose User")
                }
                InputField(label = "Title", onValueChange = screenState::onTitleChanged)
                InputFieldArea(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    label = "Description",
                    onValueChange = screenState::onDescriptionChanged
                )
            }
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    label: String,
    trailingIcon: ImageVector? = null,
    visualTransformation: VisualTransformation? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
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
            leadingIcon = null,
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
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            visualTransformation = visualTransformation ?: VisualTransformation.None,
        )
    }
}

@Composable
fun InputFieldArea(
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(text = label)
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
        )
    }
}

data class TaskAssignedUser(
    val name: String,
    val phone: String,
    val selected: Boolean,
)


