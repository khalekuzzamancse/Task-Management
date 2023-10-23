package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.create_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.ScreenWithTopBarTitleBackArrow
import com.khalekuzzamanjustcse.taskmanagement.ui.components.UserInfoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignUser(
    modifier: Modifier = Modifier,
    users: List<TaskAssignedUser> = emptyList(),
    onLongClick: (Int) -> Unit = {},
    onCrossClick: () -> Unit = {},
) {
    ScreenWithTopBarTitleBackArrow(
        title = "Choose Users",
        onBackArrowClick =onCrossClick)
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
