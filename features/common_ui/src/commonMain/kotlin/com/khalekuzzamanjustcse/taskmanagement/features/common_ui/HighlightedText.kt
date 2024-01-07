package com.khalekuzzamanjustcse.taskmanagement.features.common_ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration

fun getHighLightedString(text: String, highLightedText: String): AnnotatedString {
    val annotatedEmailString: AnnotatedString = buildAnnotatedString {
        val str = text
        append(str)
        val urls = TextFinder().findText(text, highLightedText)
        urls.forEach { pair ->
            addStyle(
                style = SpanStyle(
                    background = Color.Yellow,
                    textDecoration = TextDecoration.None
                ),
                start = pair.first,
                end = pair.second + 1
            )
        }
    }
    return annotatedEmailString
}


class TextFinder {


    fun findText(text: String, key: String): List<Pair<Int, Int>> {

        val originalText=text.lowercase()
        val searchText=key.lowercase().toRegex()
        val indices = mutableListOf<Pair<Int, Int>>()

        searchText.findAll(originalText).forEach { matchResult ->
            val startIndex = matchResult.range.first
            val endIndex = matchResult.range.last
            indices.add(startIndex to endIndex)
        }
        return indices
    }
}