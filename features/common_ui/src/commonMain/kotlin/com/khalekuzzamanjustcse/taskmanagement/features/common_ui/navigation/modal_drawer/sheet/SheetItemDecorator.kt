package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.modal_drawer.sheet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.NavigationItem

@Composable
fun <T>ItemDecorator(
    navigationItem: NavigationItem<T>,
    isSelected:Boolean,
    onClick:() -> Unit,
) {
    NavigationDrawerItem(
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
        icon = {
            Icon(
                navigationItem.unFocusedIcon,
                contentDescription = null
            )
        },
        label = {
            Text(
                text = navigationItem.label,


                )
        },
        selected = isSelected ,
        onClick = onClick,
    )

}