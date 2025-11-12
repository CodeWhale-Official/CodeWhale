plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serializaton)
}

android {
    namespace = "com.bluewhaleyt.codewhale.common"
}

dependencies {
    api(libs.bundles.kotlin)
    api(libs.bundles.android)
    api(libs.bundles.compose)
}