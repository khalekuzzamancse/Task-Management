package com.khalekuzzamanjustcse.taskmanagement._2d_graph

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate

@Composable
fun YAxisLineArrow(modifier: Modifier = Modifier) {
    Box(modifier
        .drawWithContent {
            val x = size.width / 2
            val start = Offset(x = x, y = size.height)
            val end = Offset(x = x, y = 0f)
            drawLine(
                color = Color.Black,
                start = start,
                end = end,
                strokeWidth = 2f
            )
            val headLengthOffset = Offset(x = 0f, y = 10f)
            drawArrowHead(
                color = Color.Black,
                arrowHeadPosition = end + headLengthOffset,
                end = end
            )
            this.drawContent()
        }
    )
}

@Composable
fun XAxisLineArrow(modifier: Modifier = Modifier) {
    Box(modifier
        .drawWithContent {
            val y = size.height / 2
            val start = Offset(x = 0f, y = y)
            val end = Offset(x = size.width, y = y)
            drawLine(
                color = Color.Black,
                start = start,
                end = end,
                strokeWidth = 2f
            )
            val headLengthOffset = Offset(x = 10f, y = 0f)
            drawArrowHead(
                color = Color.Black,
                arrowHeadPosition = end - headLengthOffset,
                end = end
            )
            this.drawContent()
        }
    )
}

private fun DrawScope.drawArrowHead(color: Color, arrowHeadPosition: Offset, end: Offset) {
    if (arrowHeadPosition != Offset.Unspecified) {
        rotate(30f, end) {
            drawLine(color, start = arrowHeadPosition, end = end, 2f)
        }
        rotate(-30f, end) {
            drawLine(color, arrowHeadPosition, end, 2f)
        }
    }
}
