package com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_plan

import androidx.compose.ui.layout.Placeable

class PlanComponentPlacer(
    private val ordinates: List<Placeable>,
    private val abscissas: List<Placeable>,
    private val yAxisLine: Placeable,
    private val xAxisLine: Placeable,
    private val gapCoordinatePx: Int,
    private val axisArrowLength: Int,
    private val xAxisLabel: Placeable,
    private val yAxisLabel: Placeable,
) {
    private val yAxisWidth = ordinates.maxBy { it.width }.width + yAxisLine.width
    private val yAxisHeight = ordinates.sumOf { it.height } +
            (ordinates.size - 1) * gapCoordinatePx + axisArrowLength
    private val xAxisWidth = abscissas.sumOf { it.width } +
            (abscissas.size - 1) * gapCoordinatePx +
            yAxisWidth + gapCoordinatePx + axisArrowLength


    fun Placeable.PlacementScope.placeAxisLines() {
        placeXAxisLine()
        placeYAxisLine()
    }

    fun Placeable.PlacementScope.placeCoordinates() {
        placeAbscissas()
        placeOrdinates()

    }

    fun Placeable.PlacementScope.placeAxisLabels() {
        placeXAxisLabel()
        placeYAxisLabel()
    }
    private fun Placeable.PlacementScope.placeAbscissas() {
        var totalWidth = yAxisWidth
        abscissas.forEachIndexed { index, placeable ->
            val prevComposableWidth = abscissas.getOrNull(index - 1)?.width ?: 0
            //no gap for the first element of x axis
            totalWidth += prevComposableWidth + if (index > 0) gapCoordinatePx else 0
            placeable.placeRelative(x = totalWidth, y = yAxisHeight)
        }
    }


    private fun Placeable.PlacementScope.placeOrdinates() {
        var iThPlaceableY = axisArrowLength
        ordinates.reversed().forEachIndexed { index, it ->
            val prevComposableHeight =
                ordinates.getOrNull(index - 1)?.height ?: 0
            iThPlaceableY += prevComposableHeight + if (index > 0 || index == ordinates.size - 1) gapCoordinatePx else 0
            it.placeRelative(x = 0, y = iThPlaceableY)
        }
    }
    private fun Placeable.PlacementScope.placeXAxisLine() {
        xAxisLine.placeRelative(x = yAxisWidth, y = yAxisHeight)
    }

    private fun Placeable.PlacementScope.placeYAxisLine() {
        yAxisLine.placeRelative(yAxisWidth, 0)
    }

    private fun Placeable.PlacementScope.placeXAxisLabel() {
        val offsetY = abscissas.maxBy { it.height }.height / 2
        xAxisLabel.placeRelative(x = xAxisWidth, y = yAxisHeight - offsetY)
    }

    private fun Placeable.PlacementScope.placeYAxisLabel() {
        val offsetY = yAxisLabel.height
        val offsetX = yAxisLine.width / 2
        yAxisLabel.placeRelative(x = yAxisWidth - offsetX, y = 0 - offsetY)
    }
}