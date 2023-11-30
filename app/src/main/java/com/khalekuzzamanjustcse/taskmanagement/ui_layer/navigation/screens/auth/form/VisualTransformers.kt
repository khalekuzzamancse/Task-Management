package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmedText = text.text.filter { it.isDigit() }
        val formattedText = buildString {
            for (i in trimmedText.indices) {
                if (i == 5) {
                    append('-')
                }
                if (i < 11) {
                    append(trimmedText[i])
                }
            }
        }
        return TransformedText(
            AnnotatedString.Builder(formattedText).toAnnotatedString(),
            OffsetMapping.Identity
        )
    }
}
