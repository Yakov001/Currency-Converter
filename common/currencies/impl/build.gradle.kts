plugins {
    id("multiplatform-setup")
    id(libs.plugins.serialization.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.core)
            implementation(projects.common.currencies.api)

            implementation(libs.bundles.ktor.client)
            implementation(libs.bundles.coil)
            implementation(libs.decompose)
            implementation(libs.kstore)

            implementation(compose.components.resources)
        }
        iosMain.dependencies {
            implementation(libs.kstore.file)
        }
        androidMain.dependencies {
            implementation(libs.kstore.file)
        }
        wasmJsMain.dependencies {
            implementation(libs.kstore.storage)
        }
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.serialization.json)
            implementation(libs.bundles.mockito)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.koin.test)
            implementation(libs.serialization.json)
        }
    }
}