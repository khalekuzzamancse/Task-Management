package com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_graph

import androidx.compose.ui.layout.Placeable

class PlacementUtils(
    private val yAxisNumbersPlaceableList: List<Placeable>,
    private val xAxisNumbersPlaceableList: List<Placeable>,
    private val yAxisLinePlaceable:Placeable,
    private val xAxisLinePlaceable: Placeable,
    private val gapBetweenValuePx: Int,
    private val axisArrowLength:Int,
    private val xAxisLabel:Placeable,
    private val yAxisLabel:Placeable,
) {
    private val yAxisWidth = yAxisNumbersPlaceableList.maxBy { it.width }.width+yAxisLinePlaceable.width
    private val yAxisHeight = yAxisNumbersPlaceableList.sumOf { it.height } +
            (yAxisNumbersPlaceableList.size - 1) * gapBetweenValuePx+axisArrowLength
    private val xAxisWidth = xAxisNumbersPlaceableList.sumOf { it.width }+
            (xAxisNumbersPlaceableList.size-1)*gapBetweenValuePx+
            yAxisWidth+gapBetweenValuePx+axisArrowLength


    fun Placeable.PlacementScope.placePoints(
        pointsPlaceable: List<Placeable>,
        points: List<Point>
    ) {
        pointsPlaceable.forEachIndexed { index, point ->
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
            val pointHeight =point.height
            val heightDiff =yAxisPointYThValueHeight-pointHeight
            val offsetY=heightDiff/2
            //
            point.placeRelative(x = x+offsetX, y = yAxisHeight - y+offsetY)
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
        var iThPlaceableY = axisArrowLength
        yAxisNumbersPlaceableList.reversed().forEachIndexed { index, it ->
            val prevComposableHeight =
                yAxisNumbersPlaceableList.getOrNull(index - 1)?.height ?: 0
            iThPlaceableY += prevComposableHeight + if (index > 0 || index == yAxisNumbersPlaceableList.size - 1) gapBetweenValuePx else 0
            it.placeRelative(x = 0, y = iThPlaceableY)
        }
    }

    fun Placeable.PlacementScope.placeYAxisBar() {
        yAxisLinePlaceable.placeRelative(yAxisWidth, 0)
    }
    fun Placeable.PlacementScope.placeXAxisLabel() {
        val offsetY=xAxisNumbersPlaceableList.maxBy { it.height }.height/2
        xAxisLabel.placeRelative(x=xAxisWidth,y=yAxisHeight-offsetY)
    }
    fun Placeable.PlacementScope.placeYAxisLabel() {
        val offsetY=yAxisLabel.height
        val offsetX=yAxisLinePlaceable.width/2
        yAxisLabel.placeRelative(x=yAxisWidth-offsetX,y=0-offsetY)
    }
}