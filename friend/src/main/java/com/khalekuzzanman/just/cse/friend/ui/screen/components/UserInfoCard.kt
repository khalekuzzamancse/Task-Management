package com.khalekuzzanman.just.cse.friend.ui.screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserInfoCard(
    modifier: Modifier = Modifier,
    name: String,
    phone: String,
    selected: Boolean = false,
    savedInContact: Boolean,
    onLongClick: () -> Unit = { },
    endExtraContent: @Composable() (RowScope.() -> Unit) = {},
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
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
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
                    text = if(savedInContact)"$phone(saved)" else phone,
                    style = MaterialTheme.typography.labelSmall
                )

            }
            Spacer(modifier = Modifier.weight(1f)) // U
            endExtraContent()
        }

    }

}

@Composable
fun ProfileImage(
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceTint)
            .padding(8.dp)
            .clickable {
                onClick()
            },
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