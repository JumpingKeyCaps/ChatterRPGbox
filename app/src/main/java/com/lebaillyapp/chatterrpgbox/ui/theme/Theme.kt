package com.lebaillyapp.chatterrpgbox.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.lebaillyapp.chatterrpgbox.R
import androidx.compose.material3.Typography
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF000000),
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF000000),
    secondary = PurpleGrey40,
    tertiary = Pink40



    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ChatterRPGboxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicDarkColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> DarkColorScheme
    }


    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            // 1. Définir la couleur de la Barre de Statut
            window.statusBarColor = Color(0x00000000).toArgb()

            // 2. Définir la couleur de la Barre de Navigation
            window.navigationBarColor = colorScheme.primary.toArgb()

            // isAppearanceLightStatusBars = false force les icônes de la Status Bar à être CLAIRES.
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false

            // isAppearanceLightNavigationBars = false force les icônes de la Navigation Bar à être CLAIRES.
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = medievalTypography,
        content = content
    )



}