package com.khalekuzzamanjustcse.taskmanagement.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class MyTask(
    val tile: String,
    val description: String,
    val assigner: String,
    val status: Boolean = false,
    val timestamp: String = ""
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyTaskCard(
    modifier: Modifier = Modifier,
    title: String,
    assigner: String = "Mr Bean",
    checked: Boolean = false,
    onCheckedChanged: (Boolean) -> Unit,
    onLongClick: () -> Unit = { },
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onLongClick()
            },
        tonalElevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "Assigned by : $assigner",
                    style = MaterialTheme.typography.labelSmall,
                )
            }
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChanged,
                modifier = Modifier.align(Alignment.Top)
            )
        }

    }

}
