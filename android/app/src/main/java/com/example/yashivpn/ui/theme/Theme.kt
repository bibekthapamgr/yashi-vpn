package com.example.yashivpn.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/** Status colors with no Material 3 slot. Never show connection state by color alone. */
@Immutable
data class YashiStatusColors(val success: Color, val warning: Color, val error: Color)

val LocalYashiStatusColors = staticCompositionLocalOf {
    YashiStatusColors(success = Color.Unspecified, warning = Color.Unspecified, error = Color.Unspecified)
}

private val DarkColors = darkColorScheme(
    primary = Color(QuietOrbitPalette.Dark.BRAND),
    onPrimary = Color(QuietOrbitPalette.Dark.ON_BRAND),
    background = Color(QuietOrbitPalette.Dark.BACKGROUND),
    onBackground = Color(QuietOrbitPalette.Dark.TEXT_PRIMARY),
    surface = Color(QuietOrbitPalette.Dark.SURFACE),
    onSurface = Color(QuietOrbitPalette.Dark.TEXT_PRIMARY),
    surfaceVariant = Color(QuietOrbitPalette.Dark.SURFACE_ELEVATED),
    onSurfaceVariant = Color(QuietOrbitPalette.Dark.TEXT_SECONDARY),
    error = Color(QuietOrbitPalette.Dark.ERROR),
    // White on the dark error token is only 1.77:1, so text on error uses the midnight background.
    onError = Color(QuietOrbitPalette.Dark.BACKGROUND),
    outlineVariant = Color(QuietOrbitPalette.Dark.BORDER_DECORATIVE),
)

private val LightColors = lightColorScheme(
    primary = Color(QuietOrbitPalette.Light.BRAND),
    onPrimary = Color(QuietOrbitPalette.Light.ON_BRAND),
    background = Color(QuietOrbitPalette.Light.BACKGROUND),
    onBackground = Color(QuietOrbitPalette.Light.TEXT_PRIMARY),
    surface = Color(QuietOrbitPalette.Light.SURFACE),
    onSurface = Color(QuietOrbitPalette.Light.TEXT_PRIMARY),
    surfaceVariant = Color(QuietOrbitPalette.Light.SURFACE_ELEVATED),
    onSurfaceVariant = Color(QuietOrbitPalette.Light.TEXT_SECONDARY),
    error = Color(QuietOrbitPalette.Light.ERROR),
    onError = Color(QuietOrbitPalette.Light.SURFACE),
    outlineVariant = Color(QuietOrbitPalette.Light.BORDER_DECORATIVE),
)

/** Follows the system appearance by default; Settings will offer System/Light/Dark (A08). */
@Composable
fun YashiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val statusColors = if (darkTheme) {
        YashiStatusColors(
            success = Color(QuietOrbitPalette.Dark.SUCCESS),
            warning = Color(QuietOrbitPalette.Dark.WARNING),
            error = Color(QuietOrbitPalette.Dark.ERROR),
        )
    } else {
        YashiStatusColors(
            success = Color(QuietOrbitPalette.Light.SUCCESS),
            warning = Color(QuietOrbitPalette.Light.WARNING),
            error = Color(QuietOrbitPalette.Light.ERROR),
        )
    }
    CompositionLocalProvider(LocalYashiStatusColors provides statusColors) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            content = content,
        )
    }
}
