package com.khalekuzzamanjustcse.taskmanagement.features.task_graph.calender_view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

data class Day(
    val day: Int,
    val tasksCount: Int,
)

fun dummyData(): List<Day> {
    val dummyDays = (1..31).map { day ->
        Day(
            day = day,
            tasksCount =Random.nextInt(3)
        )
    }
    return dummyDays
}

@Composable
fun _2dCalender() {
    CalenderComposable(
        days = dummyData(),
        monthName = "January",
    )

}

@Composable
fun CalenderComposable(
    monthName:String,
    days: List<Day>,
    onDayClick:(day:Int)->Unit={},
) {
    Column (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(
                width = 2.dp,
                color = Color.Black
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = monthName,
            style = MaterialTheme.typography.titleMedium,

        )
        Spacer(Modifier.height(8.dp))
        Days(
            days=days,
            onDayClick = onDayClick
        )
    }


}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Days(
    days: List<Day>,
    onDayClick:(day:Int)->Unit={},
) {


        FlowRow(
            modifier = Modifier
                .padding( 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            days.forEachIndexed{index,item ->
                    Day(
                        day=item.day,
                        badgeCount = item.tasksCount,
                        onDayClick = onDayClick,
                        visibilityDelay = (index+1)*100L
                    )

            }


        }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Day(
    day: Int,
    badgeCount: Int,
    visibilityDelay: Long,
    onDayClick: (day: Int) -> Unit = {},
) {
    val primary=MaterialTheme.colorScheme.primary
    var color by remember {
        mutableStateOf(primary)
    }
    var textColor by remember(color) {
      mutableStateOf(  if (isColorDark(color)) Color.White else Color.Black)
    }
    var visibile by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit){
        delay(visibilityDelay)
        visibile=true
    }

    LaunchedEffect(color){
        delay(1000)
        if (badgeCount>0){
            color= Color(
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255),

            )
            textColor= if (isColorDark(color)) Color.White else Color.Black

        }

    }

    AnimatedVisibility(
        visible = visibile,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ){
        Surface(
            shadowElevation = 8.dp,
            modifier = Modifier
                .size(55.dp)
                .clip(RoundedCornerShape(4.dp))
                .clickable {
                    onDayClick(day)
                }
            ,
            color = color
        ) {
            BadgedBox(
                modifier = Modifier.padding(8.dp).size(50.dp),
                badge = {
                    if (badgeCount>0){
                        Badge {
                            Text(text = "$badgeCount")
                        }
                    }

                }
            ) {
                Text(
                    text="$day",
                    color=textColor)
            }


        }
    }


}
private fun isColorDark(color: Color): Boolean {
    val luminance = 0.299 * color.red + 0.587 * color.green + 0.114 * color.blue
    return luminance < 0.5
}