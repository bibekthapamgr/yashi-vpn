package com.example.yashivpn.ui.theme

/**
 * Quiet Orbit tokens as ARGB values, from docs/team-pack/02-BRAND-UI-UX.md.
 * Kept free of Android and Compose types so unit tests can check contrast on the JVM.
 */
object QuietOrbitPalette {
    object Dark {
        const val BACKGROUND = 0xFF081321
        const val SURFACE = 0xFF122238
        const val SURFACE_ELEVATED = 0xFF1B304A
        const val TEXT_PRIMARY = 0xFFF4F8FF
        const val TEXT_SECONDARY = 0xFFB5C4D6
        const val BRAND = 0xFF68E0E8
        const val ON_BRAND = 0xFF081321
        const val SUCCESS = 0xFF76E3B1
        const val WARNING = 0xFFFFD38A
        const val ERROR = 0xFFFFADB5
        const val BORDER_DECORATIVE = 0xFF2B405A
    }

    object Light {
        const val BACKGROUND = 0xFFF5F8FC
        const val SURFACE = 0xFFFFFFFF
        const val SURFACE_ELEVATED = 0xFFEAF0F7
        const val TEXT_PRIMARY = 0xFF122238
        const val TEXT_SECONDARY = 0xFF4A5E75
        const val BRAND = 0xFF006872
        const val ON_BRAND = 0xFFFFFFFF
        const val SUCCESS = 0xFF17643B
        const val WARNING = 0xFF805000
        const val ERROR = 0xFFA52238
        const val BORDER_DECORATIVE = 0xFFCFD9E7
    }
}
