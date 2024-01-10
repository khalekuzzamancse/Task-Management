package com.khalekuzzamanjustcse.taskmanagement.features.task_graph.calender_view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

data class Day(
    val day: Int,
    val tasksCount: Int,
)

fun dummyData(): List<Day> {
    val dummyDays = (1..31).map { day ->
        Day(
            day = day,
            tasksCount = Random.nextInt(3)
        )
    }
    return dummyDays
}

@Composable
fun _2dCalender() {
    CalenderComposable(
        days = dummyData(),
        monthName = "January 2024",
    )

}

@Composable
fun CalenderComposable(
    monthName: String,
    days: List<Day>,
    onDayClick: (day: Int) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(
                width = 2.dp,
                color = Color.Black
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = monthName,
            style = MaterialTheme.typography.titleMedium,

            )
        Spacer(Modifier.height(8.dp))
        Days(
            days = days,
            onDayClick = onDayClick
        )
    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Days(
    days: List<Day>,
    onDayClick: (day: Int) -> Unit = {},
) {


    FlowRow(
        modifier = Modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        days.forEachIndexed { index, item ->
            Day(
                day = item.day,
                badgeCount = item.tasksCount,
                onDayClick = onDayClick,
                visibilityDelay = (index + 1) * 50L
            )

        }


    }


}



