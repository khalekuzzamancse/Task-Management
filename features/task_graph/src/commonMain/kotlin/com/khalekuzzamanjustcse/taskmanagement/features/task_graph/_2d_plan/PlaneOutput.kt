package com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_plan.components.XAxisLineArrow
import com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_plan.components.YAxisLineArrow
import kotlin.math.abs

data class CoordinatesInfo(
    val abscissaInfo: Rect = Rect(0f, 0f, 0f, 0f),
    val ordinateInfo: Rect = Rect(0f, 0f, 0f, 0f),
)

class CoordinateManager(

) {
    private val abscissaInfos = mutableListOf<Rect>()
    private val ordinateInfos = mutableListOf<Rect>()

    fun addAbscissa(abscissaInfo: Rect) {
        abscissaInfos.add(abscissaInfo)
    }

    fun addOrdinate(ordinateInfo: Rect) {
        ordinateInfos.add(ordinateInfo)
    }

    fun getCoordinatesInfo(x: Int, y: Int): CoordinatesInfo {
        return CoordinatesInfo(abscissaInfo = abscissaInfos[x], ordinateInfo = ordinateInfos[y])
    }


}

@Composable
fun _2dPlaneOutput() {
    val numberModifier = Modifier
        .background(Color.Green)
    var show by remember { mutableStateOf(false) }
    var placedAbissa by remember { mutableStateOf(0) }
    var placedOridtes by remember { mutableStateOf(0) }

    val coordinateManager = remember { CoordinateManager() }


    if (show) {
        PlacePoint(coordinateManager.getCoordinatesInfo(0, 0))
        PlacePoint(coordinateManager.getCoordinatesInfo(1, 2))
        PlacePoint(coordinateManager.getCoordinatesInfo(3, 0))
        PlacePoint(coordinateManager.getCoordinatesInfo(10, 1))
        PlacePoint(coordinateManager.getCoordinatesInfo(5, 10))

    }
    if (placedAbissa == 11 && placedOridtes == 11) {
        show = true

    }

    _2DPlane(
        modifier = Modifier.padding(16.dp).wrapContentSize(),
        xAxisNumbers = {
            for (i in 0..10) {
                Value(label = "$i", modifier = numberModifier) { abscissaInfo ->
                    coordinateManager.addAbscissa(abscissaInfo)
                    placedAbissa++
                }
            }
        },
        gap = 10.dp,
        arrowHeadLengthPx = 10,
        yAxisNumbers = {
            for (i in 0..10) {
                Value(label = "$i", modifier = numberModifier) { ordinateInfo ->
                    coordinateManager.addOrdinate(ordinateInfo)
                    placedOridtes++
                }
            }
        },
        yAxisLine = {
            YAxisLineArrow(
                modifier = Modifier
                    .width(2.dp)
            )

        },
        xAxisLine = {
            XAxisLineArrow(modifier = Modifier.height(2.dp))
        },
        xAxisLabel = {
            Text("X")
        },
        yAxisLabel = {
            Text("Y")
        },
    )


}

@Composable
fun PlacePoint(
    coordinatesInfo: CoordinatesInfo
) {
    val pointSize = remember { 8.dp }
    val density = LocalDensity.current
    val pointSizePx = remember { with(density) { pointSize.toPx() } }
    val (abscissaInfo, ordinateInfo) = coordinatesInfo
    val pointHeight = pointSizePx
    val pointWidth = pointSizePx
    val offsetX = abs(abscissaInfo.width - pointWidth) / 2 + pointWidth
    val offsetY = abs(ordinateInfo.height - pointHeight) / 2 + pointHeight
    Point(offset = Offset(abscissaInfo.left + offsetX, ordinateInfo.top + offsetY))
}

@Composable
fun Value(
    modifier: Modifier = Modifier,
    label: String,
    onPositioned: (Rect) -> Unit = {}
) {

    Box(
        modifier = modifier.onGloballyPositioned {
            val rect = it.boundsInParent()
            onPositioned(
                rect
            )
        },
        contentAlignment = Alignment.Center
    ) {
        Text(text = label)
    }


}

@Composable
fun Point(modifier: Modifier = Modifier, size: Dp = 8.dp, offset: Offset) {
    val sizePx = with(LocalDensity.current) { size.toPx() }
    val shiftX = sizePx.toInt()
    val shiftY = sizePx.toInt()
    Box(
        modifier = modifier
            .size(size)
            .offset {
                IntOffset(offset.x.toInt() + shiftX, offset.y.toInt() + shiftY)
            }
            .background(Color.Black),
    )


}