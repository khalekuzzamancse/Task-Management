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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun _2dGraph() {
    val size = 8.dp
    val size2 = 16.dp
    val numberModifier = Modifier.sizeIn(minWidth = size2, minHeight = size2).background(
        Color.Green
    )
    _2DPlane(
        modifier = Modifier.padding(16.dp),
        xAxisNumbers = {
            for (i in 1..8) {
                Value(label = "$i",modifier = numberModifier)
            }
        },
        gap = 10.dp,
        yAxisNumbers = {
            for (i in 1..10) {
                Value(label = "$i",modifier = numberModifier)
            }
        },
        yAxisLine = {
            YAxisBar()
        },
        xAxisLine = {
            XAxisBar()
        },
        points = listOf(
            Point(x = 1, y = 1) { CoordinatePoint(size)},
            Point(x = 2, y = 2) { CoordinatePoint(size)},
            Point(x = 3, y = 3) { CoordinatePoint(size)},
            Point(x = 4, y = 4) { CoordinatePoint(size)},
            Point(x = 5, y = 5) { CoordinatePoint(size)},
            Point(x = 6, y = 6) { CoordinatePoint(size)},
            Point(x = 7, y = 7) { CoordinatePoint(size)},
            Point(x = 8, y = 8) { CoordinatePoint(size)},


        )
    )

}

@Composable
fun Value(label: String, modifier: Modifier=Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Text(text = label)
    }


}
@Composable
fun CoordinatePoint(size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
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
    xAxisLine: @Composable () -> Unit,
    xAxisNumbers: @Composable () -> Unit,
    points: List<Point>,
) {
    val density = LocalDensity.current.density
    val gapBetweenNumbersPx = (gap.value * density).toInt()

    val pointsComposable = @Composable {
        points.forEach {point-> point.content()}
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

class PlacementUtils(
    private val yAxisNumbersPlaceableList: List<Placeable>,
    private val xAxisNumbersPlaceableList: List<Placeable>,
    private val gapBetweenValuePx: Int
) {
    private val yAxisWidth = yAxisNumbersPlaceableList.maxBy { it.width }.width
    private val yAxisHeight = yAxisNumbersPlaceableList.sumOf { it.height } +
            (yAxisNumbersPlaceableList.size - 1) * gapBetweenValuePx

    fun Placeable.PlacementScope.placePoints(pointsPlaceable: List<Placeable>, points: List<Point>) {
        pointsPlaceable.forEachIndexed { index, placeable ->
            val numberOfYValues = points[index].y
            val numberOfXValues = points[index].x
            val numbersOfHorizontalGap=numberOfYValues-1
            val totalHorizontalGap=numbersOfHorizontalGap*gapBetweenValuePx
            val x = xAxisNumbersPlaceableList.take(numberOfXValues).sumOf { it.width } + totalHorizontalGap
            val numbersOfGap=numberOfYValues-1
            val totalGap=numbersOfGap*gapBetweenValuePx
            val y = yAxisNumbersPlaceableList.reversed().take(numberOfYValues).sumOf { it.height }+totalGap
            val offsetY=yAxisNumbersPlaceableList[numberOfYValues-1].height/2
            val offsetX=xAxisNumbersPlaceableList[numberOfXValues-1].width/2
            placeable.placeRelative(x = x+offsetX, y = yAxisHeight-y+offsetY)
        }
    }

    fun Placeable.PlacementScope.placeXAxisBar(xAxisLinePlaceable: Placeable) {
        xAxisLinePlaceable.placeRelative(x = yAxisWidth, y = yAxisHeight )
    }

    fun Placeable.PlacementScope.placeXAxisCoordinates() {
        var totalWidth = yAxisWidth
        xAxisNumbersPlaceableList.forEachIndexed { index, placeable ->
            val prevComposableWidth = xAxisNumbersPlaceableList.getOrNull(index - 1)?.width ?: 0
            //no gap for the first element of x axis
            totalWidth += prevComposableWidth + if (index > 0) gapBetweenValuePx else 0
            placeable.placeRelative(x = totalWidth, y = yAxisHeight)
        }
    }

    fun Placeable.PlacementScope.placeYCoordinates() {
        var totalHeight = 0
        yAxisNumbersPlaceableList.reversed().forEachIndexed { index, it ->
            val prevComposableHeight =
                yAxisNumbersPlaceableList.getOrNull(index - 1)?.height ?: 0
            totalHeight += prevComposableHeight + if (index > 0||index==yAxisNumbersPlaceableList.size-1) gapBetweenValuePx else 0
            it.placeRelative(x = 0, y = totalHeight)
        }
    }
    fun Placeable.PlacementScope.placeYAxisBar(yAxisLinePlaceable: Placeable) {
        yAxisLinePlaceable.placeRelative(yAxisWidth, 0)
    }

}


class MeasureUtils(
    private val gapBetweenNumbersPx: Int,
    private val xAxisNumberMeasurableList: List<Measurable>,
    private val yAxisBar: Measurable,
    private val yAxisNumberMeasurableList: List<Measurable>,
    private val xAxisBar: Measurable,
) {
    private val noOfXCoordinates = xAxisNumberMeasurableList.size
    private val noOfYCoordinates = yAxisNumberMeasurableList.size

    private lateinit var yAxisNumbersPlaceable: List<Placeable>
    private lateinit var xAxisNumbersPlaceable: List<Placeable>
    private lateinit var yAxisBarPlaceable: Placeable
    private lateinit var xAxisBarPlaceable: Placeable
    private val yAxisNumbersHeight: Int
        get() {
            val totalGap = noOfYCoordinates - 1
            return totalGap * gapBetweenNumbersPx + yAxisNumbersPlaceable.sumOf { it.height }
        }
    private val xAxisWidth: Int
        get() {
            return xAxisNumbersPlaceable.sumOf { it.width } + (noOfXCoordinates - 1) * gapBetweenNumbersPx
        }

    fun measureXAxisNumbers(constraints: Constraints): List<Placeable> {
        xAxisNumbersPlaceable = xAxisNumberMeasurableList.map { it.measure(constraints) }
        return xAxisNumbersPlaceable
    }

    fun measureYAxisNumbers(constraints: Constraints): List<Placeable> {
        yAxisNumbersPlaceable = yAxisNumberMeasurableList.map { it.measure(constraints) }
        return yAxisNumbersPlaceable
    }

    fun measureYAxisBar(constraints: Constraints): Placeable {
        yAxisBarPlaceable = yAxisBar.measure(
            constraints.copy(
                minHeight = yAxisNumbersHeight,
                maxHeight = yAxisNumbersHeight
            )
        )
        return yAxisBarPlaceable
    }

    fun measureXAxisBar(constraints: Constraints): Placeable {
        xAxisBarPlaceable =
            xAxisBar.measure(constraints.copy(minWidth = xAxisWidth, maxWidth = xAxisWidth))
        return xAxisBarPlaceable
    }


}


