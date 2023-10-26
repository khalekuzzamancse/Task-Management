package com.khalekuzzamanjustcse.taskmanagement.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui.theme.TaskManagementTheme

@Preview
@Composable
fun UserInfoCardPreview() {
    TaskManagementTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            UserInfoCard(name = "Mr Bean", phone = "01738813865")
            UserInfoCard(name = "Mr Azad Ali", phone = "01738813865")
            UserInfoCard(name = "Mr Bean Karim", phone = "01738813865")
            UserInfoCard(name = "Mr Bean Karim", phone = "01738813865", selected = true)
            UserInfoCard(name = "Mr Bean Karim", phone = "01738813865") {
                IconButton(
                    onClick = { },
                ) {
                    Icon(imageVector = Icons.Filled.PersonAdd, contentDescription = null)
                }
            }
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserInfoCard(
    modifier: Modifier = Modifier,
    name: String,
    phone: String,
    selected: Boolean = false,
    onLongClick: () -> Unit = { },
    endExtraContent: @Composable RowScope.() -> Unit = {},
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = onLongClick
            ) {

            },
//        shape = MaterialTheme.shapes.medium,
        color =if(selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier,
//                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selected) {
                SelectedProfileImage()

            } else {
                ProfileImage()

            }

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
            endExtraContent()
        }

    }

}

@Composable
fun ProfileImage() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceTint)
            .padding(8.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Contact Icon",
            tint = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
        )
    }

}

@Composable
fun SelectedProfileImage(modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Icon(
            Icons.Default.Check,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Center),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}