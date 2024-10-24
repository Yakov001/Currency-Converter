plugins {
    id("multiplatform-api-setup")
    id(libs.plugins.serialization.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.decompose)
        }
    }
}

android {
    namespace = "com.resonanse.common.currencies.api"
}