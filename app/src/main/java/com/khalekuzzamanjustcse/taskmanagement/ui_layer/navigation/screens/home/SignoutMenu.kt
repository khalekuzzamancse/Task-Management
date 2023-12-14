package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.ProfileImage
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.theme.TaskManagementTheme

@Preview
@Composable
fun HomeScreenDropDownPreview() {
    TaskManagementTheme {
        HomeScreenDropDown({})
    }
}

@Composable
fun HomeScreenDropDown(
    onLogOutIconClick: () -> Unit
) {
    var showMenu by remember {
        mutableStateOf(false)
    }

    if (!showMenu) {
        ProfileImage(
            onClick = {
                showMenu = true
            }
        )
    } else {

        DropdownMenu(
            modifier = Modifier
                .padding(16.dp),
            expanded = true,
            onDismissRequest = { showMenu = false },


            ) {
            Column(modifier = Modifier) {
                ProfileSection()
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))

                DropdownMenuItem(
                    text = { Text(text = "PROFILE") },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Contacts,
                            contentDescription = null,
                        )
                    },

                )
                Divider(modifier = Modifier.fillMaxWidth())
                DropdownMenuItem(
                    text = { Text(text = "SETTING") },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null,
                        )
                    },
                )
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Cancel,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    },
                    text = {
                        Text(text = "Log out", color = MaterialTheme.colorScheme.error)
                    },
                    onClick = {
                        onLogOutIconClick()
                        showMenu = false
                    }
                )
            }
        }
    }

}

@Composable
fun ProfileSection(
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceTint)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Contact Icon",
                tint = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Md Abul Kalam Azad",
            style = MaterialTheme.typography.titleLarge,

            )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "01967-675643",
            style = MaterialTheme.typography.labelLarge,
        )

    }
}