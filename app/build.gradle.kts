import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
}

val keyPropertiesFile = rootProject.file("key.properties")
val keyProperties = Properties()
keyProperties.load(FileInputStream(keyPropertiesFile))

val apiKey: String = keyProperties.getProperty("apiKey")

android {
    namespace = "com.stratpoint.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.stratpoint.weatherapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        animationsDisabled = true
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    // Android Core
    implementation(libs.bundles.android.core)
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))

    // Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    // Firebase
    implementation(libs.bundles.firebase)
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Retrofit
    implementation(libs.bundles.retrofit)
    annotationProcessor(libs.room.compiler)

    // Room
    implementation(libs.bundles.room)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    // Accompanist Permission
    implementation(libs.accompanist.permission)

    // Testing
    testImplementation(testLibs.bundles.local)
    androidTestImplementation(testLibs.bundles.instrumented)
    debugImplementation(testLibs.bundles.ui.compose)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}