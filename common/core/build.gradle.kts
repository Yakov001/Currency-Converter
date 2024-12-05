import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id("multiplatform-setup")
    id(libs.plugins.buildConfig.get().pluginId)
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

buildkonfig {
    packageName = "${Android.namespace}.common.core"

    defaultConfigs {
        val apiKey: String = gradleLocalProperties(rootDir, providers).getProperty("apiKey")

        require(apiKey.isNotEmpty()) {
            "Missing API_KEY in local.properties as `apiKey=apiKey`"
        }

        buildConfigField(STRING, "API_KEY", apiKey)
    }
}