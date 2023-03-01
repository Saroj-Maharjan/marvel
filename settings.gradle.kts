rootProject.name = "MarvelApp"
include(":app")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
include(":core:data")
include(":core:designsystem")
include(":core:network")
include(":core:database")
include(":core:common")
