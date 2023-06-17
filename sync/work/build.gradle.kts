plugins {
    id("marvel.android.library")
    id("marvel.android.hilt")
}

android {
    defaultConfig {
        testInstrumentationRunner = "com.sawrose.marvelapp.core.testing.MarvelTestRunner"
    }
    namespace = "com.sawrose.marvelapp.sync"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:datastore"))
    implementation(project(":core:model"))

    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.work.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.ext.work)

    kapt(libs.hilt.ext.compiler)

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(libs.androidx.work.testing)
}