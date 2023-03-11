import com.sawrose.marvelapp.MaBuildType

plugins {
    id("marvel.android.application")
    id("marvel.android.application.compose")
    id("marvel.android.flavour")
    id("marvel.android.hilt")

}

android {
    defaultConfig {
        applicationId = "com.sawrose.marvelapp"
        versionCode = 1
        versionName = "0.0.1"

        // Custom test runner to set up Hilt dependency graph
        testInstrumentationRunner =
            "com.google.samples.apps.nowinandroid.core.testing.MaTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = MaBuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = MaBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.getByName("debug")
        }
        val benchmark by creating {
            // Enable all the optimizations from release build through initWith(release).
            initWith(release)
            matchingFallbacks.add("release")
            // Debug key signing is available to everyone.
            signingConfig = signingConfigs.getByName("debug")
            // Only use benchmark proguard rules
            proguardFiles("benchmark-rules.pro")
            isMinifyEnabled = true
            applicationIdSuffix = MaBuildType.BENCHMARK.applicationIdSuffix
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
//        managedDevices {
//            devices {
//                maybeCreate<ManagedVirtualDevice>("pixel4api30").apply {
//                    device = "Pixel 4"
//                    apiLevel = 30
//                    // ATDs currently support only API level 30.
//                    systemImageSource = "aosp-atd"
//                }
//            }
//        }
//    }

        namespace = "com.sawrose.marvelapp"
    }
}


dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:ui"))


    implementation(project(":feature:characters"))
    implementation(project(":feature:favourite"))

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashScreen)
    implementation(libs.androidx.metrics.performance)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.coil.kt)
}
