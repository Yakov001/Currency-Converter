plugins {
    id("multiplatform-setup")
    id(libs.plugins.serialization.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.core)
            implementation(projects.common.converter.api)
            implementation(projects.common.currencies.impl)

            implementation(libs.decompose)
            implementation(libs.decompose.compose)

            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
        }
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.serialization.json)
            implementation(libs.mockito.core)
            implementation(libs.mockito.kotlin)
        }
    }
}

android {
    namespace = "com.resonanse.common.converter.impl"
}