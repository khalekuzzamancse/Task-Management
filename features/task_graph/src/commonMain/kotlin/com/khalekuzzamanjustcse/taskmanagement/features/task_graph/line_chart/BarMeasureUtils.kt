package com.khalekuzzamanjustcse.taskmanagement.features.task_graph.line_chart

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints

class BarMeasureUtils(
    private val gapBetweenNumbersPx: Int,
    private val xAxisNumberMeasurableList: List<Measurable>,
    private val yAxisBar: Measurable,
    private val yAxisNumberMeasurableList: List<Measurable>,
    private val barsMeasurable: List<Measurable>,
    private val barsYCoordinate: List<Int>,
    private val xAxisBar: Measurable,
    private val yAxisLabel: Measurable,
    private val xAxisLabel: Measurable,
    private val arrowHeadLengthPx:Int,

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
        val height = yAxisNumbersHeight+arrowHeadLengthPx
        yAxisBarPlaceable = yAxisBar.measure(
            constraints.copy(
                minHeight =height ,
                maxHeight = height
            )
        )
        return yAxisBarPlaceable
    }

    fun measureXAxisBar(constraints: Constraints): Placeable {
        val width = xAxisWidth+arrowHeadLengthPx
        xAxisBarPlaceable =xAxisBar.measure(constraints.copy(minWidth = width, maxWidth = width))
        return xAxisBarPlaceable
    }

    fun measureBars(constraints: Constraints): List<Placeable> {
        val barsPlaceable = barsMeasurable.mapIndexed { index,bar ->
            val yValue =barsYCoordinate[index]
            val numberOfVerticalGap=yValue-1
            val totalVerticalGap=numberOfVerticalGap*gapBetweenNumbersPx
            val barHeight = yAxisNumbersPlaceable.reversed().take(yValue).sumOf { it.height }+totalVerticalGap
            bar.measure(constraints.copy(minHeight = barHeight, maxHeight = barHeight))
        }
        return barsPlaceable
    }
    fun measureXAxisLabels(constraints: Constraints):Placeable{
       return xAxisLabel.measure(constraints)
    }
    fun measureYAxisLabels(constraints: Constraints):Placeable{
        return yAxisLabel.measure(constraints)
    }


}
