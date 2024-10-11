plugins {
    id("multiplatform-api-setup")
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