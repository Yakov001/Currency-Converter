import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id(libs.plugins.kotlinMultiplatform.get().pluginId)
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.jetbrainsCompose.get().pluginId)
    id(libs.plugins.composeCompiler.get().pluginId)
}

kotlin {
    sourceSets {

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