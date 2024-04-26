pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {

        create("libs") {

            version("coreVersion", "1.13.0")
            version("lifecycleVersion", "2.7.0")
            version("composeActivityVersion", "1.9.0")
            version("composeVersion", "1.6.6")
            version("composeMaterial3Version", "1.2.1")
            version("coroutinesVersion", "1.8.0")
            version("hiltVersion", "2.49")
            version("hiltNavigationVersion", "1.2.0")
            version("hiltCompilerVersion", "2.45")
            version("constraintLayoutVersion", "1.0.1")
            version("firebaseAnalyticsVersion", "21.6.2")
            version("firebaseAuthVersion", "22.3.1")
            version("retrofitVersion", "2.11.0")
            version("loggingInterceptorVersion", "4.12.0")
            version("roomVersion", "2.5.2")
            version("accompanistVersion", "0.25.0")
            version("servicesLocationVersion", "21.2.0")

            // Android Core
            library(
                "core",
                "androidx.core",
                "core-ktx"
            ).versionRef("coreVersion")
            library(
                "lifecycle",
                "androidx.lifecycle",
                "lifecycle-runtime-ktx"
            ).versionRef("lifecycleVersion")

            // Compose
            library(
                "compose-activity",
                "androidx.activity",
                "activity-compose"
            ).versionRef("composeActivityVersion")
            library(
                "compose-ui",
                "androidx.compose.ui",
                "ui"
            ).versionRef("composeVersion")
            library(
                "compose-ui-graphics",
                "androidx.compose.ui",
                "ui-graphics"
            ).versionRef("composeVersion")
            library(
                "compose-ui-tooling-preview",
                "androidx.compose.ui",
                "ui-tooling-preview"
            ).versionRef("composeVersion")
            library(
                "compose-material3",
                "androidx.compose.material3",
                "material3"
            ).versionRef("composeMaterial3Version")
            library(
                "compose-constraint-layout",
                "androidx.constraintlayout",
                "constraintlayout-compose"
            ).versionRef("constraintLayoutVersion")
            library(
                "services-location",
                "com.google.android.gms",
                "play-services-location"
            ).versionRef("servicesLocationVersion")

            // Coroutines
            library(
                "coroutines",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-android"
            ).versionRef(
                "coroutinesVersion"
            )

            // Hilt
            library(
                "hilt-android",
                "com.google.dagger",
                "hilt-android"
            ).versionRef("hiltVersion")
            library(
                "hilt-navigation",
                "androidx.hilt",
                "hilt-navigation-compose"
            ).versionRef("hiltNavigationVersion")
            library(
                "hilt-compiler",
                "com.google.dagger",
                "hilt-android-compiler"
            ).versionRef("hiltCompilerVersion")

            // Firebase
            library(
                "firebase-analytics",
                "com.google.firebase",
                "firebase-analytics-ktx"
            ).versionRef("firebaseAnalyticsVersion")
            library(
                "firebase-auth",
                "com.google.firebase",
                "firebase-auth-ktx"
            ).versionRef("firebaseAuthVersion")

            // Retrofit
            library(
                "retrofit",
                "com.squareup.retrofit2",
                "retrofit"
            ).versionRef("retrofitVersion")
            library(
                "retrofit-gson",
                "com.squareup.retrofit2",
                "converter-gson"
            ).versionRef("retrofitVersion")
            library(
                "logging-interceptor",
                "com.squareup.okhttp3",
                "logging-interceptor"
            ).versionRef("loggingInterceptorVersion")

            // Room
            library(
                "room-runtime",
                "androidx.room",
                "room-runtime"
            ).versionRef("roomVersion")
            library(
                "room-ktx",
                "androidx.room",
                "room-ktx"
            ).versionRef("roomVersion")
            library(
                "room-compiler",
                "androidx.room",
                "room-compiler"
            ).versionRef("roomVersion")

            // Accompanist Permission
            library(
                "accompanist-permission",
                "com.google.accompanist",
                "accompanist-permissions"
            ).versionRef("accompanistVersion")

            // Bundles
            bundle(
                "android-core", listOf(
                    "core",
                    "lifecycle",
                    "compose-activity",
                    "compose-ui",
                    "compose-ui-graphics",
                    "compose-ui-tooling-preview",
                    "compose-material3",
                    "coroutines",
                    "compose-constraint-layout",
                    "services-location"
                )
            )
            bundle(
                "hilt", listOf(
                    "hilt-android",
                    "hilt-navigation"
                )
            )
            bundle(
                "firebase", listOf(
                    "firebase-analytics",
                    "firebase-auth"
                )
            )
            bundle(
                "retrofit", listOf(
                    "retrofit",
                    "retrofit-gson",
                    "logging-interceptor"
                )
            )
            bundle(
                "room", listOf(
                    "room-runtime",
                    "room-ktx"
                )
            )
        }

        create("testLibs") {

            version("junitVersion", "4.13.2")
            version("uiAndroidJunitVersion", "1.1.5")
            version("espressoCoreVersion", "3.5.1")
            version("uiTestVersion", "1.6.6")
            version("mockitoVersion", "3.2.0")
            version("mockitoInlineVersion", "5.1.0")
            version("archCoreVersion", "2.2.0")
            version("kotlinxCoroutinesVersion", "1.8.0")
            version("turbineVersion", "0.12.1")

            // Local Test
            library("junit", "junit", "junit").versionRef("junitVersion")
            library(
                "mockito-kotlin",
                "org.mockito.kotlin",
                "mockito-kotlin"
            ).versionRef("mockitoVersion")
            library(
                "mockito-inline",
                "org.mockito",
                "mockito-inline"
            ).versionRef("mockitoInlineVersion")
            library(
                "arch-core",
                "androidx.arch.core",
                "core-testing"
            ).versionRef("archCoreVersion")
            library(
                "kotlinx-coroutines",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-test"
            ).versionRef("kotlinxCoroutinesVersion")
            library(
                "turbine",
                "app.cash.turbine",
                "turbine"
            ).versionRef("turbineVersion")

            // UI Androidx JUnit
            library(
                "ui-android-junit",
                "androidx.test.ext",
                "junit"
            ).versionRef("uiAndroidJunitVersion")

            // Espresso
            library(
                "espresso-core",
                "androidx.test.espresso",
                "espresso-core"
            ).versionRef("espressoCoreVersion")

            // UI Test
            library(
                "ui-test",
                "androidx.compose.ui",
                "ui-test-junit4"
            ).versionRef("uiTestVersion")

            // Debug implementation for compose ui test
            library(
                "compose-ui-tooling",
                "androidx.compose.ui",
                "ui-tooling"
            ).versionRef("uiTestVersion")
            library(
                "compose-ui-test-manifest",
                "androidx.compose.ui",
                "ui-test-manifest"
            ).versionRef("uiTestVersion")

            // Bundles
            bundle(
                "local", listOf(
                    "junit",
                    "mockito-kotlin",
                    "mockito-inline",
                    "arch-core",
                    "turbine",
                    "kotlinx-coroutines"
                )
            )
            bundle(
                "instrumented", listOf(
                    "ui-android-junit",
                    "espresso-core",
                    "ui-test"
                )
            )
            bundle(
                "ui-compose", listOf(
                    "compose-ui-tooling",
                    "compose-ui-test-manifest"
                )
            )
        }

    }

}

rootProject.name = "Weather App"
include(":app")
