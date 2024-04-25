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
                    "coroutines"
                )
            )
            bundle(
                "hilt", listOf(
                    "hilt-android",
                    "hilt-navigation"
                )
            )
        }

        create("testLibs") {

            version("junitVersion", "4.13.2")
            version("uiAndroidJunitVersion", "1.1.5")
            version("espressoCoreVersion", "3.5.1")
            version("uiTestVersion", "1.6.6")

            // Local Test
            library("junit", "junit", "junit").versionRef("junitVersion")

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
                    "junit"
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
