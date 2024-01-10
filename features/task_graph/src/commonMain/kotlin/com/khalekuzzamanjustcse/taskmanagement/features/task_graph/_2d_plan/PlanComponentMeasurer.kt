package com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_plan

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints

class PlanComponentMeasurer(
    private val gapBetweenCoordinatePx: Int,
    private val abscissas: List<Measurable>,
    private val ordinates: List<Measurable>,
    private val yAxisLine: Measurable,
    private val xAxisLine: Measurable,
    private val yAxisLabel: Measurable,
    private val xAxisLabel: Measurable,
    private val axisArrowLengthPx:Int,
) {
    private val noOfXAbscissas = abscissas.size
    private val noOfYOrdinates = ordinates.size

    private lateinit var ordinatePlaceables: List<Placeable>
    private lateinit var abscissaPlaceables: List<Placeable>
    private lateinit var ordinateLine: Placeable
    private lateinit var abiscissaLine: Placeable
    private val ordinatesHeight: Int
        get() {
            val totalGap = noOfYOrdinates - 1
            return totalGap * gapBetweenCoordinatePx + ordinatePlaceables.sumOf { it.height }
        }
    private val abscissasWidth: Int
        get() {
            return abscissaPlaceables.sumOf { it.width } + (noOfXAbscissas - 1) * gapBetweenCoordinatePx
        }

    fun measureAbscissas(constraints: Constraints): List<Placeable> {
        abscissaPlaceables = abscissas.map { it.measure(constraints) }
        return abscissaPlaceables
    }

    fun measureOrdinates(constraints: Constraints): List<Placeable> {
        ordinatePlaceables = ordinates.map { it.measure(constraints) }
        return ordinatePlaceables
    }
    fun measureYAxisLine(constraints: Constraints): Placeable {
        val height = ordinatesHeight+axisArrowLengthPx
        ordinateLine = yAxisLine.measure(
            constraints.copy(
                minHeight =height ,
                maxHeight = height
            )
        )
        return ordinateLine
    }

    fun measureXAxisLine(constraints: Constraints): Placeable {
        val width = abscissasWidth+axisArrowLengthPx
        abiscissaLine =xAxisLine.measure(constraints.copy(minWidth = width, maxWidth = width))
        return abiscissaLine
    }
    fun measureXAxisLabel(constraints: Constraints):Placeable{
        return xAxisLabel.measure(constraints)
    }
    fun measureYAxisLabel(constraints: Constraints):Placeable{
        return yAxisLabel.measure(constraints)
    }

}
