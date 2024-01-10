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
    xAxisLabel: @Composable () -> Unit,
    yAxisLabel: @Composable () -> Unit,
    axisArrowLength:Int=10,
    bars: List<Bar>,
) {
    val density = LocalDensity.current.density
    val gapBetweenNumbersPx = (gap.value * density).toInt()

    val barsComposable = @Composable {
        bars.forEach { bar -> bar.content() }
    }
    val contents = listOf(
        xAxisNumbers, yAxisNumbers,
        barsComposable, yAxisLine, xAxisLine,
        xAxisLabel, yAxisLabel
    )
    Layout(modifier = modifier, contents = contents) { listOfMeasurableList, constraints ->
        val xAxisNumbersMeasurable = listOfMeasurableList[0]
        val yAxisNumbersMeasurable = listOfMeasurableList[1]
        val barsMeasurable = listOfMeasurableList[2]
        val yAxisLineMeasurable = listOfMeasurableList[3]
        val xAxisLineMeasurable = listOfMeasurableList[4]
        val xAxisLabelMeasurable = listOfMeasurableList[5]
        val yAxisLabelMeasurable = listOfMeasurableList[6]

        val measureUtils = BarMeasureUtils(
            gapBetweenNumbersPx = gapBetweenNumbersPx,
            xAxisNumberMeasurableList = xAxisNumbersMeasurable,
            yAxisNumberMeasurableList = yAxisNumbersMeasurable,
            yAxisBar = yAxisLineMeasurable.first(),
            xAxisBar = xAxisLineMeasurable.first(),
            barsMeasurable = barsMeasurable,
            barsYCoordinate = bars.map { it.y },
            yAxisLabel = yAxisLabelMeasurable.first(),//pass a single to most composable
            xAxisLabel = xAxisLabelMeasurable.first(),
            arrowHeadLengthPx = axisArrowLength
        )
        val yAxisNumbersPlaceable = measureUtils.measureYAxisNumbers(constraints)
        val yAxisLinePlaceable = measureUtils.measureYAxisBar(constraints)
        val xAxisNumbersPlaceable = measureUtils.measureXAxisNumbers(constraints)
        val xAxisLinePlaceable = measureUtils.measureXAxisBar(constraints)
        val barsPlaceable = measureUtils.measureBars(constraints)
        val xAxisLabelPlaceable = measureUtils.measureXAxisLabels(constraints)
        val yAxisLabelPlaceable = measureUtils.measureYAxisLabels(constraints)

        layout(width = constraints.maxWidth, height = constraints.maxHeight, placementBlock
        = {
            val placementUtils = BarPlacementUtils(
                yAxisNumbersPlaceableList = yAxisNumbersPlaceable,
                xAxisNumbersPlaceableList = xAxisNumbersPlaceable,
                gapBetweenValuePx = gapBetweenNumbersPx,
                xAxisLinePlaceable = xAxisLinePlaceable,
                yAxisLinePlaceable = yAxisLinePlaceable,
                yAxisLabel = yAxisLabelPlaceable,
                xAxisLabel = xAxisLabelPlaceable,
                axisArrowLength = axisArrowLength
            )
            placementUtils.run {
                placeYCoordinates()
                placeYAxisBar()
                placeXAxisCoordinates()
                placeXAxisBar()
                placeBars(bars = barsPlaceable, points = bars)
                placeXAxisLabel()
                placeYAxisLabel()
            }
        })
    }
}
