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
import androidx.compose.ui.text.TextStyle
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
    onChunksCount: (Int) -> Unit
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    // Découpe du texte en chunks de 2 lignes max
    val chunks = remember(text) {
        val words = text.split(" ")
        val tempChunks = mutableListOf<Pair<String, String>>()
        var line1 = ""
        var line2 = ""

        fun fits(line: String): Boolean {
            val layout = textMeasurer.measure(
                AnnotatedString(line),
                style = TextStyle(fontFamily = fontFamily, fontSize = fontSize),
                constraints = Constraints(maxWidth = with(density) { maxWidth.roundToPx() })
            )
            return layout.lineCount <= 1
        }

        for (word in words) {
            val tryLine1 = if (line1.isEmpty()) word else "$line1 $word"
            if (fits(tryLine1)) {
                line1 = tryLine1
            } else {
                val tryLine2 = if (line2.isEmpty()) word else "$line2 $word"
                if (fits(tryLine2)) {
                    line2 = tryLine2
                } else {
                    tempChunks.add(line1 to line2)
                    line1 = word
                    line2 = ""
                }
            }
        }
        if (line1.isNotEmpty() || line2.isNotEmpty()) tempChunks.add(line1 to line2)
        onChunksCount(tempChunks.size) // informe le parent
        tempChunks
    }

    var displayedLines by remember { mutableStateOf(listOf("", "")) }
    var isTyping by remember { mutableStateOf(false) }

    // Typing effect
    LaunchedEffect(currentChunk, currentSpeed, chunks) {
        if (chunks.isEmpty() || currentChunk >= chunks.size) return@LaunchedEffect
        isTyping = true
        displayedLines = listOf("", "")
        val fullChunk = chunks[currentChunk].first + "\n" + chunks[currentChunk].second
        var visibleCount = 0
        while (visibleCount < fullChunk.length) {
            val partial = fullChunk.take(++visibleCount)
            val lines = partial.split("\n")
            displayedLines = listOf(lines.getOrNull(0).orEmpty(), lines.getOrNull(1).orEmpty())
            delay(currentSpeed)
        }
        isTyping = false
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Text(
            text = displayedLines.getOrElse(0) { "" },
            fontFamily = fontFamily,
            fontSize = fontSize,
            color = color,
            softWrap = false,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
        Text(
            text = displayedLines.getOrElse(1) { "" },
            fontFamily = fontFamily,
            fontSize = fontSize,
            color = color,
            softWrap = false,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}