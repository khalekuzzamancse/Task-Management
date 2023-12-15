package com.khalekuzzamanjustcse.taskmanagement.ui_layer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.theme.TaskManagementTheme


@Preview
@Composable
fun CircularProgressBarPreview() {
    TaskManagementTheme {
        Column(modifier=Modifier.fillMaxSize()) {
            CircularProgressBar()
        }
    }


}
@Composable
fun CircularProgressBar(size: Dp =64.dp) {
    CircularProgressIndicator(
        modifier = Modifier.width(size),
        color = MaterialTheme.colorScheme.surfaceVariant,
        trackColor = MaterialTheme.colorScheme.secondary,
    )
}