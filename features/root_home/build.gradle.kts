plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}
kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    jvm("desktop"){
        jvmToolchain(17)
    }
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.preview)
                implementation(project(":features:common_ui"))
                implementation(project(":features:task_creation"))
                implementation(project(":features:task_graph"))
            }
        }
//        val androidMain by getting{
//            dependencies {
//
//            }
//        }
//        val desktopMain by getting{
//            dependencies {
//
//            }
//        }
    }


}
android {
    namespace = "com.khalekuzzamanjustcse.taskmanagement.features.root_home"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}