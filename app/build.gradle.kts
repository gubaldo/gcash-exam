plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.stratpoint.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.stratpoint.weatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Android Core
    implementation(libs.bundles.android.core)
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))

    //Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    // Testing
    testImplementation(testLibs.bundles.local)
    androidTestImplementation(testLibs.bundles.instrumented)
    debugImplementation(testLibs.bundles.ui.compose)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
}