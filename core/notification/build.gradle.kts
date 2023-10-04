plugins {
    id("marvel.android.library")
    id("marvel.android.library.compose")
    id("marvel.android.hilt")
}

android {
    namespace = "com.sawrose.marvelapp.core.notification"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.core.ktx)
}