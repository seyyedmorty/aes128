package com.example.aes128.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = AmberGold,
    onPrimary = Color.Black,
    secondary = MutedAmber,
    onSecondary = Color.White,
    background = CarbonBlack,
    surface = DeepGunmetal,
    onBackground = SoftWhite,
    onSurface = SoftWhite,
    outline = MutedAmber.copy(alpha = 0.6f)
)

// برای لایت مود هم همین ترکیب را بگذار تا تم تغییر نکند،
// اما کمی روشن‌تر در بخش سطح (Surface)
private val LightColorScheme = lightColorScheme(
    primary = AmberGold,
    onPrimary = Color.Black,
    background = Color(0xFF121212), // خاکستری بسیار تیره به جای مشکی مطلق
    surface = Color(0xFF1E1E1E),
    onBackground = SoftWhite,
    onSurface = SoftWhite,
    outline = MutedAmber
)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */


@Composable
fun AES128Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}