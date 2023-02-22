plugins {
    id("marvel.android.library")
    id("marvel.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.sawrose.marvelapp.core.network"

    buildFeatures {
        buildConfig = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
}