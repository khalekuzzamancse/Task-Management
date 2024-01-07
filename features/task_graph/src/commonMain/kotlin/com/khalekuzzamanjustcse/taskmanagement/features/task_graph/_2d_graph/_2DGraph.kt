package com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType

@Composable
fun _2dGraph() {
        val testLineParameters: List<LineParameters> = listOf(
            LineParameters(
                label = "Earnings",
                data = listOf(0.0, 1.0, 2, 3, 4, 5) as List<Double>,
                lineColor = Color(0xFFFF7F50),
                lineType = LineType.DEFAULT_LINE,
                lineShadow = true
            ),


        )

        Box(Modifier) {
            LineChart(
                modifier = Modifier.fillMaxSize(),
                linesParameters = testLineParameters,
                isGrid = true,
                gridColor = Color.Blue,
                xAxisData = listOf("2015", "2016", "2017", "2018", "2019", "2020"),
                animateChart = true,
                showGridWithSpacer = true,
                yAxisStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray,
                ),
                xAxisStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.W400
                ),
                yAxisRange = 14,
                oneLineChart = false,
                gridOrientation = GridOrientation.VERTICAL
            )
        }


}