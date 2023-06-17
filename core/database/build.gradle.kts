plugins {
    id("marvel.android.library")
    id("marvel.android.hilt")
    id("marvel.android.room")
}

android {
    namespace = "com.sawrose.marvelapp.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
}