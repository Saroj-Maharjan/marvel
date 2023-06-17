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
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:notification"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.mockk)

}