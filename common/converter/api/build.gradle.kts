plugins {
    id("multiplatform-api-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.common.currencies.api)
            implementation(projects.common.core)

            implementation(libs.decompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.bundles.mockito)
        }
    }
}

android {
    namespace = "com.resonanse.common.converter.api"
}