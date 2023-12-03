package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.create_task

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.UserInfoCard

@Composable
fun AssignUser(
    modifier: Modifier = Modifier,
    users: List<TaskAssignedUser> = emptyList(),
    isLoading: Boolean = false,
    onLongClick: (Int) -> Unit = {},
    onCrossClick: () -> Unit = {},
) {
    Column(modifier = modifier) {
        users.forEachIndexed { i, it ->
            UserInfoCard(
                name = it.name,
                phone = it.phone,
                selected = it.selected,
                onLongClick = { onLongClick(i) },
                savedInContact = false
            )
        }
    }


}
