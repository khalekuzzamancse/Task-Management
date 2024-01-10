package com.khalekuzzamanjustcse.taskmanagement.features.task_graph.calender_view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun Day(
    day: Int,
    badgeCount: Int,
    visibilityDelay: Long,
    onDayClick: (day: Int) -> Unit = {},
) {
    val primary = MaterialTheme.colorScheme.primary
    var color by remember {
        mutableStateOf(primary)
    }
    var textColor by remember(color) {
        mutableStateOf(if (isColorDark(color)) Color.White else Color.Black)
    }
    var visible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        delay(visibilityDelay)
        visible = true
    }

    LaunchedEffect(color) {
        delay(5000)
        if (badgeCount > 0) {
            color = Color(
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255),

                )
            textColor = if (isColorDark(color)) Color.White else Color.Black

        }

    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Surface(
            shadowElevation = 8.dp,
            modifier = Modifier
                .size(55.dp)
                .clip(RoundedCornerShape(4.dp))
                .clickable {
                    onDayClick(day)
                },
            color = color
        ) {
            BadgedBox(
                modifier = Modifier.padding(8.dp).size(50.dp),
                badge = {
                    if (badgeCount > 0) {
                        Badge {
                            Text(text = "$badgeCount")
                        }
                    }

                }
            ) {
                Text(
                    text = "$day",
                    color = textColor
                )
            }


        }
    }


}
fun isColorDark(color: Color): Boolean {
    val luminance = 0.299 * color.red + 0.587 * color.green + 0.114 * color.blue
    return luminance < 0.5
}