package com.khalekuzzanman.just.cse.friend.ui.screen.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CircularProgressBar(size: Dp =64.dp) {
    CircularProgressIndicator(
        modifier = Modifier.width(size),
        color = MaterialTheme.colorScheme.surfaceVariant,
        trackColor = MaterialTheme.colorScheme.secondary,
    )
}