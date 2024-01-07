pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

    }
}
dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven ( "https://jitpack.io" )
    }
}
rootProject.name = "TaskManagement"
include(":app",":desktop_app")
include(":shared")
include(":features",":features:common_ui","" +
        ":features:task_creation",":features:root_home","features:task_graph")