plugins {
    id("multiplatform-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.ktor.client)

            api(libs.koin.core)
            api(libs.koin.compose)
        }
    }
}

android {
    namespace = "com.resonanse.common.core"
}