//import de.fayard.versions.bootstrapRefreshVersions
//import de.fayard.dependencies.DependenciesPlugin

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            val plugin = requested.id.id
            val module = when {
                plugin.startsWith("com.android") -> "com.android.tools.build:gradle:3.5.3"
                else -> return@eachPlugin
            }
            println("resolutionStrategy for plugin=$plugin : $module")
            useModule(module)
        }
    }
}

buildscript {
    repositories {
        gradlePluginPortal()
    }
    //dependencies.classpath("de.fayard:dependencies:0.5.6")
}

plugins {
    id("com.gradle.enterprise").version("3.1.1")
}


//bootstrapRefreshVersions(DependenciesPlugin.artifactVersionKeyRules)

rootProject.name = "android-kotlin-magellan"

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
    }
}