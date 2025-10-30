package com.lebaillyapp.chatterrpgbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lebaillyapp.chatterrpgbox.model.AnimStep
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

    // Round shape of the card
    val speakCardRoundedCornerShaper = RoundedCornerShape(
        topStart = 42.dp,
        topEnd = 42.dp,
        bottomStart = 42.dp,
        bottomEnd = 42.dp
    )
    // Cut shape of the card
    val speakCardCutCornerShaper = CutCornerShape(
        topStart = 12.dp,
        topEnd = 12.dp,
        bottomStart = 12.dp,
        bottomEnd = 12.dp
    )

    // size of the avatar in dp
    val avatarSizer = 125


//todo THE COMPO CORE
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
    ) {
        //Background header
        Image(
            modifier = Modifier.align(Alignment.TopCenter)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.background_1),
            contentDescription = "Background",
            contentScale = ContentScale.FillWidth
        )


        //Speak box
        Box(modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth().padding(bottom = 62.dp,start=10.dp)) {

            Box(modifier = Modifier
                .padding(
                    start = (avatarSizer*0.45f).dp,
                    top = (avatarSizer*0.30f).dp,
                    end = 10.dp,
                    bottom = 10.dp
                )
            ){
                Card(
                    modifier = Modifier
                        .height(74.dp)
                        .widthIn(min = 300.dp, max = 300.dp)
                        .align(Alignment.CenterStart),
                    shape = speakCardRoundedCornerShaper,
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF0C0B17)
                    ),

                ) {
                    Box(modifier = Modifier.fillMaxSize().padding(start = 60.dp, end = 20.dp)){

                        Column(Modifier.fillMaxSize().align(Alignment.TopStart),verticalArrangement = Arrangement.Center) {
                            Text(
                                text = "Mr.Evil",
                                fontFamily  = pixelFont,
                                fontSize = 19.sp,
                                modifier = Modifier
                                    .align(Alignment.Start),
                                color = Color(0xFF414141)
                            )
                            Text(
                                text = "Welcome to hell hooman!",
                                fontFamily  = pixelFont,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .align(Alignment.Start),
                                color = Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "Let first, introduce myself ...",
                                fontFamily  = pixelFont,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .align(Alignment.Start),
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }


                        Icon(
                            painter = painterResource(id = R.drawable.touch_hand_action),
                            contentDescription = "Skull icon",
                            tint = Color.White.copy(alpha = 0.3f),
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    // Action ici
                                }
                                .align(Alignment.CenterEnd)
                                .offset(x= (-5).dp, y = 0.dp)

                        )


                    }




                }
            }


            //Avatar
            AnimatedHero(
                sequence = heroAnimationSequence2,
                modifier = Modifier
                    .size(avatarSizer.dp)
                    .align(Alignment.TopStart)
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