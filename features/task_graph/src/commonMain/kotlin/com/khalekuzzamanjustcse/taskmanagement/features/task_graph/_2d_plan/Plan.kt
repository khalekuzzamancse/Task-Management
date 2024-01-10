package com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_plan

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun _2DPlane(
    modifier: Modifier = Modifier,
    gap: Dp,
    yAxisNumbers: @Composable () -> Unit,
    yAxisLine: @Composable () -> Unit,
    xAxisLine: @Composable () -> Unit,
    xAxisNumbers: @Composable () -> Unit,
    xAxisLabel: @Composable () -> Unit,
    yAxisLabel: @Composable () -> Unit,
    arrowHeadLengthPx: Int = 10,
) {
    val density = LocalDensity.current.density
    val gapBetweenNumbersPx = (gap.value * density).toInt()
    val contents = listOf(
        xAxisNumbers, yAxisNumbers, yAxisLine, xAxisLine,
        xAxisLabel, yAxisLabel
    )
    Layout(modifier = modifier, contents = contents) { listOfMeasurableList, constraints ->
        val xAxisNumbersMeasurable = listOfMeasurableList[0]
        val yAxisNumbersMeasurable = listOfMeasurableList[1]
        val yAxisLineMeasurable = listOfMeasurableList[2]
        val xAxisLineMeasurable = listOfMeasurableList[3]
        val xAxisLabelMeasurable = listOfMeasurableList[4]
        val yAxisLabelMeasurable = listOfMeasurableList[5]

        val measureUtils = PlanComponentMeasurer(
            gapBetweenCoordinatePx = gapBetweenNumbersPx,
            abscissas = xAxisNumbersMeasurable,
            ordinates = yAxisNumbersMeasurable,
            xAxisLine = xAxisLineMeasurable.first(),//pass a top level singe composable
            yAxisLine = yAxisLineMeasurable.first(),//pass a top level singe composable
            axisArrowLengthPx = arrowHeadLengthPx,
            xAxisLabel = xAxisLabelMeasurable.first(),//pass a top level singe composable
            yAxisLabel = yAxisLabelMeasurable.first(), //pass a top level singe composable
        )
        val yAxisNumbersPlaceable = measureUtils.measureOrdinates(constraints)
        val yAxisLinePlaceable = measureUtils.measureYAxisLine(constraints)
        val xAxisNumbersPlaceable = measureUtils.measureAbscissas(constraints)
        val xAxisLinePlaceable = measureUtils.measureXAxisLine(constraints)
        val xAxisLabelPlaceable = measureUtils.measureXAxisLabel(constraints)
        val yAxisLabelPlaceable = measureUtils.measureYAxisLabel(constraints)

        layout(width = constraints.maxWidth, height = constraints.maxHeight, placementBlock
        = {
            val placementUtils = PlanComponentPlacer(
                ordinates = yAxisNumbersPlaceable,
                abscissas = xAxisNumbersPlaceable,
                gapCoordinatePx = gapBetweenNumbersPx,
                xAxisLine = xAxisLinePlaceable,
                yAxisLine = yAxisLinePlaceable,
                axisArrowLength = arrowHeadLengthPx,
                xAxisLabel = xAxisLabelPlaceable,
                yAxisLabel = yAxisLabelPlaceable,
            )
            //placings between
            placementUtils.run {
                placeCoordinates()
                placeAxisLines()
                placeAxisLabels()
            }
        })
    }
}
