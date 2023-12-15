package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.data_layer.MyFriend
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.ProfileImage
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.SelectedProfileImage
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.UserInfoCard
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.User


@Composable
fun FriendListScreen(
    viewModel: FriendListScreenViewModel,
    onNavIconClicked: () -> Unit,
) {
    GenericListScreen(
        items = viewModel.users.collectAsState().value,
        isLoading = viewModel.isLoading.collectAsState().value,
        itemContent = { contact ->
            FriendInfoCard(name = contact.name, phone = contact.phone)
        },
        screenTitle = "My Friends",
        onBack = onNavIconClicked,

        )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendInfoCard(
    modifier: Modifier = Modifier,
    name: String,
    phone: String,
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
//        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier,
//                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage()
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = phone,
                    style = MaterialTheme.typography.labelSmall
                )

            }
            Spacer(modifier = Modifier.weight(1f)) // U
        }

    }

}
