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
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
    }
}

android {
    namespace = "com.resonanse.common.core"
}