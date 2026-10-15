plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

// The display name stays configurable until trademark and domain clearance (M01 task 2).
val appDisplayName: String = providers.gradleProperty("APP_DISPLAY_NAME").getOrElse("Yashi VPN")

android {
    // Placeholder identity: com.example.* cannot be published on Google Play, by design.
    // Replace it after brand and domain clearance (docs/decisions/ADR-0012).
    namespace = "com.example.yashivpn"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.yashivpn"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
        manifestPlaceholders["appDisplayName"] = appDisplayName
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    lint {
        abortOnError = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)
}
