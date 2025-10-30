package com.lebaillyapp.chatterrpgbox.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay

@Composable
fun DialogueCrawler(
    modifier: Modifier = Modifier,
    text: String,
    fontFamily: FontFamily,
    fontSize: TextUnit,
    color: Color,
    maxWidth: Dp,
    currentChunk: Int,
    currentSpeed: Long,
    highlightMap: Map<String, Color> = emptyMap(), // mot -> couleur
    onChunksCount: (Int) -> Unit
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    // Découpe du texte en chunks de 2 lignes max
    val chunks = remember(text) {
        val tempChunks = mutableListOf<String>()
        var currentLine = ""

        fun fits(line: String): Boolean {
            val layout = textMeasurer.measure(
                AnnotatedString(line),
                style = TextStyle(fontFamily = fontFamily, fontSize = fontSize),
                constraints = Constraints(maxWidth = with(density) { maxWidth.roundToPx() })
            )
            return layout.lineCount <= 2 // 2 lignes max
        }

        text.split(" ").forEach { word ->
            val tryLine = if (currentLine.isEmpty()) word else "$currentLine $word"
            if (fits(tryLine)) {
                currentLine = tryLine
            } else {
                tempChunks.add(currentLine)
                currentLine = word
            }
        }
        if (currentLine.isNotEmpty()) tempChunks.add(currentLine)
        onChunksCount(tempChunks.size)
        tempChunks
    }

    var displayedText by remember { mutableStateOf(AnnotatedString("")) }
    var isTyping by remember { mutableStateOf(false) }

    LaunchedEffect(currentChunk, currentSpeed, chunks) {
        if (chunks.isEmpty() || currentChunk >= chunks.size) return@LaunchedEffect
        isTyping = true
        val fullText = chunks[currentChunk]

        var visibleCount = 0
        while (visibleCount < fullText.length) {
            val partial = fullText.take(++visibleCount)
            val builder = buildAnnotatedString {
                var index = 0
                partial.split(" ").forEach { word ->
                    if (index != 0) append(" ")
                    val start = length
                    append(word)
                    val colorForWord = highlightMap[word] ?: color
                    addStyle(SpanStyle(color = colorForWord), start, start + word.length)
                    index++
                }
            }
            displayedText = builder
            delay(currentSpeed)
        }
        isTyping = false
    }

    Text(
        text = displayedText,
        fontFamily = fontFamily,
        fontSize = fontSize,
        modifier = modifier,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}