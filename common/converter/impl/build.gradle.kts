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

            implementation(libs.bundles.coil)

            implementation(libs.decompose)
            implementation(libs.decompose.compose)

            implementation(compose.components.resources)
        }
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.serialization.json)
            implementation(libs.bundles.mockito)
        }
    }
}

android {
    namespace = "com.resonanse.common.converter.impl"
    dependencies {
        implementation(libs.compose.ui.tooling.preview)
        debugImplementation(libs.compose.ui.tooling)
    }
}