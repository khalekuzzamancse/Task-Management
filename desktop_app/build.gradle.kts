import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}
kotlin {
   jvm{
       jvmToolchain(17)
       withJava()
   }
    sourceSets{
        val jvmMain by getting{
            dependencies {
                implementation(project(":shared"))
                implementation(project(":features:root_home"))
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(libs.voyager.navigator)
            }
        }
    }


}
compose.desktop{
    application{
        mainClass="DesktopMainKt"
        nativeDistributions{
            targetFormats(TargetFormat.Exe)
            packageName="justdiarydesktop"
            version="1.0.0"
        }
    }
}