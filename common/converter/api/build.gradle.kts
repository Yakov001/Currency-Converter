plugins {
    id("multiplatform-api-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.common.currencies.api)

            implementation(libs.decompose)
        }
    }
}

android {
    namespace = "com.resonanse.common.converter.api"
}