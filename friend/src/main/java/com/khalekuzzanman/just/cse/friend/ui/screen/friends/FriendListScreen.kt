package com.khalekuzzanman.just.cse.friend.ui.screen.friends

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.ProfileImage
import com.khalekuzzanman.just.cse.friend.ui.screen.components.GenericListScreen

data class MyFriend(
    val name: String,
    val phone: String,
    val friendShipId: String = "",
)


@Composable
fun FriendListScreen(
    friends: List<MyFriend>,
    navIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavIconClicked: () -> Unit,
) {
    GenericListScreen(
        items = friends,
        navIcon = navIcon,
        onNavIconClicked = onNavIconClicked,
        screenTitle = "My Friends",
        itemContent = { contact ->
            FriendInfoCard(
                name = contact.name,
                phone = contact.phone
            )
        },
    )

}

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
