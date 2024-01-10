package com.khalekuzzamanjustcse.taskmanagement.features.task_graph.line_chart

import androidx.compose.ui.layout.Placeable

class BarPlacementUtils(
    private val yAxisNumbersPlaceableList: List<Placeable>,
    private val xAxisNumbersPlaceableList: List<Placeable>,
    private val yAxisLinePlaceable:Placeable,
    private val xAxisLinePlaceable: Placeable,
    private val gapBetweenValuePx: Int
) {
    private val yAxisWidth = yAxisNumbersPlaceableList.maxBy { it.width }.width+yAxisLinePlaceable.width
    private val yAxisHeight = yAxisNumbersPlaceableList.sumOf { it.height } +
            (yAxisNumbersPlaceableList.size - 1) * gapBetweenValuePx

    fun Placeable.PlacementScope.placeBars(
        bars: List<Placeable>,
        points: List<Bar>
    ) {
        bars.forEachIndexed { index, point ->
            val pointY = points[index].y
            val pointX = points[index].x
            val numbersOfHorizontalGap = pointX-1
            val totalHorizontalGap = numbersOfHorizontalGap * gapBetweenValuePx
            val x = yAxisWidth+xAxisNumbersPlaceableList.take(pointX-1).sumOf { it.width }+totalHorizontalGap

            val numbersOfGap = pointY - 1
            val totalGap = numbersOfGap * gapBetweenValuePx
            val y = yAxisNumbersPlaceableList.reversed().take(pointY).sumOf { it.height } + totalGap
            val xAxisPointXThXValueWidth=xAxisNumbersPlaceableList[pointX - 1].width
            val pointWidth =point.width
            val widthDiff =xAxisPointXThXValueWidth-pointWidth
            val offsetX=widthDiff/2
            //
            val yAxisPointYThValueHeight = yAxisNumbersPlaceableList[pointY - 1].height
            val offsetBarY =point.height
            //
            point.placeRelative(x =x+offsetX, y =yAxisHeight-offsetBarY)
        }
    }

    fun Placeable.PlacementScope.placeXAxisBar() {
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

    fun Placeable.PlacementScope.placeYAxisBar() {
        yAxisLinePlaceable.placeRelative(yAxisWidth, 0)
    }

}