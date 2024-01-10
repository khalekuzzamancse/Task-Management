package com.khalekuzzamanjustcse.taskmanagement.features.task_graph._2d_graph

import androidx.compose.runtime.Composable

data class Point(
    val x: Int,
    val y: Int,
    val content: @Composable () -> Unit,
)
