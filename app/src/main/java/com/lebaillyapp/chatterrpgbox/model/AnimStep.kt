package com.lebaillyapp.chatterrpgbox.model

import androidx.annotation.DrawableRes

data class AnimStep(
    @DrawableRes val drawableResId: Int,
    // Le temps d'affichage de l'asset avant de passer au suivant, en millisecondes
    val durationMs: Long
)