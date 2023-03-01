plugins {
    id("marvel.android.library")
    id("marvel.android.hilt")
}

android {
    namespace = "com.sawrose.marvelapp.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
}