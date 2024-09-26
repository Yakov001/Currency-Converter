plugins {
    id("multiplatform-setup")
    id(libs.plugins.serialization.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.core)

            implementation(libs.bundles.ktor.client)
            implementation(libs.decompose)
        }
    }
}

android {
    namespace = "com.resonanse.common.coins"
}