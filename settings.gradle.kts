enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Resonanse"

include(":composeApp")

include(":common:core")

include(":common:converter:api")
include(":common:converter:impl")

include(":common:currencies:api")
include(":common:currencies:impl")
