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
include(":feature:characters")
include(":feature:favourite")
include(":core:model")
include(":core:ui")
include(":core:datastore")
include(":feature:settings")
include(":core:domain")
include(":sync:work")
include(":core:testing")
include(":core:notification")
