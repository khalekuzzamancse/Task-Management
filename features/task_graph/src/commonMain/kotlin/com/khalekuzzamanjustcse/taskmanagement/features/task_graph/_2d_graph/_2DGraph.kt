package com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_graph

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun _2dGraph() {
    val size = 16.dp
    _2DPlane(
        modifier =Modifier.padding(16.dp) ,
        xAxisNumbers = {
            for (i in 1..8) {
                Text(
                    text = "$i", modifier = Modifier
                        .sizeIn(minWidth = size, minHeight = size)
                )
            }
        },
        gap = 10.dp,
        yAxisNumbers = {
            for (i in 10 downTo 1) {
                Text(
                    text = "$i", modifier = Modifier
                        .sizeIn(minWidth = size, minHeight = size)
                )
            }
        },
        yAxisLine = {
            YAxisBar()
        },
        xAxisLine = {
            XAxisBar()
        },
        points = listOf(
            Point(x = 1, y = 1) {
                CoordinatePoint(size)
            },

        )
    )

}

@Composable
fun CoordinatePoint(
    size: Dp,
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(Color.Blue).clickable { }
    )
}

@Composable
fun YAxisBar() {
    Box(
        modifier = Modifier
            .width(2.dp)
            .background(Color.Black)
    )

}

@Composable
fun XAxisBar() {
    Box(
        modifier = Modifier
            .height(2.dp)
            .background(Color.Black)
    )

}



data class Point(
    val x: Int,
    val y: Int,
    val content: @Composable () -> Unit,
)

@Composable
fun _2DPlane(
    modifier: Modifier = Modifier,
    gap: Dp,
    yAxisNumbers: @Composable () -> Unit,
    yAxisLine: @Composable () -> Unit,
    xAxisLine: @Composable() () -> Unit,
    xAxisNumbers: @Composable () -> Unit,
    points: List<Point>,
) {
    val pointsComposable = @Composable {
        points.forEach {
            it.content()
        }
    }
    val contents = listOf(
        xAxisNumbers, yAxisNumbers, pointsComposable, yAxisLine, xAxisLine
    )

    Layout(
        modifier = modifier,
        contents = contents
    ) { listOfMeasurableList, constraints ->
        val (xAxisNumbersMeasurables, yAxisNumbersMeasurables, pointsMeasurable, yAxisLineMeasurable, xAxisLineMeasurable) = listOfMeasurableList
        val yAxisNumbersPlaceable = yAxisNumbersMeasurables.map { it.measure(constraints) }
        //
        val yAxisNumbersHeight = (yAxisNumbersPlaceable.size - 1) * gap.toPx()
            .toInt() + yAxisNumbersPlaceable.sumOf { it.height }
        val yAxisLinePlaceable = yAxisLineMeasurable
            .first()
            .measure(
                constraints.copy(
                    minHeight = yAxisNumbersHeight,
                    maxHeight = yAxisNumbersHeight
                )
            )
        //pass a single composable simple or complex

        //
        val xAxisNumbersPlaceable = xAxisNumbersMeasurables.map { it.measure(constraints) }
        val xAxisWidth =
            xAxisNumbersPlaceable.sumOf { it.width } + (xAxisNumbersPlaceable.size - 1) * gap.toPx()
                .toInt()

        val xAxisLinePlaceable = xAxisLineMeasurable
            .first()//pass a  signal root level composable
            .measure(constraints.copy(minWidth = xAxisWidth, maxWidth = xAxisWidth))
        //
        val pointsPlaceable = pointsMeasurable.map { it.measure(constraints) }


        //Measuring points

        //
        layout(width = constraints.maxWidth, height = constraints.maxHeight) {
            var totalHeight = 0
            yAxisNumbersPlaceable.forEachIndexed { index, it ->
                val prevComposableHeight = yAxisNumbersPlaceable.getOrNull(index - 1)?.height ?: 0
                totalHeight += prevComposableHeight + if (index > 0) gap.toPx().toInt() else 0
                it.placeRelative(x = 0, y = totalHeight)
            }
            //
            val yAxisMaxWidth = yAxisNumbersPlaceable.maxBy { it.width }.width

            //y axis line
            yAxisLinePlaceable.placeRelative(yAxisMaxWidth, 0)
            //
            var totalWidth = yAxisMaxWidth//offset from x axis
            val xAxisNumberMaxHeight = xAxisNumbersPlaceable.maxBy { it.height }.height
            xAxisNumbersPlaceable.forEachIndexed { index, it ->
                val prevComposableWidth = xAxisNumbersPlaceable.getOrNull(index - 1)?.width ?: 0
                //no gap for the first element of x axis
                totalWidth += prevComposableWidth + if (index > 0) gap.toPx().toInt() else 0
                it.placeRelative(x = totalWidth, y = totalHeight + xAxisNumberMaxHeight)
            }
            //place x axis line
            xAxisLinePlaceable.placeRelative(x = yAxisMaxWidth, y = totalHeight + xAxisNumberMaxHeight)

            //placing point

            pointsPlaceable.forEachIndexed { index, it ->
                val n = yAxisNumbersPlaceable.size - points[index].y
                val coordinateX = points[index].x
                val x = xAxisNumbersPlaceable.take(coordinateX)
                    .sumOf { it.width } + coordinateX * gap.toPx().toInt() + yAxisMaxWidth
                val y = yAxisNumbersPlaceable.take(n).sumOf { it.height } + (n) * gap.toPx()
                    .toInt() + gap.toPx().toInt()
                it.placeRelative(x = x, y = y)


            }

        }
    }
}


