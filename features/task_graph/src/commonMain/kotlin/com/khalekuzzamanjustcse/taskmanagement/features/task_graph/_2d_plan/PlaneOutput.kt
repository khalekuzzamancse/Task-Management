package com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.features.task_graph.XAxisLineArrow
import com.khalekuzzamanjustcse.taskmanagement.features.task_graph.YAxisLineArrow

@Composable
fun _2dPlaneOutput() {
    val valueSize = 16.dp
    val numberModifier = Modifier
        .sizeIn(minWidth = valueSize, minHeight = valueSize)
        .background(Color.Green)


  _2DPlane(
        modifier = Modifier.padding(16.dp),
        xAxisNumbers = {
            for (i in 1..8) {
                Value(label = "$i", modifier = numberModifier)
            }
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
            Text("X")
        },
        yAxisLabel = {
            Text("Y")
        },
    )

}


@Composable
fun Value(label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = label)
    }


}
