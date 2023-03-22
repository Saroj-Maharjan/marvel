plugins {
    id("marvel.android.library")
    kotlin("kapt")
}

android {
    namespace = "com.sawrose.marvelapp.core.domain"
}

dependencies{
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}