import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
    id("kotlin-multiplatform")
    id("com.android.library")
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

        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}