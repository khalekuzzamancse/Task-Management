package com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_graph

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun _2dGraph() {
    val pointSize = 8.dp
    val size2 = 16.dp
    val sizePx = with(LocalDensity.current) { pointSize.toPx() }
    val numberModifier = Modifier.sizeIn(minWidth = size2, minHeight = size2)
    var pointsOffsets by remember { mutableStateOf(emptySet<Offset>()) }
    val onPositioned: (Offset) -> Unit = { offset ->
        val centerOffset = offset + Offset(sizePx / 2, sizePx / 2)
        val tmpList = pointsOffsets.toMutableList()
        tmpList.add(centerOffset)
        pointsOffsets = tmpList.toSet()
    }
    val pointsComposable = remember {
        listOf(
            Point(x = 1, y = 1) {
                CoordinatePoint(
                    size = pointSize, onPositioned = {
                        onPositioned(it)
                    })
            },
            Point(x = 3, y = 3) {
                CoordinatePoint(
                    size = pointSize, onPositioned = {
                        onPositioned(it)
                    })
            },
            Point(x = 5, y = 1) {
                CoordinatePoint(
                    size = pointSize, onPositioned = {
                        onPositioned(it)
                    })
            },
        )
//            Point(x = 4, y = 7) {
//                CoordinatePoint(
//                    size = pointSize, onPositioned = {
//                        onPositioned(it)
//                    })
//            },
    }

    _2DPlane(
        modifier = Modifier.padding(16.dp).drawBehind {
            val path = Path()
            if (pointsOffsets.size == pointsComposable.size) {
                val points = pointsOffsets.toList()
                val startPoint = pointsOffsets.first()
                path.apply {
                    moveTo(startPoint.x, startPoint.y)
                    for (i in 1 until pointsOffsets.size) {
                        addCurveBetweenPoints(
                            path=path,
                            startPoint=points[i-1],
                            endPoint=points[i]
                        )
                    }
                }
                drawThePath(path)


            }
        },
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
            YAxisBar()
        },
        xAxisLine = {
            XAxisBar()
        },
        points = pointsComposable
    )

}
fun DrawScope.drawThePath(path: Path){
    drawPath(
        path = path,
        style = Stroke(2f),
        color = Color.Black

    )
}

fun addCurveBetweenPoints(path: Path, startPoint: Offset, endPoint: Offset) {
    path.apply {
        moveTo(startPoint.x, startPoint.y)
        val control = (startPoint + endPoint) / 2f
        quadraticBezierTo(control.x, control.y, endPoint.x, endPoint.y)
    }
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

@Composable
fun CoordinatePoint(
    size: Dp,
    onPositioned: (Offset) -> Unit
) {
    var color by remember {
        mutableStateOf(Color.Blue)
    }
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color).clickable {
                color = if (color == Color.Blue) Color.Red else Color.Blue
            }
            .onGloballyPositioned {
                onPositioned(it.positionInParent())
            }
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


@Composable
fun _2DPlane(
    modifier: Modifier = Modifier,
    gap: Dp,
    yAxisNumbers: @Composable () -> Unit,
    yAxisLine: @Composable () -> Unit,
    xAxisLine: @Composable () -> Unit,
    xAxisNumbers: @Composable () -> Unit,
    points: List<Point>,
) {
    val density = LocalDensity.current.density
    val gapBetweenNumbersPx = (gap.value * density).toInt()

    val pointsComposable = @Composable {
        points.forEach { point -> point.content() }
    }
    val contents = listOf(xAxisNumbers, yAxisNumbers, pointsComposable, yAxisLine, xAxisLine)
    Layout(modifier = modifier, contents = contents) { listOfMeasurableList, constraints ->
        val (xAxisNumbersMeasurable, yAxisNumbersMeasurable, pointsMeasurable, yAxisLineMeasurable, xAxisLineMeasurable) = listOfMeasurableList
        val measureUtils = MeasureUtils(
            gapBetweenNumbersPx = gapBetweenNumbersPx,
            xAxisNumberMeasurableList = xAxisNumbersMeasurable,
            yAxisNumberMeasurableList = yAxisNumbersMeasurable,
            yAxisBar = yAxisLineMeasurable.first(),
            xAxisBar = xAxisLineMeasurable.first()
        )
        val yAxisNumbersPlaceable = measureUtils.measureYAxisNumbers(constraints)
        val yAxisLinePlaceable = measureUtils.measureYAxisBar(constraints)
        val xAxisNumbersPlaceable = measureUtils.measureXAxisNumbers(constraints)
        val xAxisLinePlaceable = measureUtils.measureXAxisBar(constraints)
        val pointsPlaceable = pointsMeasurable.map { it.measure(constraints) }

        layout(width = constraints.maxWidth, height = constraints.maxHeight, placementBlock
        = {
            val placementUtils = PlacementUtils(
                yAxisNumbersPlaceableList = yAxisNumbersPlaceable,
                xAxisNumbersPlaceableList = xAxisNumbersPlaceable,
                gapBetweenValuePx = gapBetweenNumbersPx
            )
            //placings between
            placementUtils.run {
                placeYCoordinates()
                placeYAxisBar(yAxisLinePlaceable)
                placeXAxisCoordinates()
                placeXAxisBar(xAxisLinePlaceable)
                placePoints(pointsPlaceable = pointsPlaceable, points = points)
            }
        })
    }
}






