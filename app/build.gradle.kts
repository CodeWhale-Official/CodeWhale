plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serializaton)
}

android {
    namespace = "com.bluewhaleyt.codewhale"

    defaultConfig {
        applicationId = "com.bluewhaleyt.codewhale"
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
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    implementation(project(":common"))

    coreLibraryDesugaring(libs.android.desugar.jdk.libs)
}