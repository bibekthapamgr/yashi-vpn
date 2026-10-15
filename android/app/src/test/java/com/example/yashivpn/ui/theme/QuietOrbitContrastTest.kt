package com.example.yashivpn.ui.theme

import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.pow

/**
 * Guards the WCAG AA text contrast (4.5:1) of the Quiet Orbit token pairs used for text.
 * Decorative borders are excluded on purpose: the spec limits them to card separation.
 */
class QuietOrbitContrastTest {

    @Test
    fun darkThemeTextPairsMeetWcagAa() = with(QuietOrbitPalette.Dark) {
        for (background in listOf(BACKGROUND, SURFACE, SURFACE_ELEVATED)) {
            assertAa("primary text", TEXT_PRIMARY, background)
            assertAa("secondary text", TEXT_SECONDARY, background)
            assertAa("brand link", BRAND, background)
            assertAa("success", SUCCESS, background)
            assertAa("warning", WARNING, background)
            assertAa("error", ERROR, background)
        }
        assertAa("button text on brand", ON_BRAND, BRAND)
        assertAa("text on error", BACKGROUND, ERROR)
    }

    @Test
    fun lightThemeTextPairsMeetWcagAa() = with(QuietOrbitPalette.Light) {
        for (background in listOf(BACKGROUND, SURFACE, SURFACE_ELEVATED)) {
            assertAa("primary text", TEXT_PRIMARY, background)
            assertAa("secondary text", TEXT_SECONDARY, background)
            assertAa("brand link", BRAND, background)
            assertAa("success", SUCCESS, background)
            assertAa("warning", WARNING, background)
            assertAa("error", ERROR, background)
        }
        assertAa("button text on brand", ON_BRAND, BRAND)
        assertAa("text on error", SURFACE, ERROR)
    }

    private fun assertAa(name: String, foreground: Long, background: Long) {
        val ratio = contrast(foreground, background)
        assertTrue(
            "$name contrast ${"%.2f".format(ratio)}:1 on ${hex(background)} is below 4.5:1",
            ratio >= 4.5,
        )
    }

    private fun contrast(a: Long, b: Long): Double {
        val la = luminance(a)
        val lb = luminance(b)
        return (maxOf(la, lb) + 0.05) / (minOf(la, lb) + 0.05)
    }

    private fun luminance(argb: Long): Double {
        fun channel(value: Long): Double {
            val s = value / 255.0
            return if (s <= 0.04045) s / 12.92 else ((s + 0.055) / 1.055).pow(2.4)
        }
        val r = channel((argb shr 16) and 0xFF)
        val g = channel((argb shr 8) and 0xFF)
        val b = channel(argb and 0xFF)
        return 0.2126 * r + 0.7152 * g + 0.0722 * b
    }

    private fun hex(argb: Long): String = "#%06X".format(argb and 0xFFFFFF)
}
