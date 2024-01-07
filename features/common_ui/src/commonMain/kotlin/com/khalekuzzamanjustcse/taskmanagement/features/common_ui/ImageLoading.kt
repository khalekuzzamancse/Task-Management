package com.khalekuzzamanjustcse.taskmanagement.features.common_ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ImageLoader(
    url: String,
    modifier: Modifier = Modifier,
) {
    KamelImage(
        resource = asyncPainterResource(url),
        contentDescription = "",
        modifier = modifier,
    )

}