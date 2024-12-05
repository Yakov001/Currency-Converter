plugins {
    id("multiplatform-api-setup")
    id(libs.plugins.serialization.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.decompose)
            api(libs.kotlinx.datetime)
        }
    }
}