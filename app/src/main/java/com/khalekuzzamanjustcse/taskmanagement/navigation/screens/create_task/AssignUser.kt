package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.create_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.CommonScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.components.UserInfoCard

@Composable
fun AssignUser(
    modifier: Modifier = Modifier,
    users: List<TaskAssignedUser> = emptyList(),
    isLoading:Boolean=false,
    onLongClick: (Int) -> Unit = {},
    onCrossClick: () -> Unit = {},
) {
    CommonScreen(
        title = "Choose Users",
        onBackArrowClick =onCrossClick,
        isLoading = isLoading
    )
    { scaffoldPadding ->
        Column(modifier = modifier.padding(scaffoldPadding)) {
            users.forEachIndexed { i, it ->
                UserInfoCard(
                    name = it.name,
                    phone = it.phone,
                    selected = it.selected,
                    onLongClick = { onLongClick(i) }
                )
            }

        }
    }


}
