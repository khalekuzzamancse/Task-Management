package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.create_task

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Title
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.ProfileImage
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph.ProgressBar
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph.showToast
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.VerticalSpacer
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form.DatePickerNoOutlined
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form.FieldValidator
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form.FormManager
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form.FormTextFieldState
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form.FormTextFieldStateManager
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form.FormTextInput

@Preview
@Composable
fun TaskScreenPreview() {
    val containerColor = MaterialTheme.colorScheme.surface
    val context= LocalContext.current
    val viewModel = remember {
        CreateTaskViewModel(CreateTaskFormManager(containerColor)){msg->
            showToast(context,msg)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        TaskScreen(viewModel)
        ProgressBar()
    }

}

class CreateTaskFormManager(
    containerColor: Color
) : FormManager() {
    private val validator = FieldValidator()
    val title = FormTextFieldStateManager(
        fieldState = FormTextFieldState(
            label = "Title",
            leadingIcon = Icons.Filled.Title,
            containerColor = containerColor,
        ),
        validator = validator::validateEmpty
    )
    val dueDate = FormTextFieldStateManager(
        fieldState = FormTextFieldState(
            label = "Due Date",
            leadingIcon = Icons.Filled.DateRange,
            trailingIcon = Icons.Filled.Edit,
            containerColor = containerColor,
            readOnly = true
        ),
        validator = validator::validateEmpty
    )
    val description = FormTextFieldStateManager(
        fieldState = FormTextFieldState(
            label = "Description",
            containerColor = containerColor,
            singleLine = false
        ),
        validator = validator::validateEmpty
    )
    override val field: List<FormTextFieldStateManager> =
        listOf(title, dueDate, description)
    val titleText: String
        get() = title.state.value.text
    val dueDateText: String
        get() = dueDate.state.value.text
    val descriptionText: String
        get() = description.state.value.text


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    viewModel: CreateTaskViewModel,
    onBackArrowClick: () -> Unit = {},
) {


    val formManager = remember {
        viewModel.formManager
    }
    val inputFieldModifier = Modifier.fillMaxWidth()

    val showUser = viewModel.userSelectedMode.collectAsState().value
    val title = if (showUser) "Choose Users" else "New Task"
    val onNavigationIconClick: () -> Unit = {
        if (showUser) {
            viewModel.onUserSelectedModeChanged(false)
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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    if (!showUser) {
                        ProfileImage(onClick = {
                            viewModel.onUserSelectedModeChanged(true)
                        })
                    }
                },
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (!showUser) {
                Button(onClick = {
                    formManager.validate()
                    if (formManager.isValid()) {
                        viewModel.onDone()
                        val data = formManager.getData()
                        Log.d("FormDataComplete", data.toString())
                    }
                }) {
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
                    onLongClick = viewModel::onLongClick,
                    users = viewModel.users.collectAsState().value,
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                ) {

                    FormTextInput(
                        modifier = inputFieldModifier,
                        state = formManager.title.state.collectAsState().value,
                        onValueChanged = formManager.title::onTextChange
                    )
                    VerticalSpacer()
                    DatePickerNoOutlined(
                        modifier = inputFieldModifier,
                        state = formManager.dueDate.state.collectAsState().value,
                        onDateSelected = formManager.dueDate::onTextChange
                    )
                    VerticalSpacer()
                    FormTextInput(
                        modifier = inputFieldModifier,
                        state = formManager.description.state.collectAsState().value,
                        onValueChanged = formManager.description::onTextChange
                    )
                }
            }
        }

    }

}

data class TaskAssignedUser(
    val name: String,
    val phone: String,
    val selected: Boolean,
)


