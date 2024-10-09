plugins {
    id(libs.plugins.javaLibrary.get().pluginId)
    id(libs.plugins.kotlinJvm.get().pluginId)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}