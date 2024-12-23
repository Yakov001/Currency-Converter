import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.binaryen.BinaryenExec
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id(libs.plugins.kotlinMultiplatform.get().pluginId)
    id(libs.plugins.androidApplication.get().pluginId)
    id(libs.plugins.jetbrainsCompose.get().pluginId)
    id(libs.plugins.composeCompiler.get().pluginId)
    id(libs.plugins.serialization.get().pluginId)
}

kotlin {

    jvmToolchain(libs.versions.jdk.get().toInt())

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    androidTarget()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {

        commonMain.dependencies {
            implementation(projects.common.core)
            implementation(projects.common.currencies.api)
            implementation(projects.common.currencies.impl)
            implementation(projects.common.converter.api)
            implementation(projects.common.converter.impl)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.decompose)
            implementation(libs.decompose.compose)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
        wasmJsMain.dependencies {
            implementation(libs.serialization.json)
        }
    }
}

android {
    namespace = Android.namespace
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = Android.applicationId
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(Android.Proguard.proguardRulesPro)
        }
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

tasks.named<BinaryenExec>("compileProductionExecutableKotlinWasmJsOptimize") {
    binaryenArgs = mutableListOf("-O1", "--all-features")
}
