plugins {
    id("multiplatform-api-setup")
    id(libs.plugins.serialization.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.core)
            implementation(libs.decompose)
            api(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}