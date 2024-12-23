pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Booster"
include(":app")
include(":common")
include(":features:news")
include(":features:headlines")
include(":features:home")
include(":features:newsdetails")
include(":features:user")
include(":features:profile")
include(":features:auth")
include(":features:savvy")
