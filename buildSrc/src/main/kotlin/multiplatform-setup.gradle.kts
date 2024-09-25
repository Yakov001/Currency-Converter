import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("kotlin-multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    sourceSets {

        jvmToolchain(17)

        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {
            browser()
        }

        androidTarget()

        iosX64()
        iosArm64()
        iosSimulatorArm64()

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)
        }
    }
}

android {
    compileSdk = 34
}