plugins {
    id("multiplatform-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.core)
        }
    }
}

android {
    namespace = "com.resonanse.common.coins"
}