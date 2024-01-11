package com.khalekuzzamanjustcse.taskmanagement.features.task_graph.bar_chart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

data class Bar(
    val x: Int,
    val y: Int,
    val content: @Composable () -> Unit,
)

@Composable
fun BarComponent(
    visibilityDelay:Long=100,
    label: String?=null,
) {
    val primary = MaterialTheme.colorScheme.primary
    var color by remember {
        mutableStateOf(primary)
    }

    var visible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        delay(visibilityDelay)
        visible = true
    }
    LaunchedEffect(Unit) {
        while (true){
            delay(1000)
           color= randomDarkColor()
        }
        }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ){
        BoxWithConstraints(
            modifier = Modifier
                .width(16.dp)
                .background(
                    color =color
                ).clickable {
                    color = if (color == Color.Blue) Color.Red else Color.Blue
                },
            contentAlignment = Alignment.Center
        ){
            if (label != null) {
                Text(label)
            }
        }
    }

}
fun randomGradient(): Brush {
    val colors = List(3) {
        randomDarkColor()
    }
    return Brush.linearGradient(colors)

}
fun randomDarkColor(): Color {
    val red = Random.nextInt(255)
    val green = Random.nextInt(255)
    val blue = Random.nextInt(255)
    return Color(red, green,blue)
}
