package com.khalekuzzamanjustcse.taskmanagement.features.common_ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Composable
fun TypeWriter(
    text: String,
    delay:Long,
    decorator:@Composable (text: String) ->Unit,
) {
    TypeWriter(
        text= AnnotatedString(text),
        delay=delay,
        decorator={
            decorator(it.text)
        }
    )
}

@Composable
fun TypeWriter(
    text: AnnotatedString,
    delay:Long,
    decorator:@Composable (AnnotatedString)->Unit
) {
    var currentText by remember { mutableStateOf(AnnotatedString("")) }
    OnEachCharacter(text=text, delay =delay){
        currentText=it
    }
    decorator(currentText)
}


@Composable
private fun OnEachCharacter(
    text: AnnotatedString,
    delay:Long,
    onSubstringProcessed: (AnnotatedString) -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (currentIndex < text.length) {
                val substring = text.subSequence(0, currentIndex + 1)
                onSubstringProcessed(substring)
                delay(delay)
                currentIndex++
            }
        }
    }

}


