plugins {
    id("multiplatform-api-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.common.currencies.api)

            implementation(libs.decompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.mockito.core)
            implementation(libs.mockito.kotlin)
        }
    }
}

android {
    namespace = "com.resonanse.common.converter.api"
}