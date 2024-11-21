plugins {
    id("multiplatform-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.ktor.client)

            api(libs.koin.core)
            api(libs.koin.compose)

            api(libs.kotlinx.datetime)
        }
        androidMain.dependencies {
            api(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
        }
    }
}

android {
    namespace = "com.resonanse.common.core"
}