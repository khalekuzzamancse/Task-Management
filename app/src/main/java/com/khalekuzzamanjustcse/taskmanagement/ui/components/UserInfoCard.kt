package com.khalekuzzamanjustcse.taskmanagement.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
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
        }

    }

}

@Composable
fun UserInfoCard(
    modifier: Modifier = Modifier,
    name: String,
    phone: String,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        tonalElevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceTint)
                    .padding(8.dp)
                ,
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Contact Icon",
                    tint = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
                )
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
        }

    }

}