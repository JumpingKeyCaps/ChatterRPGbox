package com.lebaillyapp.chatterrpgbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lebaillyapp.chatterrpgbox.model.AnimStep
import com.lebaillyapp.chatterrpgbox.ui.theme.ChatterRPGboxTheme
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


    val heroAnimationSequence1 = listOf(
        AnimStep(drawableResId = R.drawable.mrevil_angry_2, durationMs = 200L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_4, durationMs = 200L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_2, durationMs = 200L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_3, durationMs = 200L)
    )


    val heroAnimationSequence2 = listOf(
        AnimStep(drawableResId = R.drawable.mrevil_angry_1, durationMs = 2000L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_4, durationMs = 100L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_5, durationMs = 100L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_4, durationMs = 100L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_3, durationMs = 100L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_4, durationMs = 100L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_3, durationMs = 100L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_4, durationMs = 100L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_5, durationMs = 100L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_4, durationMs = 100L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_6, durationMs = 350L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_2, durationMs = 500L)
    )


    val heroAnimationSequence5 = listOf(
        AnimStep(drawableResId = R.drawable.mrevil_angry_1, durationMs = 1000L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_3, durationMs = 500L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_7, durationMs = 2000L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_6, durationMs = 300L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_2, durationMs = 300L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_1, durationMs = 500L),

    )



    val heroAnimationSequence4 = listOf(
        AnimStep(drawableResId = R.drawable.mrevil_angry_1, durationMs = 400L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_5, durationMs = 200L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_4, durationMs = 200L),
        )

    val heroAnimationSequence8 = listOf(
        AnimStep(drawableResId = R.drawable.mrevil_angry_1, durationMs = 3000L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_2, durationMs = 2000L),
    )


    val heroAnimationSequence = listOf(
        AnimStep(drawableResId = R.drawable.mrevil_angry_1, durationMs = 3000L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_2, durationMs = 2000L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_1, durationMs = 3000L),
        AnimStep(drawableResId = R.drawable.mrevil_angry_7, durationMs = 2000L),
    )




    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
    ) {

        Image(
            modifier = Modifier.align(Alignment.TopCenter)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.background_1),
            contentDescription = "Background",
            contentScale = ContentScale.FillWidth
        )


        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 86.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedHero(
                sequence = heroAnimationSequence,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = "Hell is loading...",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                color = Color.White.copy(alpha = 0.5f)
            )
        }
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