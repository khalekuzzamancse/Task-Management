package com.khalekuzzamanjustcse.taskmanagement.features.task.create_task

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CreateTaskScreen() {
    val scope = rememberCoroutineScope()
    val viewModel = remember {
        CreateTaskViewModel(
            onShowToast = {},
            scope = scope
        )
    }
    CreateTaskScreen(
        assignedUsers = viewModel.users.collectAsState().value,
        userSelectionMode = viewModel.userSelectedMode.collectAsState().value,
        onUserSelected = viewModel::onUserChoose,
        onCreateTaskRequest = viewModel::onCreateTaskRequest,
        onCreateTaskConfirm = viewModel::onCreateTaskConfirm,
        onCreateTaskCancel = viewModel::onCreateTaskCancel,
        formState = viewModel.formState
    )

}


@Composable
fun CreateTaskScreen(
    assignedUsers: List<TaskAssignedUser> = taskAssignedUserList,
    onUserSelected:(Int)->Unit={},
    userSelectionMode: Boolean = false,
    onCreateTaskRequest: () -> Unit = {},
    onCreateTaskConfirm: () -> Unit = {},
    formState: CreateTaskFormState,
    onCreateTaskCancel: () -> Unit = {},
) {
//    val isHorizontal = calculateWindowSizeClass().widthSizeClass != WindowWidthSizeClass.Compact

    if (userSelectionMode) {
        AssignUser(
            modifier = Modifier,
            onUserChosen = onUserSelected,
            users = assignedUsers,
            onUserChooseFinished = onCreateTaskConfirm
        )
    } else {
        FormSection(
            formState = formState,
            onCreateTaskRequest = {
                onCreateTaskRequest()
            }
        )
    }


}

@Composable
fun FormSection(
    formState: CreateTaskFormState,
    onCreateTaskRequest: () -> Unit,

    ) {
    //    val isHorizontal = calculateWindowSizeClass().widthSizeClass != WindowWidthSizeClass.Compact

    val isHorizontal = false
    Scaffold(
        floatingActionButton = {
            Button(onClick = {
                onCreateTaskRequest()
            }) {
                Text(text = "Create New  Task")
            }
        }
    ) { scaffoldPadding ->
        CreateTaskForm(
            formModifier = Modifier
                .padding(scaffoldPadding)
                .padding(8.dp)
                .fillMaxWidth(),
            isHorizontal = isHorizontal,
            title = formState.titleState.collectAsState().value,
            onTitleChanged = formState::onTitleChanged,
            descriptionState = formState.descriptionState.collectAsState().value,
            onDescriptionState = formState::onDescriptionChanged,
            timePickerState = formState.dueTimeState.collectAsState().value,
            onDueDateChanged = formState::onDueDateChanged,
            onDueTimeUpdate = formState::onDueTimeChanged,
        )
    }

}



