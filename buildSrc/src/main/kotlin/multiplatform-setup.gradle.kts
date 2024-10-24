import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
    id("kotlin-multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    sourceSets {

        jvmToolchain(libs.versions.jdk.get().toInt())

        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {
            browser()
        }

        androidTarget()

        iosX64()
        iosArm64()
        iosSimulatorArm64()

        targets.configureEach {
            compilations.configureEach {
                compileTaskProvider.get().compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }

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
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}