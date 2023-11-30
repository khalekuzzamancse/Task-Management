package com.khalekuzzamanjustcse.taskmanagement.ui_layer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.theme.TaskManagementTheme


@Composable
fun BasicTextEditor(
    text: String,
    label: String,
    singleLine: Boolean = true,
    onValueChanged: (String) -> Unit,

) {

    TaskManagementTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {

          
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = onValueChanged,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                ),
                label = {
                    Text(label)
                },
                singleLine = singleLine,
                shape = MaterialTheme.shapes.medium
            )

        }

    }

}