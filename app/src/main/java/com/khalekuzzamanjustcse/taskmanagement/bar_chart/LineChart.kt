package com.khalekuzzamanjustcse.taskmanagement.bar_chart

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement._2d_graph.Value
import com.khalekuzzamanjustcse.taskmanagement._2d_graph.XAxisLineArrow
import com.khalekuzzamanjustcse.taskmanagement._2d_graph.YAxisLineArrow

@Composable
fun BarChart() {
    val valueSize = 16.dp
    val numberModifier = Modifier.sizeIn(minWidth = valueSize, minHeight = valueSize)
    val pointsComposable = remember {
        listOf(
            Bar(x = 1, y = 1) {
                BarComponent(
                    visibilityDelay = 100
                )
            },
            Bar(x = 2, y = 4) {
                BarComponent(
                    visibilityDelay = 200
                )
            },
            Bar(x = 3, y = 3) {
                BarComponent(
                    visibilityDelay = 300
                )
            },
            Bar(x = 4, y = 5) {
                BarComponent(
                    visibilityDelay = 400
                )
            },

            )
    }

    _2DBarPlane(
        modifier = Modifier
            .padding(start = 8.dp, bottom = 8.dp, end = 8.dp, top = 16.dp)
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState()),
        xAxisNumbers = {
            Value(label = "January", modifier = numberModifier)
            Value(label = "February", modifier = numberModifier)
            Value(label = "March", modifier = numberModifier)
            Value(label = "April", modifier = numberModifier)
            Value(label = "May", modifier = numberModifier)
            Value(label = "June", modifier = numberModifier)
            Value(label = "July", modifier = numberModifier)
            Value(label = "August", modifier = numberModifier)
            Value(label = "September", modifier = numberModifier)
            Value(label = "October", modifier = numberModifier)
            Value(label = "November", modifier = numberModifier)
            Value(label = "December", modifier = numberModifier)

        },
        gap = 10.dp,
        yAxisNumbers = {
            for (i in 1..10) {
                Value(label = "$i", modifier = numberModifier)
            }
        },
        yAxisLine = {
            YAxisLineArrow(
                modifier = Modifier
                    .width(2.dp)
            )

        },
        xAxisLine = {
            XAxisLineArrow(modifier = Modifier.height(2.dp))
        },
        xAxisLabel = {
            Text("Month")
        },
        yAxisLabel = {
            Text("Task")
        },
        bars = pointsComposable,
    )

}


