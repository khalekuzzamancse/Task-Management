package com.just.cse.digital_diary.features.common_ui.form

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import kotlin.math.max

@Composable
 fun FormLayout(
    eachRow1stChildMaxWidth: Dp,
    verticalGap: Dp,
    horizontalGap: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {

    Layout(
        modifier = modifier, content = content
    ) { measurables, constraints ->

        if(measurables.isEmpty())
            throw  IllegalArgumentException("total item can not be 0")
        if(measurables.size%2!=0)
            throw  IllegalArgumentException("total item can not be odd")

        val firstColumnChildMeasureAbles = measurables.filterIndexed { i, _ -> i % 2 == 0 }
        val secondColumnChildMeasureAbles = measurables.filterIndexed { i, _ -> i % 2 == 1 }
        val eachRow1stChildConstraint = Constraints(
            minWidth = 0,
            minHeight = 0,
            maxWidth = eachRow1stChildMaxWidth.toPx().toInt(),
            maxHeight = Constraints.Infinity
        )
        val firstColumnChildPlaceAbles = firstColumnChildMeasureAbles.map { measurable ->
            measurable.measure(eachRow1stChildConstraint)
        }
        //let gap between two column=5dp

        val firstColumnWidth = firstColumnChildPlaceAbles.maxOf { it.width  }+horizontalGap.toPx().toInt()
        val secondColumnWidth = constraints.maxWidth - firstColumnWidth
        val eachRowSecondChildConstraint = Constraints(
            minWidth = secondColumnWidth,
            minHeight = 0,
            maxWidth = secondColumnWidth,
            maxHeight = Constraints.Infinity
        )
        val secondColumnChildPlaceAbles = secondColumnChildMeasureAbles.map { measurable ->
            measurable.measure(eachRowSecondChildConstraint)
        }
        val layoutHeight = max(firstColumnChildPlaceAbles.sumOf { it.height }, secondColumnChildPlaceAbles.sumOf { it.height })
        layout(constraints.maxWidth, layoutHeight) {
            var y = 0
            firstColumnChildPlaceAbles.forEachIndexed { i, label ->
                val textField = secondColumnChildPlaceAbles[i]
                //Height
                val rowHeight = max(label.height, textField.height)+verticalGap.toPx().toInt()
                val eachRow1stChildMoveDown = (rowHeight - label.height) / 2
                val eachRow2ndChildMoveDown = (rowHeight - textField.height) / 2
                label.placeRelative(0, y + eachRow1stChildMoveDown)
                textField.placeRelative(firstColumnWidth, y + eachRow2ndChildMoveDown)
                y += rowHeight
            }

        }
    }
}
