package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> GenericListScreen(
    modifier: Modifier = Modifier,
    items: List<T>,
    itemDecorator: @Composable (Modifier, T) -> Unit,
) {
    // Use a Box to place an invisible Text with the longest name
    // This will determine the width of the LazyColumn

    LazyColumn(
        modifier = modifier,
    ) {
        items(items) { item ->
            itemDecorator(Modifier, item)
        }
    }


}
