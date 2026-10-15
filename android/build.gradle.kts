// AGP 9 has built-in Kotlin support, so org.jetbrains.kotlin.android is not applied.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.compose.compiler) apply false
}
