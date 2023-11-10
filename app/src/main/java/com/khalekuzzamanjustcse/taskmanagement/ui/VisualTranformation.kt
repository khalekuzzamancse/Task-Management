package com.khalekuzzamanjustcse.taskmanagement.ui

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object DateVisualTransformer : VisualTransformation {
    private val dateOffsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int =
            when (offset) {
            in 0..2 -> offset
            in 3..4 -> offset + 1
            in 5..8 -> offset + 2
            else -> 10
        }

        override fun transformedToOriginal(offset: Int): Int =
            when (offset) {
            in 0..2 -> offset
            in 4..5 -> offset - 1
            in 7..10 -> offset - 2
            else -> 8
        }

    }
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = if (text.text.length >= 8) text.text.substring(0..7) else text.text
        var outputText = ""
        for (i in originalText.indices) {
            outputText += originalText[i]
            if (i == 1 || i == 3) outputText += "-"
        }
        return TransformedText(AnnotatedString(outputText), dateOffsetMapping)
    }

}

object CreditCardVisualTransformer : VisualTransformation {
    private val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int = when (offset) {
            in 0..3 -> offset
            in 4..7 -> offset + 1
            in 8..11 -> offset + 2
            in 12..16 -> offset + 3
            else -> 19
        }

        override fun transformedToOriginal(offset: Int): Int = when (offset) {
            in 0..4 -> offset
            in 5..9 -> offset - 1
            in 10..14 -> offset - 2
            in 15..19 -> offset - 3
            else -> 16
        }

    }

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = if (text.text.length >= 16) text.text.substring(0..15) else text.text
        var outputText = ""
        for (i in originalText.indices) {
            outputText += originalText[i]
            if (i % 4 == 3 && i != 15) outputText += "-"
        }
        return TransformedText(AnnotatedString(outputText), creditCardOffsetTranslator)
    }

}


object PasswordVisualTransformation : VisualTransformation {
    private val offsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int = offset
        override fun transformedToOriginal(offset: Int): Int = offset
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val builder = AnnotatedString.Builder()
        for (i in text.indices) {
            val transformedChar = "*"
            builder.append(transformedChar)
        }
        val transformed = builder.toAnnotatedString()
        return TransformedText(transformed, offsetMapping)

    }
}