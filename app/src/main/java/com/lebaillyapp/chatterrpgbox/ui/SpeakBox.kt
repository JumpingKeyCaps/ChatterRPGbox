package com.lebaillyapp.chatterrpgbox.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lebaillyapp.chatterrpgbox.ui.animation.heroAnimationSequence2
import com.lebaillyapp.chatterrpgbox.ui.theme.pixelFont
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.lebaillyapp.chatterrpgbox.AnimatedHero
import com.lebaillyapp.chatterrpgbox.R

@Composable
fun SpeakBox(
    modifier: Modifier = Modifier,
    avatarSizer: Int,
    speakCardRoundedCornerShaper: Shape,
    text2say: String = " "
) {
    var currentChunk by remember { mutableStateOf(0) }
    var currentSpeed by remember { mutableStateOf(100L) }
    var totalChunks by remember { mutableStateOf(1) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 62.dp, start = 10.dp)
    ) {

        Box(
            modifier = Modifier
                .padding(
                    start = (avatarSizer*0.45f).dp,
                    top = (avatarSizer*0.30f).dp,
                    end = 10.dp,
                    bottom = 10.dp

                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            currentSpeed = 50L
                            tryAwaitRelease()
                            currentSpeed = 100L
                        }
                    )
                }
        ) {
            Card(
                modifier = Modifier
                    .height(74.dp)
                    .widthIn(min = 300.dp, max = 300.dp)
                    .align(Alignment.CenterStart),
                shape = speakCardRoundedCornerShaper,
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0C0B17))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 60.dp, end = 20.dp)

                ) {

                    Column(
                        Modifier
                            .fillMaxSize()
                            .align(Alignment.TopStart)
                            .padding(end = 34.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Mr.Evil",
                            fontFamily = pixelFont,
                            fontSize = 19.sp,
                            color = Color(0xFF414141),
                            softWrap = false,
                            maxLines = 1,
                            overflow = TextOverflow.Clip
                        )

                        DialogueCrawler(
                            modifier = Modifier.offset(y=(-2).dp),
                            text = text2say,
                            fontFamily = pixelFont,
                            fontSize = 20.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            maxWidth = 200.dp,
                            currentChunk = currentChunk,
                            currentSpeed = currentSpeed,
                            onChunksCount = { totalChunks = it }
                        )
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.touch_hand_action),
                        contentDescription = "Skull icon",
                        tint = Color.White.copy(alpha = 0.3f),
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterEnd)
                            .offset(x = (-5).dp)
                            .clickable {
                                if (currentChunk < totalChunks - 1) currentChunk++
                            }
                    )
                }
            }
        }

        // Avatar
        AnimatedHero(
            sequence = heroAnimationSequence2,
            modifier = Modifier
                .size(avatarSizer.dp)
                .align(Alignment.TopStart)
        )
    }
}