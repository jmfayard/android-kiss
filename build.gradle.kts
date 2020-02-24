import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application") version "3.5.3"
    kotlin("android") version "1.3.61"
    id("de.fayard.buildSrcVersions") version "0.7.0"

}

repositories {
    google()
    jcenter()
    maven("https://jitpack.io")
}
configurations.all {
    resolutionStrategy {
        for (version in Config.forceDependencyVersions) {
            force(version)
        }
    }
}

android {

    compileSdkVersion(Config.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Config.minSdkVersion)
        targetSdkVersion(Config.targetSdkVersion)
        versionCode = Config.versionCode
        versionName  = Config.versionName
        applicationId = Config.applicationId
        testInstrumentationRunner ="android.support.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    packagingOptions {
        exclude("LICENSE.txt")
    }
}

dependencies {


    // Magellan, the Simplest Navigation for Android https://github.com/wealthfront/magellan
    implementation(Libs.magellan)
    implementation(Libs.magellan_support)

    implementation(Libs.kotlinandroidviewbindings)
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.support_v4)
    implementation(Libs.appcompat_v7)
    implementation(Libs.preference_v7)
    implementation(Libs.design)
    implementation(Libs.percent)
    implementation(Libs.cardview_v7)
    implementation(Libs.constraint_layout)
    implementation(Libs.multidex)
    implementation(Libs.timber)
    implementation(Libs.slimadapter)
    implementation(Libs.support_core_utils)
    implementation(Libs.recyclerview_v7)
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.kotlinx_coroutines_android)
    compileOnly(Libs.jsr305)
    testCompileOnly(Libs.jsr305)
    testImplementation(Libs.kotlintest)
    testImplementation(Libs.junit)
    androidTestImplementation(Libs.kotlintest)
    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.espresso_contrib)
    androidTestImplementation(Libs.com_android_support_test_rules)
    androidTestImplementation(Libs.com_android_support_test_runner)
    implementation(Libs.threetenabp)
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}
