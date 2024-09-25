import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
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
    namespace = "com.resonanse.common.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}