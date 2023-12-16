plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.khalekuzzanman.just.cse.database"
    compileSdk = 33

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("com.google.firebase:firebase-auth:22.2.0")
    implementation("com.google.firebase:firebase-firestore:24.9.0")

    implementation("androidx.core:core-ktx:1.9.0")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    androidTestRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}