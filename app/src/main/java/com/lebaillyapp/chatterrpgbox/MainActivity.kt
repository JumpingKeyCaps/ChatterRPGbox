package com.lebaillyapp.chatterrpgbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lebaillyapp.chatterrpgbox.model.AnimStep
import com.lebaillyapp.chatterrpgbox.ui.DialogueCrawler
import com.lebaillyapp.chatterrpgbox.ui.SpeakBox
import com.lebaillyapp.chatterrpgbox.ui.animation.heroAnimationSequence
import com.lebaillyapp.chatterrpgbox.ui.animation.heroAnimationSequence2
import com.lebaillyapp.chatterrpgbox.ui.theme.ChatterRPGboxTheme
import com.lebaillyapp.chatterrpgbox.ui.theme.medievalPixelFont
import com.lebaillyapp.chatterrpgbox.ui.theme.pixelFont
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatterRPGboxTheme {
                TestScreen()
            }
        }
    }
}

@Composable
fun TestScreen() {
    // tailles et shapes
    val avatarSizer = 125
    val speakCardRoundedCornerShaper = RoundedCornerShape(
        topStart = 42.dp,
        topEnd = 42.dp,
        bottomStart = 42.dp,
        bottomEnd = 42.dp
    )

    val demoText = """
Greetings, mortal. I am Mr.Evil, the eternal warden of the Infernal Realms. 
Here, time is a cruel illusion, and every second stretches into an eternity of suffering. 
The air burns with the whispers of lost souls, their lamentations echoing through the obsidian halls. 
Rivers of molten fire carve paths across the landscape, reflecting the twisted shadows of despair. 
The sky is a canvas of torment, painted with crimson storms and blackened clouds that never yield. 

You will witness the hierarchy of agony, from the subtle torments that gnaw at your sanity 
to the grand spectacles of suffering orchestrated for amusement beyond mortal comprehension. 
Fear will become your companion, and regret your constant reminder that every choice brought 
you here. There is no mercy. There is only observation, and my amused curiosity at your 
feeble attempts to survive.

Welcome to my dominion, where hope dies slow, where screams are music, 
and where I, Mr.Evil, delight in the artistry of despair.
""".trimIndent()

    //todo la compo

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
    ) {
        // Background header
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.background_1),
            contentDescription = "Background",
            contentScale = ContentScale.FillWidth
        )

        // SpeakBox principale
        SpeakBox(
            modifier = Modifier.align(Alignment.BottomStart),
            avatarSizer = avatarSizer,
            speakCardRoundedCornerShaper = speakCardRoundedCornerShaper,
            text2say = demoText
        )


    }
}


@Composable
fun AnimatedHero(
    sequence: List<AnimStep>,
    modifier: Modifier = Modifier,
    isLooping: Boolean = true
) {
    // État mutable pour stocker l'ID de l'asset actuellement affiché
    var currentDrawableId by remember { mutableStateOf(sequence.first().drawableResId) }

    // LaunchedEffect crée une coroutine qui s'exécute quand le composable entre dans la composition
    LaunchedEffect(key1 = sequence) {
        // Boucle infinie pour jouer l'animation en permanence
        while (isLooping) {
            // Parcourir chaque étape de la séquence
            for (step in sequence) {
                // 1. Mettre à jour l'image affichée
                currentDrawableId = step.drawableResId

                // 2. Attendre le délai configuré
                delay(step.durationMs)
            }
        }
    }

    // Composant Image final (utilise l'ID d'asset mis à jour)
    Image(
        painter = painterResource(id = currentDrawableId),
        contentDescription = "Hero Animation Frame",
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}






@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    ChatterRPGboxTheme {
        TestScreen()
    }
}