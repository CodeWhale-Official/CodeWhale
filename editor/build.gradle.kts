plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serializaton)
}

android {
    namespace = "com.bluewhaleyt.codewhale.editor"

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    implementation(project(":common"))

    api(libs.bundles.editor)
    coreLibraryDesugaring(libs.android.desugar.jdk.libs)
}