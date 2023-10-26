package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.create_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui.components.BasicTextEditor
import com.khalekuzzamanjustcse.taskmanagement.ui.components.ProfileImage


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TaskScreen(
    onBackArrowClick: () -> Unit = {}
) {
    val screenState = remember {
        TaskScreenViewModel()
    }
    val showUser = screenState.userSelectedMode.collectAsState().value
    val title = if (showUser) "Choose Users" else "New Task"
    val onNavigationIconClick: () -> Unit = {
        if (showUser) {
            screenState.onUserSelectedModeChanged(false)
        } else {
            onBackArrowClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationIconClick) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    if (!showUser) {
                        ProfileImage(onClick = {
                            screenState.onUserSelectedModeChanged(true)
                        })
                    }
                },
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (!showUser){
                Button(onClick = { screenState.onDone() }) {
                    Text(text = "Create New  Task")
                }
            }

        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (showUser) {
                AssignUser(
                    modifier = Modifier.matchParentSize(),
                    onLongClick = screenState::onLongClick,
                    users = screenState.users.collectAsState().value,
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                    }

                    BasicTextEditor(
                        text = screenState.title.collectAsState().value,
                        label = "Title", onValueChanged = screenState::onTitleChanged
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextEditor(
                        text = screenState.description.collectAsState().value,
                        label = "Description", onValueChanged = screenState::onDescriptionChanged,
                        singleLine = false
                    )

                }
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


