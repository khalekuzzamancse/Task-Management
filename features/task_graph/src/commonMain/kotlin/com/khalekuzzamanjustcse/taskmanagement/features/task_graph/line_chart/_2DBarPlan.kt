package com.khalekuzzamanjustcse.taskmanagement.features.task_graph.line_chart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun _2DBarPlane(
    modifier: Modifier = Modifier,
    gap: Dp,
    yAxisNumbers: @Composable () -> Unit,
    yAxisLine: @Composable () -> Unit,
    xAxisLine: @Composable () -> Unit,
    xAxisNumbers: @Composable () -> Unit,
    bars: List<Bar>,
) {
    val density = LocalDensity.current.density
    val gapBetweenNumbersPx = (gap.value * density).toInt()

    val pointsComposable = @Composable {
        bars.forEach { bar -> bar.content() }
    }
    val contents = listOf(xAxisNumbers, yAxisNumbers, pointsComposable, yAxisLine, xAxisLine)
    Layout(modifier = modifier, contents = contents) { listOfMeasurableList, constraints ->
        val (xAxisNumbersMeasurable, yAxisNumbersMeasurable, barsMeasurable, yAxisLineMeasurable, xAxisLineMeasurable) = listOfMeasurableList
        val measureUtils = BarMeasureUtils(
            gapBetweenNumbersPx = gapBetweenNumbersPx,
            xAxisNumberMeasurableList = xAxisNumbersMeasurable,
            yAxisNumberMeasurableList = yAxisNumbersMeasurable,
            yAxisBar = yAxisLineMeasurable.first(),
            xAxisBar = xAxisLineMeasurable.first(),
            barsMeasurable = barsMeasurable,
            barsYCoordinate = bars.map { it.y }
        )
        val yAxisNumbersPlaceable = measureUtils.measureYAxisNumbers(constraints)
        val yAxisLinePlaceable = measureUtils.measureYAxisBar(constraints)
        val xAxisNumbersPlaceable = measureUtils.measureXAxisNumbers(constraints)
        val xAxisLinePlaceable = measureUtils.measureXAxisBar(constraints)
        val barsPlaceable = measureUtils.measureBars(constraints)

        layout(width = constraints.maxWidth, height = constraints.maxHeight, placementBlock
        = {
            val placementUtils = BarPlacementUtils(
                yAxisNumbersPlaceableList = yAxisNumbersPlaceable,
                xAxisNumbersPlaceableList = xAxisNumbersPlaceable,
                gapBetweenValuePx = gapBetweenNumbersPx,
                xAxisLinePlaceable = xAxisLinePlaceable,
                yAxisLinePlaceable = yAxisLinePlaceable
            )
            //placings between
            placementUtils.run {
                placeYCoordinates()
                placeYAxisBar()
                placeXAxisCoordinates()
                placeXAxisBar()
                placeBars(bars = barsPlaceable, points = bars)
            }
        })
    }
}
