package com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_graph

import androidx.compose.ui.layout.Placeable

class PlacementUtils(
    private val yAxisNumbersPlaceableList: List<Placeable>,
    private val xAxisNumbersPlaceableList: List<Placeable>,
    private val gapBetweenValuePx: Int
) {
    private val yAxisWidth = yAxisNumbersPlaceableList.maxBy { it.width }.width
    private val yAxisHeight = yAxisNumbersPlaceableList.sumOf { it.height } +
            (yAxisNumbersPlaceableList.size - 1) * gapBetweenValuePx

    fun Placeable.PlacementScope.placePoints(
        pointsPlaceable: List<Placeable>,
        points: List<Point>
    ) {
        pointsPlaceable.forEachIndexed { index, placeable ->
            val numberOfYValues = points[index].y
            val numberOfXValues = points[index].x
            val numbersOfHorizontalGap = numberOfYValues - 1
            val totalHorizontalGap = numbersOfHorizontalGap * gapBetweenValuePx
            val x = xAxisNumbersPlaceableList.take(numberOfXValues)
                .sumOf { it.width } + totalHorizontalGap
            val numbersOfGap = numberOfYValues - 1
            val totalGap = numbersOfGap * gapBetweenValuePx
            val y = yAxisNumbersPlaceableList.reversed().take(numberOfYValues)
                .sumOf { it.height } + totalGap
//            val offsetY = yAxisNumbersPlaceableList[numberOfYValues - 1].height / 2
//            val offsetX = xAxisNumbersPlaceableList[numberOfXValues - 1].width / 2
            val offsetY = 0
            val offsetX = 0
            placeable.placeRelative(x = x + offsetX, y = yAxisHeight - y + offsetY)
        }
    }

    fun Placeable.PlacementScope.placeXAxisBar(xAxisLinePlaceable: Placeable) {
        xAxisLinePlaceable.placeRelative(x = yAxisWidth, y = yAxisHeight)
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
            totalHeight += prevComposableHeight + if (index > 0 || index == yAxisNumbersPlaceableList.size - 1) gapBetweenValuePx else 0
            it.placeRelative(x = 0, y = totalHeight)
        }
    }

    fun Placeable.PlacementScope.placeYAxisBar(yAxisLinePlaceable: Placeable) {
        yAxisLinePlaceable.placeRelative(yAxisWidth, 0)
    }

}