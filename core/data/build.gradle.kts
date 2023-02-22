plugins {
    id("marvel.android.library")
    id("marvel.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.sawrose.marvelapp.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

}