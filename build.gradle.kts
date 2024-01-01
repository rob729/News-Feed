plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.androidx.baselineprofile) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.detekt)
    if (File("app/google-services.json").exists() && File("app/src/debug/google-services.json").exists()) {
        alias(libs.plugins.google.services) apply false
        alias(libs.plugins.firebase.crashlytics) apply false
    }
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}

detekt {
    autoCorrect = true
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/config/detekt/detekt.yml")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
