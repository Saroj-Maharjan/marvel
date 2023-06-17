import extension.buildConfigStringField
import extension.getLocalProperty

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

    buildTypes.forEach {
        try{
            it.buildConfigStringField("MARVEL_API_BASE_URL", getLocalProperty("marvel.url"))
            it.buildConfigStringField("MARVEL_API_KEY_PUBLIC", getLocalProperty("marvel.key.public"))
            it.buildConfigStringField("MARVEL_API_KEY_PRIVATE", getLocalProperty("marvel.key.private"))
        }
        catch(ignored: Exception){
            throw InvalidUserDataException("You should define 'marvel.key.public' and " +
                    "'marvel.key.private' in local.properties. Visit 'https://developer.marvel.com' " +
                    "to obtain them.")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
}