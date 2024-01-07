package com.khalekuzzamanjustcse.taskmanagement.features.common_ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit={},
    query: String,
    onQueryChange: (String) -> Unit={},
    active: Boolean = true,
    onActiveChanged: (Boolean)-> Unit,
    content:@Composable ColumnScope.()->Unit={},
) {


    SearchBar(
        modifier = modifier.fillMaxWidth(),
        query = query,
        onQueryChange =onQueryChange,
        onSearch = {
            onActiveChanged(false)
        },
        active = active,
        onActiveChange = onActiveChanged,
        placeholder = {
            Text(text = "Search in emails")
        },
        shape = RectangleShape,
        leadingIcon = {
           IconButton(
                onClick = {
                    onActiveChanged(false)
                    onGoBack()
                }
            ){
               Icon(
                   imageVector = Icons.Default.ArrowBack,
                   null
               )
           }

        }, trailingIcon = {
            if (query.isNotBlank()) {
               IconButton(
                    onClick = {
                       onQueryChange("")
                    }
                ){
                   Icon(
                       imageVector = Icons.Default.Clear,
                       null
                   )
               }
            }

        },
        content = content
    )
}
