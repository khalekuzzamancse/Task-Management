
//// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    id("com.android.application") version "8.1.1" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
//    id("com.android.library") version "8.1.1" apply false
//}

// Root Level

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false


}
true // Needed to make the Suppress annotation work for the plugins block
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}
