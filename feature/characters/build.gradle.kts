plugins {
    id("marvel.android.feature")
    id("marvel.android.library.compose")
}

android {
    namespace = "com.sawrose.marvelapp.feature.characters"
}

dependencies {
    implementation(libs.androidx.paging.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.activity.compose)
}