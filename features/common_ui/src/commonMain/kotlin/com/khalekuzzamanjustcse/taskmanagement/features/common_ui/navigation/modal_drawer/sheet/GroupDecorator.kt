package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.modal_drawer.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun GroupDecorator(
    modifier: Modifier = Modifier.fillMaxWidth(),
    icon: ImageVector,
    isExpandAble: Boolean = true,
    groupName: String,
    onClick: () -> Unit,
) {

    var backgroundBrush by remember {
        mutableStateOf(
            Brush.linearGradient(
                colors = listOf(
                    Color.Red,
                    Color.Blue
                )
            )
        )
    }

    fun changeBrush() {
        backgroundBrush = when (backgroundBrush) {
            Brush.linearGradient(colors = listOf(Color.Red, Color.Blue)) -> Brush.radialGradient(
                colors = listOf(Color.Green, Color.Yellow)
            )

            Brush.radialGradient(colors = listOf(Color.Green, Color.Yellow)) -> Brush.sweepGradient(
                colors = listOf(Color.Magenta, Color.Green)
            )

            else -> Brush.linearGradient(colors = listOf(Color.Red, Color.Blue))
        }
    }



    Surface(
        modifier = modifier
            .padding(top=2.dp, bottom = 2.dp)
            //   .background(animatedBrush)
            .clickable(onClick = { changeBrush() }),
        shadowElevation = 2.dp
    ) {
        Column {
            Row(
                Modifier.padding(start = 16.dp, end = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, null)
                Spacer(Modifier.width(4.dp))
                Text(text = groupName, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.weight(1f)) // Spacer at the beginnin
                if(isExpandAble){
                    IconButton(
                        onClick = onClick,
                        modifier = Modifier
                    ) {
                        Icon(Icons.Default.ArrowDropDown, null)
                    }
                }

            }
        }

    }

}