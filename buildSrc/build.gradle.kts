plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.plugin.kotlin)
    implementation(libs.plugin.android)
    implementation(libs.plugin.compose)
    implementation(libs.plugin.compose.compiler)
    implementation(libs.plugin.serialization)
    implementation(libs.plugin.buildkonfig)
}